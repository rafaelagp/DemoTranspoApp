package net.rafgpereira.transpoapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import net.rafgpereira.transpoapp.ui.screen.RequestCarScreen
import net.rafgpereira.transpoapp.ui.theme.TranspoAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TranspoAppTheme {
                //TODO add NavHost
                RequestCarScreen(Modifier)
            }
        }
    }
}