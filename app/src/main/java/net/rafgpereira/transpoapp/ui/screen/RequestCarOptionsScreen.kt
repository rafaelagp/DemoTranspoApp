package net.rafgpereira.transpoapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CardColors
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState
import net.rafgpereira.transpoapp.R
import net.rafgpereira.transpoapp.domain.model.TempDriver
import net.rafgpereira.transpoapp.domain.model.fakeDrivers
import net.rafgpereira.transpoapp.ui.common.ScaffoldAndSurface

val fakeOriginLatLng = LatLng(-23.5215624, -46.763286699999995)

//TODO disable all buttons on click and enable once request/nav finished
//TODO add navigate back arrow
@Composable
fun RequestCarOptionsScreen(
    modifier: Modifier,
    drivers: Array<TempDriver>,
    navigateToHistoryScreenAction: () -> Unit, //TODO pass driver thru action?
    hasBackStackEntry: Boolean,
    navigateUpAction: (() -> Unit)?,
) = ScaffoldAndSurface(
        modifier = modifier,
        title = stringResource(R.string.requestcaroptions_screen_title),
        hasBackStackEntry = hasBackStackEntry,
        navigateUpAction = navigateUpAction,
    ) {
        Column {
            RouteMap(fakeOriginLatLng)
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

//TODO draw route
@Composable
fun RouteMap(originPosition: LatLng) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(originPosition, 15f)
    }
    GoogleMap(
        modifier = Modifier.height(300.dp),
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(
            compassEnabled = false,
            indoorLevelPickerEnabled = false,
            mapToolbarEnabled = false,
            myLocationButtonEnabled = false,
            rotationGesturesEnabled = false,
            scrollGesturesEnabled = false,
            scrollGesturesEnabledDuringRotateOrZoom = false,
            tiltGesturesEnabled = false,
            zoomControlsEnabled = false,
            zoomGesturesEnabled = false
        ),
    )
}

@Composable
fun DriverCard(
    modifier: Modifier,
    driver: TempDriver,
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
            LabelAndInformation(
                stringResource(R.string.requestcaroptions_vehicle_label), driver.vehicle
            )
            LabelAndInformation(
                stringResource(R.string.requestcaroptions_rating_label), driver.rating
            )
        }
        if (driver.desc.isEmpty().not()) {
            LabelAndInformation(stringResource(R.string.requestcaroptions_desc_label), driver.desc)
        }
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            LabelAndInformation(stringResource(R.string.requestcaroptions_cost_label), driver.cost)
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
fun RequestCarOptionsScreenPreview() = RequestCarOptionsScreen(
    Modifier, fakeDrivers, {}, true) {}