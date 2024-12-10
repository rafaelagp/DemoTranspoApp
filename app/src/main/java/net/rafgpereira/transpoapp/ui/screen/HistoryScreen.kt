package net.rafgpereira.transpoapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import net.rafgpereira.transpoapp.R
import net.rafgpereira.transpoapp.ui.common.ScaffoldAndSurface

//TODO add nav back arrow function
@Composable
fun HistoryScreen(
    modifier: Modifier,
) = ScaffoldAndSurface(
    modifier = modifier,
    title = stringResource(R.string.history_screen_title),
) {
    val userId by remember { mutableStateOf("") }

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
            onValueChange = {},
            label = { Text(text = stringResource(R.string.requestcar_userid_field_title),) },
        )
        DriverDropdownAndFilter(modifier = modifier)
    }
}

@Composable
fun DriverDropdownAndFilter(modifier: Modifier,) = Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
) {
    TextField(
        modifier = modifier.width(dimensionResource(R.dimen.small_textfield_width)),
        value = "",
        onValueChange = {},
        label = { Text(text = stringResource(R.string.history_dropdown_label),) },
    )
    //TODO disable text fields and button on click and enable when request finishes
    //TODO add animation while request on-going
    //TODO implement request
    Button(
        modifier = modifier.width(dimensionResource(R.dimen.button_width)),
        onClick = {},
    ) { Text(stringResource(R.string.history_filter_button_text),) }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun HistoryScreenPreview() = HistoryScreen(Modifier)