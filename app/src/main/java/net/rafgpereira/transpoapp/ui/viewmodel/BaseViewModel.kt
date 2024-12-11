package net.rafgpereira.transpoapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import net.rafgpereira.transpoapp.domain.repository.IRepository
import net.rafgpereira.transpoapp.ui.common.UiState

open class BaseViewModel (protected val repository: IRepository,) : ViewModel() {
    val errorMessage = repository.errorMessage

    protected val _uiState = MutableStateFlow(UiState.START)
    val uiState = _uiState.asStateFlow()

    fun clearUiState() { _uiState.value = UiState.START }

    fun clearErrorMessage() =
        viewModelScope.launch { repository.clearErrorMessage() }
}