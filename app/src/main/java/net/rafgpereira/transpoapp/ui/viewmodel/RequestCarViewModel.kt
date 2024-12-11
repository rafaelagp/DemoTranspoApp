package net.rafgpereira.transpoapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import net.rafgpereira.transpoapp.data.exception.RequestError
import net.rafgpereira.transpoapp.data.network.getJsonObject
import net.rafgpereira.transpoapp.domain.repository.IRepository
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

    private val _errorMessage = MutableSharedFlow<String?>()
    val errorMessage = _errorMessage.asSharedFlow()

    private val _uiState = MutableStateFlow(UiState.START)
    val uiState = _uiState.asStateFlow()

    fun estimate() = viewModelScope.launch {
        _uiState.value = UiState.LOADING

        try {
            val result = repository.getEstimate(
                userId = userId.value,
                origin = origin.value,
                destination = destination.value
            )

            if (result.isSuccessful) {
                _uiState.value = UiState.NAVIGATE
            }
            else {
                result.errorBody()?.getJsonObject<RequestError>()?.errorDescription?.let {
                    _errorMessage.emit(it)
                }
                _uiState.value = UiState.START
            }
        } catch (ex: Exception) {
            ex.message?.let { _errorMessage.emit(it) }
            _uiState.value = UiState.START
        }
    }

    fun clearErrorMessage() = viewModelScope.launch { _errorMessage.emit(null) }

    fun clearUiState() { _uiState.value = UiState.START }
}

enum class UiState {
    START,
    LOADING,
    NAVIGATE
}