package net.rafgpereira.transpoapp.data.repository

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import net.rafgpereira.transpoapp.data.exception.RequestError
import net.rafgpereira.transpoapp.data.model.ConfirmRequestBody
import net.rafgpereira.transpoapp.data.model.ConfirmRequestDriver
import net.rafgpereira.transpoapp.data.model.EstimateRequestBody
import net.rafgpereira.transpoapp.data.network.IApiService
import net.rafgpereira.transpoapp.util.getJsonObject
import net.rafgpereira.transpoapp.domain.model.Driver
import net.rafgpereira.transpoapp.domain.model.LatLng
import net.rafgpereira.transpoapp.domain.model.Ride
import net.rafgpereira.transpoapp.domain.repository.IRepository
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(
    private val apiService: IApiService,
) : IRepository {
    private val _drivers = MutableStateFlow<List<Driver>>(listOf())
    override val drivers = _drivers.asStateFlow()

    private val _rides = MutableStateFlow<List<Ride>>(listOf())
    override val rides = _rides.asStateFlow()

    private val _route = MutableStateFlow<List<LatLng>>(listOf())
    override val route = _route.asStateFlow()

    private val _errorMessage = MutableSharedFlow<String?>()
    override val errorMessage = _errorMessage.asSharedFlow()

    override suspend fun getEstimate(
        userId: String,
        origin: String,
        destination: String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
    ) {
        try {
            apiService.getEstimate(EstimateRequestBody(userId, origin, destination)).let { result ->
                if (result.isSuccessful) {
                    result.body()?.options?.let { _drivers.emit(it) }
                    val steps = mutableListOf<LatLng>()
                    result.body()?.routeResponse?.routes?.forEach { route ->
                        route.legs.forEach { leg ->
                            leg.steps.forEach { step ->
                                steps.add(step.startLocation.latLng)
                                steps.add(step.endLocation.latLng)
                            }
                        }
                    }
                    _route.emit(steps)
                    onSuccess()
                } else handleFailedRequest(result, onFailure)
            }
        } catch (ex: Exception) {
            handleException(ex, onFailure)
        }
    }

    override suspend fun confirm(
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
    ) {
        try {
            apiService.confirm(
                ConfirmRequestBody(
                    userId, origin, destination, distance, duration,
                        ConfirmRequestDriver(driverId, driverName), value
                )
            ).let { result ->
                if (result.isSuccessful) onSuccess()
                else handleFailedRequest(result, onFailure)
            }
        } catch (ex: Exception) {
            handleException(ex, onFailure)
        }
    }

    override suspend fun getHistory(
        userId: String,
        driverId: Long,
        onSuccess: () -> Unit,
        onFailure: () -> Unit,
    ) {
        try {
            apiService.getHistory(userId, driverId).let { result ->
                if (result.isSuccessful) {
                    result.body()?.rides?.let { _rides.emit(it) }
                    onSuccess()
                } else handleFailedRequest(result, onFailure)
            }
        } catch (ex: Exception) {
            handleException(ex, onFailure)
        }
    }

    override suspend fun clearErrorMessage() { _errorMessage.emit(null) }

    private suspend fun <T> handleFailedRequest(result: Response<T>, onFailure: () -> Unit) {
        result.errorBody()?.getJsonObject<RequestError>()?.errorDescription?.let {
            _errorMessage.emit(it)
        }
        onFailure()
    }

    private suspend fun handleException(ex: Exception, onFailure: () -> Unit) {
        _errorMessage.emit(ex.localizedMessage)
        onFailure()
    }
}