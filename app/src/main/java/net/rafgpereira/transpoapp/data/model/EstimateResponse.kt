package net.rafgpereira.transpoapp.data.model

import net.rafgpereira.transpoapp.domain.model.Driver
import net.rafgpereira.transpoapp.domain.model.LatLng

data class EstimateResponse(
    val origin: LatLng,
    val destination: LatLng,
    val duration: Long,
    val options: List<Driver>,
    val routeResponse: EstimateResponseRouteResult
)

data class EstimateResponseRouteResult(
    val routes: List<EstimateResponseRoute>
)

data class EstimateResponseRoute(
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