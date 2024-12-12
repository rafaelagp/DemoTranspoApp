package net.rafgpereira.transpoapp.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.flow.MutableStateFlow
import net.rafgpereira.transpoapp.R
import net.rafgpereira.transpoapp.domain.model.Driver
import net.rafgpereira.transpoapp.domain.model.Ride
import net.rafgpereira.transpoapp.domain.model.fakeDrivers
import net.rafgpereira.transpoapp.ui.common.ErrorAlertDialog
import net.rafgpereira.transpoapp.ui.common.ScaffoldAndSurface
import net.rafgpereira.transpoapp.ui.common.TextOrProgressIndicator
import net.rafgpereira.transpoapp.ui.common.ThemedElevatedCard
import net.rafgpereira.transpoapp.ui.common.UiState
import net.rafgpereira.transpoapp.ui.viewmodel.RideHistoryViewModel
import net.rafgpereira.transpoapp.util.debounced
import java.text.NumberFormat

@Composable
fun RideHistoryScreen(
    modifier: Modifier,
    viewModel: RideHistoryViewModel,
    navigateUp: (() -> Unit)?,
) {
    val errorMessage = viewModel.errorMessage.collectAsState(null)
    val drivers = viewModel.drivers.collectAsState()
    val rides = viewModel.rides.collectAsState()
    val uiState = viewModel.uiState.collectAsState()
    val driverId = viewModel.driverId
    val userId = viewModel.userId

    if (errorMessage.value != null)
        ErrorAlertDialog(modifier, errorMessage.value.toString()) { viewModel.clearErrorMessage() }

    RideHistoryScreenContent(
        modifier = modifier,
        uiState = uiState.value,
        driverId = driverId,
        userId = userId,
        rides = rides.value,
        drivers = drivers.value,
        getRideHistory = {
            viewModel.clearRideHistory()
            viewModel.getRideHistory()
        },
        navigateUp = navigateUp,
        clearRideHistory = { viewModel.clearRideHistory() },
    )
}

@Composable
fun RideHistoryScreenContent(
    modifier: Modifier,
    uiState: UiState,
    driverId: MutableStateFlow<Int>,
    userId: MutableStateFlow<String>,
    drivers: List<Driver>,
    rides: List<Ride>,
    getRideHistory: () -> Unit,
    navigateUp: (() -> Unit)?,
    clearRideHistory: () -> Unit,
) = ScaffoldAndSurface(
    modifier = modifier,
    title = stringResource(R.string.history_screen_title),
    navigateUp = navigateUp,
) {
    Column(
        modifier = modifier
            .padding(dimensionResource(R.dimen.screen_padding))
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(
            space = dimensionResource(R.dimen.space),
            alignment = Alignment.Top,
        ),
        horizontalAlignment = Alignment.Start,
    ) {
        TextField(
            modifier = modifier.width(dimensionResource(R.dimen.small_textfield_width)),
            value = userId.collectAsState().value,
            onValueChange = { userId.value = it },
            label = { Text(text = stringResource(R.string.requestride_userid_field_title),) },
            enabled = uiState == UiState.START
        )
        val driversWithSpace =
            mutableListOf<Driver>().also { it.add(Driver()) }.also { it.addAll(drivers) }.toList()
        DriverDropdownAndFilter(
            modifier, uiState, driverId, driversWithSpace, getRideHistory, clearRideHistory
        )
        LazyColumn(contentPadding = PaddingValues(dimensionResource(R.dimen.space))) {
            items (
                count = rides.size,
                itemContent = { index ->
                    val ride = rides[index]
                    RideCard(modifier, ride)
                },
            )
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun RideCard(
    modifier: Modifier,
    ride: Ride,
) = ThemedElevatedCard(modifier) {
    Column(
        modifier = modifier.padding(dimensionResource(R.dimen.double_space)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space)),
    ) {
        LabelAndInformation(stringResource(R.string.ridehistory_date), ride.date)
        LabelAndInformation(
            stringResource(R.string.requestrideoptions_name_label), ride.driver.name
        )
        LabelAndInformation(
            stringResource(R.string.requestride_originaddress_field_title).lowercase(), ride.origin
        )
        LabelAndInformation(
            stringResource(R.string.requestride_destinationaddress_field_title).lowercase(),
            ride.destination
        )
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.space)),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            LabelAndInformation(
                stringResource(R.string.ridehistory_distance_field_title).lowercase(),
                String.format("%.2f", ride.distance) + " Km"
            )
            LabelAndInformation(
                stringResource(R.string.ridehistory_duration_field_title).lowercase(),
                ride.duration + " mins"
            )
            LabelAndInformation(
                stringResource(R.string.requestrideoptions_cost_label),
                NumberFormat.getCurrencyInstance().format(ride.value),
            )
        }
    }
}

