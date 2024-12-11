package net.rafgpereira.transpoapp.domain.repository

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import net.rafgpereira.transpoapp.domain.model.Driver
import net.rafgpereira.transpoapp.domain.model.RouteStep

interface IRepository {
    val drivers: StateFlow<List<Driver>>
    val route: StateFlow<List<RouteStep>>
    val errorMessage: SharedFlow<String?>

    suspend fun getEstimate(
       userId: String,
       origin: String,
       destination: String,
       onSuccess: () -> Unit,
       onFailure: () -> Unit,
    )

    suspend fun confirm(
        userId: String,
        origin: String,
        destination: String,
        distance: Long,
        duration: String,
        driverId: Long,
        driverName: String,
        value: Double,
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
    )

    suspend fun clearErrorMessage()
}