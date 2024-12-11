package net.rafgpereira.transpoapp.ui.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import net.rafgpereira.transpoapp.domain.model.Driver
import net.rafgpereira.transpoapp.domain.repository.IRepository
import net.rafgpereira.transpoapp.ui.common.UiState
import javax.inject.Inject

@HiltViewModel
class RequestCarOptionsViewModel @Inject constructor(repository: IRepository,)
: BaseViewModel(repository) {
    val drivers = repository.drivers
    val route = repository.route

    fun confirm(driver: Driver) = viewModelScope.launch {
        _uiState.value = UiState.LOADING
        repository.confirm(
            driverId = driver.id,
            driverName = driver.name,
            value = driver.value,
            onSuccess = { _uiState.value = UiState.NAVIGATE },
            onFailure = { _uiState.value = UiState.START }
        )
    }
}