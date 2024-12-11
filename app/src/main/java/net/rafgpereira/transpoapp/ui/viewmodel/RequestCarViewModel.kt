package net.rafgpereira.transpoapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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

    private val _isBusy = MutableStateFlow(false)
    val isBusy = _isBusy.asStateFlow()

    private val _errorMessage = MutableSharedFlow<String>()
    val errorMessage = _errorMessage.asSharedFlow()

    private val _navigateToOptions = MutableSharedFlow<Boolean>()
    val shouldNavigateToOptions = _navigateToOptions.asSharedFlow()

    fun estimate() = viewModelScope.launch {
        _isBusy.value = true
        try {
            repository.getEstimate(
                userId = userId.value,
                origin = origin.value,
                destination = destination.value
            )
            _navigateToOptions.emit(true)
        } catch (ex: Exception) {
            //TODO improve exception handling
            ex.message?.let { _errorMessage.emit(it) }
            _isBusy.value = false
        }
    }

    fun clearErrorMessage() = viewModelScope.launch {
        _errorMessage.emit("")
    }

    fun clearIsBusy() { _isBusy.value = false }
}