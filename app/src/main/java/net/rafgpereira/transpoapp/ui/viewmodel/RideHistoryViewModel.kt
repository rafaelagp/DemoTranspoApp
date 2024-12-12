package net.rafgpereira.transpoapp.ui.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import net.rafgpereira.transpoapp.domain.repository.IRepository
import net.rafgpereira.transpoapp.ui.common.UiState
import javax.inject.Inject

@HiltViewModel
class RideHistoryViewModel @Inject constructor(repository: IRepository)
: BaseViewModel(repository) {
    val drivers = repository.drivers
    val rides = repository.rideHistory
    val userId = MutableStateFlow("")
    val driverId = MutableStateFlow(0)

    fun getRideHistory() = viewModelScope.launch {
        _uiState.value = UiState.LOADING
        repository.getHistory(
            userId = userId.value,
            driverId = driverId.value,
            onSuccess = { _uiState.value = UiState.START },
            onFailure = { _uiState.value = UiState.START }
        )
    }

    fun clearRideHistory() = repository.clearRideHistory()
}