package net.rafgpereira.transpoapp.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class AppDestination {

    @Serializable
    data object RequestCarScreen : AppDestination()

    @Serializable
    data object RequestRideOptionsScreen : AppDestination()

    @Serializable
    data object HistoryScreen : AppDestination()
}