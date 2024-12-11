package net.rafgpereira.transpoapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.CardColors
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import coil3.compose.AsyncImage
import net.rafgpereira.transpoapp.R
import net.rafgpereira.transpoapp.util.StaticMapsUrl
import net.rafgpereira.transpoapp.domain.model.Driver
import net.rafgpereira.transpoapp.domain.model.MAX_RATING
import net.rafgpereira.transpoapp.domain.model.fakeDrivers
import net.rafgpereira.transpoapp.ui.common.ErrorAlertDialog
import net.rafgpereira.transpoapp.ui.common.ScaffoldAndSurface
import net.rafgpereira.transpoapp.ui.common.TextOrProgressIndicator
import net.rafgpereira.transpoapp.ui.common.UiState
import net.rafgpereira.transpoapp.ui.viewmodel.RequestCarOptionsViewModel
import net.rafgpereira.transpoapp.util.debounced
import java.text.NumberFormat

@Composable
fun RequestCarOptionsScreen(
    modifier: Modifier,
    viewModel: RequestCarOptionsViewModel,
    navigateToHistoryScreen: () -> Unit,
    navigateUp: (() -> Unit)?,
) {
    val errorMessage = viewModel.errorMessage.collectAsState(null)
    val drivers = viewModel.drivers.collectAsState()
    val route = viewModel.route.collectAsState()
    val uiState = viewModel.uiState.collectAsState()
    val staticMapUrl = StaticMapsUrl
        .Builder()
        .setMarkers(listOf(route.value.first(), route.value.last()))
        .setPath(route.value)
        .setPathColor(MaterialTheme.colorScheme.primary)
        .build()
        .value

    if (uiState.value == UiState.NAVIGATE) {
        navigateToHistoryScreen()
        viewModel.clearUiState()
    }

    if (errorMessage.value != null)
        ErrorAlertDialog(modifier, errorMessage.value.toString()) { viewModel.clearErrorMessage() }

    RequestCarOptionsScreenContent(
        modifier = modifier,
        uiState = uiState.value,
        drivers = drivers.value,
        staticMapUrl = staticMapUrl,
        confirm = { driver -> viewModel.confirm(driver) },
        navigateUp = navigateUp,
    )
}

@Composable
fun RequestCarOptionsScreenContent(
    modifier: Modifier,
    uiState: UiState,
    staticMapUrl: String,
    drivers: List<Driver>,
    confirm: (Driver) -> Unit,
    navigateUp: (() -> Unit)?,
) = ScaffoldAndSurface(
        modifier = modifier,
        title = stringResource(R.string.requestcaroptions_screen_title),
        navigateUp = navigateUp,
    ) {
        Column {
            RouteMap(modifier, staticMapUrl)
            LazyColumn(contentPadding = PaddingValues(dimensionResource(R.dimen.space))) {
                items(
                    count = drivers.size,
                    itemContent = { index ->
                        val driver = drivers[index]
                        DriverCard(
                            modifier = modifier,
                            uiState = uiState,
                            driver = driver,
                            choose = { confirm(driver) },
                        )
                    },
                )
            }
        }
    }

@Composable
fun RouteMap(modifier: Modifier, imageUrl: String) {
    AsyncImage(
        modifier = modifier.fillMaxWidth(),
        model = imageUrl,
        alignment = Alignment.Center,
        contentScale = ContentScale.FillWidth,
        contentDescription = stringResource(R.string.requestcaroptions_routemap_desc)
    )
}

@Composable
fun DriverCard(
    modifier: Modifier,
    uiState: UiState,
    driver: Driver,
    choose: () -> Unit,
) = ElevatedCard(
    modifier = modifier.padding(dimensionResource(R.dimen.space)),
    colors = CardColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = Color.Black,
        disabledContainerColor = Color.DarkGray,
        disabledContentColor = Color.DarkGray
    )
) {
    Column(
        modifier = modifier.padding(dimensionResource(R.dimen.double_space)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space)),
    ) {
        LabelAndInformation(stringResource(R.string.requestcaroptions_name_label), driver.name)
        LabelAndInformation(
            stringResource(R.string.requestcaroptions_vehicle_label), driver.vehicle
        )
        RatingLabelAndInformation(driver.review.rating, driver.review.comment)
        if (driver.description.isEmpty().not()) {
            LabelAndInformation(
                stringResource(R.string.requestcaroptions_desc_label),
                driver.description
            )
        }
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ValueLabelAndInformation(driver.value)
            Button(
                modifier = modifier.width(dimensionResource(R.dimen.button_width)),
                onClick = debounced(choose),
                contentPadding = PaddingValues(dimensionResource(R.dimen.zero_dp)),
                enabled = uiState == UiState.START,
            ) {
                TextOrProgressIndicator(
                    modifier = modifier,
                    showIndicatorCondition = uiState != UiState.START,
                    text = stringResource(R.string.requestcaroptions_choose_button_text),
                )
            }
        }
    }
}

@Composable
fun RatingLabelAndInformation(rating: Long, comment: String) = Column {
        Text(stringResource(R.string.requestcaroptions_rating_label))
        Row {
            for (i in 1..rating)
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = stringResource(
                        R.string.requestcaroptions_rating_star_desc
                    )
                )
            if (rating < 5) {
                for (i in 1..MAX_RATING - rating)
                    Icon(
                        imageVector = Icons.Filled.Star,
                        tint = Color.LightGray,
                        contentDescription = stringResource(
                            R.string.requestcaroptions_rating_max_star_desc
                        )
                    )
            }
        }
        InformationText(comment)
    }

@Composable
fun ValueLabelAndInformation(value: Double) = Column {
        Text(stringResource(R.string.requestcaroptions_cost_label))
        InformationText(
            NumberFormat.getCurrencyInstance().format(value),
            MaterialTheme.typography.headlineMedium
        )
    }

@Composable
fun LabelAndInformation(label: String, info: String) = Column {
        Text(label)
        InformationText(info)
    }

@Composable
fun InformationText(info: String, style: TextStyle = MaterialTheme.typography.titleMedium) = Text(
        text = info,
        style = style,
        fontWeight = FontWeight.Bold,
    )

@Composable
@Preview(showSystemUi = false, showBackground = true)
fun LabelAndInformationPreview() = LabelAndInformation(
    stringResource(R.string.requestcaroptions_name_label), fakeDrivers[0].name
)

@Composable
@Preview(showSystemUi = false, showBackground = false)
fun DriverCardPreview() = DriverCard(Modifier, UiState.START, fakeDrivers[0]) {}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun RequestCarOptionsScreenContentPreview() =
    RequestCarOptionsScreenContent(
        Modifier, UiState.START,"", fakeDrivers, {}) {}