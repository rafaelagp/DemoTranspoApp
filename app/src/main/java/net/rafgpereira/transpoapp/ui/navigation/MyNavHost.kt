package net.rafgpereira.transpoapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import net.rafgpereira.transpoapp.ui.screen.RideHistoryScreen
import net.rafgpereira.transpoapp.ui.screen.RequestRideOptionsScreen
import net.rafgpereira.transpoapp.ui.screen.RequestRideScreen
import net.rafgpereira.transpoapp.ui.viewmodel.RequestRideViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import net.rafgpereira.transpoapp.ui.viewmodel.RequestRideOptionsViewModel
import net.rafgpereira.transpoapp.ui.viewmodel.RideHistoryViewModel

@Composable
fun MyNavHost(navController: NavHostController) =
    NavHost(
        navController = navController,
        startDestination = AppDestination.RequestCarScreen
    ) {
        composable<AppDestination.RequestCarScreen> {
            RequestRideScreen(
                modifier = Modifier,
                viewModel = hiltViewModel<RequestRideViewModel>(),
                navigateToOptionsScreen = {
                    navController.navigate(AppDestination.RequestRideOptionsScreen)
                },
            )
        }
        composable<AppDestination.RequestRideOptionsScreen> {
            RequestRideOptionsScreen(
                modifier = Modifier,
                viewModel = hiltViewModel<RequestRideOptionsViewModel>(),
                navigateToHistoryScreen = {
                    navController.navigate(AppDestination.HistoryScreen)
                },
                navigateUp = { navController.navigateUp() },
            )
        }
        composable<AppDestination.HistoryScreen> {
            RideHistoryScreen(
                modifier = Modifier,
                viewModel = hiltViewModel<RideHistoryViewModel>(),
                navigateUp = { navController.navigateUp() },
            )
        }
    }