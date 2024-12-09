package net.rafgpereira.transpoapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import net.rafgpereira.transpoapp.ui.screen.RequestCarScreen

@Composable
fun MyNavHost(navController: NavHostController) =
    NavHost(
        navController = navController,
        startDestination = Route.RequestCarScreen
    ) {
        composable<Route.RequestCarScreen> {
            RequestCarScreen(Modifier)
        }
    }