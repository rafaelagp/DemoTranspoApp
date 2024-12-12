package net.rafgpereira.transpoapp.ui.common

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardColors
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import net.rafgpereira.transpoapp.R

@Composable
fun ThemedElevatedCard(modifier: Modifier, content: @Composable ColumnScope.() -> Unit) {
    ElevatedCard(
        modifier = modifier.padding(dimensionResource(R.dimen.space)),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = Color.Black,
            disabledContainerColor = Color.DarkGray,
            disabledContentColor = Color.DarkGray
        ),
        content = content
    )
}