package net.rafgpereira.transpoapp.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Route {

    @Serializable
    data object RequestCarScreen : Route()

    @Serializable
    data object RequestCarOptionsScreen : Route()

    @Serializable
    data object HistoryScreen : Route()
}