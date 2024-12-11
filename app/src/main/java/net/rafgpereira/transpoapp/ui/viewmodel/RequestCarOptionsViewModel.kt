package net.rafgpereira.transpoapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import net.rafgpereira.transpoapp.domain.repository.IRepository
import net.rafgpereira.transpoapp.ui.common.UiState
import javax.inject.Inject

@HiltViewModel
class RequestCarOptionsViewModel @Inject constructor(repository: IRepository,)
: ViewModel() {

    val drivers = repository.drivers
    val route = repository.route

    private val _uiState = MutableStateFlow(UiState.LOADING)
    val uiState = _uiState.asStateFlow()

    fun resetUiState() { _uiState.value = UiState.START }

    fun setUiStateNavigate() { _uiState.value = UiState.NAVIGATE }
}