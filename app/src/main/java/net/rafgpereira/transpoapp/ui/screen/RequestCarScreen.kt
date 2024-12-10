package net.rafgpereira.transpoapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import net.rafgpereira.transpoapp.R
import net.rafgpereira.transpoapp.ui.common.ScaffoldAndSurface

@Composable
fun RequestCarScreen(
    modifier: Modifier,
    navigateToOptionsScreenAction: () -> Unit,
) {
    var userId by remember { mutableStateOf("") }
    var originAddress by remember { mutableStateOf("") }
    var destinationAddress by remember { mutableStateOf("") }

    ScaffoldAndSurface(modifier = modifier) {
        Column(
            modifier = modifier
                .padding(dimensionResource(R.dimen.screen_padding))
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(
                space = dimensionResource(R.dimen.space),
                alignment = Alignment.CenterVertically,
            ),
            horizontalAlignment = Alignment.Start,
        ) {
            TextField(
                modifier = modifier.width(dimensionResource(R.dimen.small_textfield_width)),
                value = userId,
                onValueChange = { userId = it },
                label = {
                    Text(text = stringResource(R.string.requestcar_userid_field_title),)
                },
            )
            TextField(
                modifier = modifier.fillMaxWidth(),
                value = originAddress,
                onValueChange = { originAddress = it },
                label = {
                    Text(text = stringResource(R.string.requestcar_originaddress_field_title),)
                },
            )
            TextField(
                modifier = modifier.fillMaxWidth(),
                value = destinationAddress,
                onValueChange = { destinationAddress = it },
                label = {
                    Text(text =
                        stringResource(R.string.requestcar_destinationaddress_field_title),
                    )
                },
            )
            //TODO disable text fields and button on click, enable once request/nav finished
            //TODO add on-going request animation
            //TODO implement request
            Button(
                modifier = modifier
                    .padding(
                        PaddingValues(
                            start = dimensionResource(R.dimen.zero_dp),
                            top = dimensionResource(R.dimen.space),
                            end = dimensionResource(R.dimen.zero_dp),
                            bottom = dimensionResource(R.dimen.zero_dp)
                        )
                    )
                    .align(Alignment.CenterHorizontally)
                    .width(dimensionResource(R.dimen.button_width)),
                onClick = navigateToOptionsScreenAction,
            ) { Text(stringResource(R.string.requestcar_request_button_text),) }
        }
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun RequestCarScreenPreview() = RequestCarScreen(Modifier) {}