package net.rafgpereira.transpoapp.data.model

import com.google.android.gms.maps.model.LatLng
import net.rafgpereira.transpoapp.domain.model.DriverReview

data class EstimateResponse(
    val origin: LatLng,
    val destination: LatLng,
    val distance: Long,
    val duration: Long,
    val options: List<EstimateResponseDriver>,
    //val routeResponse: EstimateResponseRouteResult
)

data class EstimateResponseDriver(
    val id: Long,
    val name: String,
    val description: String,
    val vehicle: String,
    val review: DriverReview,
    val value: Double,
)

//data class EstimateResponseRouteResult(
//    val routes: List<EstimateResponseRoute>,
//    val geocodingResults: GeocodingResult,
//)
//
//data class EstimateResponseRoute(
//    val legs: List<Leg>,
//    val distanceMeters: Long,
//    val duration: String,
//    val staticDuration: String,
//    val polyline: polyline,
//    val description: String,
//    val warnings: List<String>,
//    val viewport: LatLng,
//    val travelAdvisory: Map<String, Any>,
//    val localizedValues: LocalizedValues3,
//    val routeLabels: List<String>,
//    val polylineDetails: Map<String, Any>,
//)