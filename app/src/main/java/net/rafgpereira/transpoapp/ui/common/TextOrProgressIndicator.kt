package net.rafgpereira.transpoapp.ui.common

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import net.rafgpereira.transpoapp.R

@Composable
fun TextOrProgressIndicator(
    modifier: Modifier,
    showIndicatorCondition: Boolean,
    text: String,
) = if (showIndicatorCondition)
        CircularProgressIndicator(
            modifier.size(dimensionResource(R.dimen.progress_indicator_size)),
        )
    else Text(text,)