package net.rafgpereira.transpoapp.ui.common

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import net.rafgpereira.transpoapp.R

@Composable
fun ScaffoldAndSurface(
    modifier: Modifier,
    title: String? = null,
    navigateUpAction: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    val appName = stringResource(R.string.app_name)
    val screenTitle = if (title.isNullOrEmpty()) appName else "$appName: $title"

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            ThemedTopAppBar(
                modifier = modifier,
                title = screenTitle,
                navigateUpAction = navigateUpAction
            )
        },
    ) { innerPadding ->
        Surface(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
            content = content
        )
    }
}