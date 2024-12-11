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

@Composable
fun MyNavHost(navController: NavHostController) =
    NavHost(
        navController = navController,
        startDestination = Route.RequestCarScreen
    ) {
        composable<Route.RequestCarScreen> {
            RequestCarScreen(
                modifier = Modifier,
                viewModel = hiltViewModel<RequestCarViewModel>(),
                navigateToOptionsScreen = {
                    navController.navigate(Route.RequestCarOptionsScreen)
                },
            )
        }
        composable<Route.RequestCarOptionsScreen> {
            RequestCarOptionsScreen(
                modifier = Modifier,
                drivers = fakeDrivers, //TODO remove fake data
                navigateToHistoryScreenAction = { navController.navigate(Route.HistoryScreen) },
                navigateUpAction = { navController.navigateUp() },
            )
        }
        composable<Route.HistoryScreen> {
            HistoryScreen(
                modifier = Modifier,
                navigateUpAction = { navController.navigateUp() },
                drivers = fakeDrivers, //TODO remove fake data
            )
        }
    }