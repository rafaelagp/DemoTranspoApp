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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import net.rafgpereira.transpoapp.R
import net.rafgpereira.transpoapp.domain.model.TempDriver
import net.rafgpereira.transpoapp.domain.model.fakeDrivers
import net.rafgpereira.transpoapp.ui.common.ScaffoldAndSurface

//TODO add nav back arrow function
@Composable
fun HistoryScreen(
    modifier: Modifier,
    navigateUpAction: (() -> Unit)?,
    drivers: Array<TempDriver>,
) = ScaffoldAndSurface(
    modifier = modifier,
    title = stringResource(R.string.history_screen_title),
    navigateUpAction = navigateUpAction,
) {
    var userId by remember { mutableStateOf("") }

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
            value = userId,
            onValueChange = { userId = it },
            label = { Text(text = stringResource(R.string.requestcar_userid_field_title),) },
        )
        DriverDropdownAndFilter(modifier, drivers)
    }
}

@Composable
fun DriverDropdownAndFilter(
    modifier: Modifier,
    drivers: Array<TempDriver>,
) = Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically,
) {
    DriverDropdownMenu(modifier, drivers)
    //TODO disable text fields and button on click and enable when request finishes
    //TODO add animation while request on-going
    //TODO implement request
    Button(
        modifier = modifier.width(dimensionResource(R.dimen.button_width)),
        onClick = {},
    ) { Text(stringResource(R.string.history_filter_button_text),) }
}

@Composable
fun DriverDropdownMenu(
    modifier: Modifier,
    drivers: Array<TempDriver>,
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
                    .clickable { dropdownExpandedState.value = true },
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
                        }
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showSystemUi = false, showBackground = true)
fun DriverDropdownMenuPreview() = DriverDropdownMenu(Modifier, fakeDrivers, true)

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun HistoryScreenPreview() = HistoryScreen(Modifier, {}, fakeDrivers)