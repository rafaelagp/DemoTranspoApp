package net.rafgpereira.transpoapp.ui.common

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import net.rafgpereira.transpoapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorAlertDialog(
    modifier: Modifier,
    errorMessage: String,
    onDismiss: () -> Unit,
) {
    BasicAlertDialog(onDismissRequest = { onDismiss() },) {
        Surface(
            modifier = modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
        ) {
            Column(modifier = Modifier.padding(dimensionResource(R.dimen.double_space))) {
                Text(
                    text = stringResource(R.string.error_dialog_title),
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.double_space)))
                Text(text = errorMessage, style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.double_space)))
                TextButton(
                    onClick = { onDismiss() },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(stringResource(R.string.error_dialog_button))
                }
            }
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
@Preview
fun ErrorAlertDialogPreview() = ErrorAlertDialog(
    Modifier, stringResource(R.string.error_dialog_preview_message),
) {}