package net.rafgpereira.transpoapp.data.model

import com.google.android.gms.maps.model.LatLng
import net.rafgpereira.transpoapp.domain.model.Driver

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
    val startLocation: LatLng,
    val endLocation: LatLng
)