@Composable
fun DriverDropdownAndFilter(
    modifier: Modifier,
    uiState: UiState,
    driverId: MutableStateFlow<Int>,
    drivers: List<Driver>,
    getRideHistory: () -> Unit,
    clearRideHistory: () -> Unit,
) = Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically,
) {
    DriverDropdownMenu(
        modifier, uiState, driverId, drivers, false, clearRideHistory
    )
    Button(
        modifier = modifier.width(dimensionResource(R.dimen.button_width)),
        onClick = debounced(getRideHistory),
        enabled = uiState == UiState.START
    ) {
        TextOrProgressIndicator(
            modifier = modifier,
            showIndicatorCondition = uiState != UiState.START,
            text = stringResource(R.string.history_filter_button_text),
        )
    }
}

@Composable
fun DriverDropdownMenu(
    modifier: Modifier,
    uiState: UiState,
    driverId: MutableStateFlow<Int>,
    drivers: List<Driver>,
    isDropdownExpanded: Boolean = false,
    clearRideHistory: () -> Unit,
) {
    val dropdownExpandedState = remember { mutableStateOf(isDropdownExpanded) }
    val itemPosition = remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier
            .width(dimensionResource(R.dimen.small_textfield_width))
            .background(MaterialTheme.colorScheme.surfaceContainerHighest),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box {
            TextField(
                modifier = modifier
                    .width(dimensionResource(R.dimen.small_textfield_width))
                    .clickable { if (uiState == UiState.START) dropdownExpandedState.value = true },
                value = drivers[itemPosition.intValue].name,
                onValueChange = {},
                label = { Text(stringResource(R.string.history_dropdown_label),) },
                enabled = false,
                trailingIcon = { Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = stringResource(R.string.history_dropdown_icon_desc),
                ) },
                colors = if (uiState == UiState.START) {
                    TextFieldDefaults.colors(
                        disabledTextColor = MaterialTheme.colorScheme.onSurface,
                        disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        disabledTrailingIconColor = MaterialTheme.colorScheme.onSurface,
                        disabledIndicatorColor =
                        if (dropdownExpandedState.value)
                            MaterialTheme.colorScheme.surfaceContainerHighest
                        else MaterialTheme.colorScheme.onSurface,
                    )
                } else TextFieldDefaults.colors()
            )
            DropdownMenu(
                modifier = modifier.width(dimensionResource(R.dimen.small_textfield_width)),
                expanded = dropdownExpandedState.value,
                onDismissRequest = { dropdownExpandedState.value = false },
                containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                shape = RectangleShape,
                shadowElevation = dimensionResource(R.dimen.zero_dp),
            ) {
                drivers.forEachIndexed { index, driver ->
                    DropdownMenuItem(
                        text = { Text(text = driver.name,) },
                        onClick = {
                            dropdownExpandedState.value = false
                            itemPosition.intValue = index
                            driverId.value = driver.id
                            clearRideHistory()
                        }
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showSystemUi = false, showBackground = true)
fun DriverDropdownMenuPreview() = DriverDropdownMenu(
    Modifier, UiState.START, MutableStateFlow(0), fakeDrivers, true,
) {}

@Composable
@Preview
fun RideCardPreview() = RideCard(Modifier, Ride(
    id = 96,
    date = "2024-12-12T03:54:06",
    origin = "815 Barrows Rapid, 779, Wymanboro, 16566",
    destination = "64261 Evelyn Oval, 439, Winstonburgh, 70885",
    distance = 9.6398210157435,
    duration = "10:51",
    driver = fakeDrivers[0],
    value = 172.39936035728795)
)

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun HistoryScreenContentPreview() = RideHistoryScreenContent(
    Modifier, UiState.START, MutableStateFlow(0), MutableStateFlow(""), fakeDrivers,
    mutableListOf(), {}, {}) {}