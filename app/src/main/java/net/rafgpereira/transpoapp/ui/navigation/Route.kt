package net.rafgpereira.transpoapp.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Route {

    @Serializable
    data object RequestCarScreen : Route()
}