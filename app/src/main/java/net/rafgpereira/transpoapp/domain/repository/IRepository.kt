package net.rafgpereira.transpoapp.domain.repository

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import net.rafgpereira.transpoapp.domain.model.Driver
import net.rafgpereira.transpoapp.domain.model.LatLng
import net.rafgpereira.transpoapp.domain.model.Ride

interface IRepository {
    val errorMessage: SharedFlow<String?>
    val drivers: StateFlow<List<Driver>>
    val rideHistory: StateFlow<List<Ride>>
    val route: StateFlow<List<LatLng>>

    suspend fun getEstimate(
       userId: String,
       origin: String,
       destination: String,
       onSuccess: () -> Unit,
       onFailure: () -> Unit,
    )

    suspend fun confirm(
        driverId: Int,
        driverName: String,
        value: Double,
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
    )

    suspend fun getHistory(
        userId: String,
        driverId: Int,
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
    )

    suspend fun clearErrorMessage()
}