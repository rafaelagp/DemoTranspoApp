package net.rafgpereira.transpoapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import net.rafgpereira.transpoapp.domain.model.fakeDrivers
import net.rafgpereira.transpoapp.ui.common.ErrorAlertDialog
import net.rafgpereira.transpoapp.ui.common.ScaffoldAndSurface
import net.rafgpereira.transpoapp.ui.common.TextOrProgressIndicator
import net.rafgpereira.transpoapp.ui.common.UiState
import net.rafgpereira.transpoapp.ui.viewmodel.RideHistoryViewModel

@Composable
fun RideHistoryScreen(
    modifier: Modifier,
    viewModel: RideHistoryViewModel,
    navigateUp: (() -> Unit)?,
) {
    val errorMessage = viewModel.errorMessage.collectAsState(null)
    val drivers = viewModel.drivers.collectAsState()
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
        drivers = drivers.value,
        getRideHistory = { viewModel.getRideHistory() },
        navigateUp = navigateUp,
    )
}

@Composable
fun RideHistoryScreenContent(
    modifier: Modifier,
    uiState: UiState,
    driverId: MutableStateFlow<Int>,
    userId: MutableStateFlow<String>,
    drivers: List<Driver>,
    getRideHistory: () -> Unit,
    navigateUp: (() -> Unit)?,
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
            label = { Text(text = stringResource(R.string.requestcar_userid_field_title),) },
            enabled = uiState == UiState.START
        )
        DriverDropdownAndFilter(modifier, uiState, driverId, drivers, getRideHistory)
        //TODO show ride history here
    }
}

@Composable
fun DriverDropdownAndFilter(
    modifier: Modifier,
    uiState: UiState,
    driverId: MutableStateFlow<Int>,
    drivers: List<Driver>,
    getRideHistory: () -> Unit,
) = Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically,
) {
    DriverDropdownMenu(modifier, uiState, driverId, drivers)
    //TODO implement request
    Button(
        modifier = modifier.width(dimensionResource(R.dimen.button_width)),
        onClick = { getRideHistory() },
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
                colors = TextFieldDefaults.colors(
                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                    disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledTrailingIconColor = MaterialTheme.colorScheme.onSurface,
                    disabledIndicatorColor =
                        if (dropdownExpandedState.value)
                            MaterialTheme.colorScheme.surfaceContainerHighest
                        else MaterialTheme.colorScheme.onSurface,
                ),
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
    Modifier, UiState.START, MutableStateFlow(0), fakeDrivers, true
)

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun HistoryScreenContentPreview() = RideHistoryScreenContent(
    Modifier, UiState.START, MutableStateFlow(0), MutableStateFlow(""), fakeDrivers, {}
) {}