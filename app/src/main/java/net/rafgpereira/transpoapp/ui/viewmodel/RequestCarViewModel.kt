package net.rafgpereira.transpoapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import net.rafgpereira.transpoapp.domain.repository.IRepository
import net.rafgpereira.transpoapp.ui.common.UiState
import javax.inject.Inject

@HiltViewModel
class RequestCarViewModel @Inject constructor(private val repository: IRepository,) : ViewModel() {
    //TODO remove data
    private val userIdData = "CT01"
    private val originData = "Av. Pres. Kenedy, 2385 - Remédios, Osasco - SP, 02675-031"
    private val destinationData = "Av. Paulista, 1538 - Bela Vista, São Paulo - SP, 01310-200"

    val userId = MutableStateFlow(userIdData)//"")
    val origin = MutableStateFlow(originData)//"")
    val destination = MutableStateFlow(destinationData)//"")

    private val _uiState = MutableStateFlow(UiState.START)
    val uiState = _uiState.asStateFlow()

    val errorMessage = repository.errorMessage

    fun estimate() = viewModelScope.launch {
        _uiState.value = UiState.LOADING
        repository.getEstimate(
            userId = userId.value,
            origin = origin.value,
            destination = destination.value,
            onSuccess = { _uiState.value = UiState.NAVIGATE },
            onFailure = { _uiState.value = UiState.START }
        )
    }

    fun clearErrorMessage() = viewModelScope.launch { repository.clearErrorMessage() }

    fun clearUiState() { _uiState.value = UiState.START }
}