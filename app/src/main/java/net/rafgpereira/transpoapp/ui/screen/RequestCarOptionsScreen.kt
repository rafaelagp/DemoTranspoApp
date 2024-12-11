package net.rafgpereira.transpoapp.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CardColors
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import net.rafgpereira.transpoapp.R
import net.rafgpereira.transpoapp.util.StaticMapsUrl
import net.rafgpereira.transpoapp.domain.model.Driver
import net.rafgpereira.transpoapp.domain.model.fakeDrivers
import net.rafgpereira.transpoapp.ui.common.ScaffoldAndSurface
import net.rafgpereira.transpoapp.ui.viewmodel.RequestCarOptionsViewModel

//TODO disable all buttons on click and enable once request/nav finished
@Composable
fun RequestCarOptionsScreen(
    modifier: Modifier,
    viewModel: RequestCarOptionsViewModel,
    navigateToHistoryScreenAction: () -> Unit,
    navigateUpAction: (() -> Unit)?,
) {
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

    RequestCarOptionsScreenContent(
        modifier = modifier,
        drivers = drivers.value,
        staticMapUrl = staticMapUrl,
        navigateUpAction = navigateUpAction,
        navigateToHistoryScreenAction = navigateToHistoryScreenAction,
    )
}

@Composable
fun RequestCarOptionsScreenContent(
    modifier: Modifier,
    staticMapUrl: String,
    drivers: List<Driver>,
    navigateUpAction: (() -> Unit)?,
    navigateToHistoryScreenAction: () -> Unit,
) = ScaffoldAndSurface(
        modifier = modifier,
        title = stringResource(R.string.requestcaroptions_screen_title),
        navigateUpAction = navigateUpAction,
    ) {
        Column {
            RouteMap(modifier, staticMapUrl)
            LazyColumn(contentPadding = PaddingValues(dimensionResource(R.dimen.space))) {
                items(
                    count = drivers.size,
                    itemContent = { index ->
                        DriverCard(
                            modifier = modifier,
                            driver = drivers[index],
                            chooseAction = navigateToHistoryScreenAction,
                        )
                    },
                )
            }
        }
    }

@Composable
fun RouteMap(modifier: Modifier, imageUrl: String) {
    Log.i("Static Maps Url", "RouteMap: $imageUrl")
    AsyncImage(
        modifier = modifier.size(400.dp),
        model = imageUrl,
        contentDescription = "Mapa estático da rota"
    )
}

@Composable
fun DriverCard(
    modifier: Modifier,
    driver: Driver,
    chooseAction: () -> Unit,
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
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.half_unit)),
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            LabelAndInformation(stringResource(R.string.requestcaroptions_name_label), driver.name)
            driver.vehicle?.let {
                LabelAndInformation(
                    stringResource(R.string.requestcaroptions_vehicle_label), it
                )
            }
            LabelAndInformation(
                stringResource(R.string.requestcaroptions_rating_label),
                    driver.review?.rating.toString()
            )
        }
        if (driver.description?.isEmpty()?.not() == true) {
            LabelAndInformation(
                stringResource(R.string.requestcaroptions_desc_label),
                driver.description
            )
        }
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            LabelAndInformation(stringResource(R.string.requestcaroptions_cost_label), driver.value.toString())
            //TODO add on-going request animation
            Button(
                modifier = modifier.width(dimensionResource(R.dimen.button_width)),
                onClick = chooseAction,
                contentPadding = PaddingValues(dimensionResource(R.dimen.zero_dp)),
            ) {
                Text(
                    text = stringResource(R.string.requestcaroptions_choose_button_text),
                    maxLines = 1,
                )
            }
        }
    }
}

@Composable
fun LabelAndInformation(label: String, info: String) =
    Column {
        Text(label)
        Text(
            text = info,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
    }

@Composable
@Preview(showSystemUi = false, showBackground = true)
fun LabelAndInformationPreview() = LabelAndInformation("nome", fakeDrivers[0].name)

@Composable
@Preview(showSystemUi = false, showBackground = false)
fun DriverCardPreview() = DriverCard(Modifier, fakeDrivers[0]) {}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun RequestCarOptionsScreenContentPreview() =
    RequestCarOptionsScreenContent(Modifier, "", fakeDrivers, {}) {}