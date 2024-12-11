package net.rafgpereira.transpoapp.data.model

import net.rafgpereira.transpoapp.domain.model.Driver
import net.rafgpereira.transpoapp.domain.model.LatLng

data class EstimateResult(
    val origin: LatLng,
    val destination: LatLng,
    val distance: Int,
    val duration: Int,
    val options: List<Driver>,
    val routeResponse: EstimateRouteResult
)

data class EstimateRouteResult(
    val routes: List<RouteResult>
)

data class RouteResult(
    val legs: List<Leg>
)

data class Leg(
    val steps: List<Step>
)

data class Step(
    val startLocation: StartLocation,
    val endLocation: EndLocation
)

data class StartLocation(val latLng: LatLng)
data class EndLocation(val latLng: LatLng)