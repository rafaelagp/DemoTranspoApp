package net.rafgpereira.transpoapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.flow.MutableStateFlow
import net.rafgpereira.transpoapp.R
import net.rafgpereira.transpoapp.ui.common.ErrorAlertDialog
import net.rafgpereira.transpoapp.ui.common.ScaffoldAndSurface
import net.rafgpereira.transpoapp.ui.viewmodel.RequestCarViewModel
import net.rafgpereira.transpoapp.ui.viewmodel.UiState

@Composable
fun RequestCarScreen(
    modifier: Modifier,
    viewModel: RequestCarViewModel,
    navigateToOptionsScreen: () -> Unit,
) {
    val userId = viewModel.userId
    val origin = viewModel.origin
    val destination = viewModel.destination
    val errorMessage = viewModel.errorMessage.collectAsState(null).value
    val uiState = viewModel.uiState.collectAsState().value

    if (uiState == UiState.NAVIGATE) {
        navigateToOptionsScreen()
        viewModel.clearUiState()
    }

    if (errorMessage != null && errorMessage.isEmpty().not())
        ErrorAlertDialog(modifier, errorMessage) { viewModel.clearErrorMessage() }

    RequestCarScreenContent(
        modifier, uiState, userId, origin, destination, { viewModel.estimate() }
    )
}

@Composable
fun RequestCarScreenContent(
    modifier: Modifier,
    uiState: UiState,
    userId: MutableStateFlow<String>,
    origin: MutableStateFlow<String>,
    destination: MutableStateFlow<String>,
    requestEstimate: () -> Unit,
) = ScaffoldAndSurface(modifier = modifier) {
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
                label = {
                    Text(text = stringResource(R.string.requestcar_userid_field_title),)
                },
                enabled = uiState == UiState.START
            )
            TextField(
                modifier = modifier.fillMaxWidth(),
                value = origin.collectAsState().value,
                onValueChange = { origin.value = it },
                label = {
                    Text(text = stringResource(R.string.requestcar_originaddress_field_title),)
                },
                enabled = uiState == UiState.START
            )
            TextField(
                modifier = modifier.fillMaxWidth(),
                value = destination.collectAsState().value,
                onValueChange = { destination.value = it },
                label = {
                    Text(text =
                    stringResource(R.string.requestcar_destinationaddress_field_title),
                    )
                },
                enabled = uiState == UiState.START
            )
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
                onClick = requestEstimate,
                enabled = uiState == UiState.START
            ) {
                if (uiState != UiState.START)
                    CircularProgressIndicator(
                        modifier.size(dimensionResource(R.dimen.progress_indicator_size)),
                    )
                else Text(stringResource(R.string.requestcar_request_button_text),)
            }
        }
    }

@Composable
@Preview(showSystemUi = true, showBackground = true)
fun RequestCarScreenContentPreview() = RequestCarScreenContent(
    Modifier, UiState.START, MutableStateFlow(""), MutableStateFlow(""),
    MutableStateFlow("")) {}