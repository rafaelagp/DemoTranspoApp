package net.rafgpereira.transpoapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import net.rafgpereira.transpoapp.domain.model.fakeDrivers
import net.rafgpereira.transpoapp.ui.screen.HistoryScreen
import net.rafgpereira.transpoapp.ui.screen.RequestCarOptionsScreen
import net.rafgpereira.transpoapp.ui.screen.RequestCarScreen
import net.rafgpereira.transpoapp.ui.viewmodel.RequestCarViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import net.rafgpereira.transpoapp.ui.viewmodel.RequestCarOptionsViewModel

@Composable
fun MyNavHost(navController: NavHostController) =
    NavHost(
        navController = navController,
        startDestination = AppDestination.RequestCarScreen
    ) {
        composable<AppDestination.RequestCarScreen> {
            RequestCarScreen(
                modifier = Modifier,
                viewModel = hiltViewModel<RequestCarViewModel>(),
                navigateToOptionsScreen = {
                    navController.navigate(AppDestination.RequestCarOptionsScreen)
                },
            )
        }
        composable<AppDestination.RequestCarOptionsScreen> {
            RequestCarOptionsScreen(
                modifier = Modifier,
                viewModel = hiltViewModel<RequestCarOptionsViewModel>(),
                navigateToHistoryScreen = {
                    navController.navigate(AppDestination.HistoryScreen)
                },
                navigateUp = { navController.navigateUp() },
            )
        }
        composable<AppDestination.HistoryScreen> {
            HistoryScreen(
                modifier = Modifier,
                navigateUp = { navController.navigateUp() },
                drivers = fakeDrivers, //TODO remove fake data
            )
        }
    }