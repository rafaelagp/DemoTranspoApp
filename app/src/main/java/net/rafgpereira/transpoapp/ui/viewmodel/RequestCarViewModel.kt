package net.rafgpereira.transpoapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.rafgpereira.transpoapp.data.model.EstimateResponse
import net.rafgpereira.transpoapp.domain.repository.IRepository
import javax.inject.Inject

@HiltViewModel
class RequestCarViewModel @Inject constructor(private val repository: IRepository) : ViewModel() {
    val userId = MutableStateFlow("")
    val origin = MutableStateFlow("")
    val destination = MutableStateFlow("")
    val response = MutableStateFlow<EstimateResponse?>(null)

    fun estimate() = viewModelScope.launch {
        response.value = repository.getEstimate(
            userId = userId.value,
            origin = origin.value,
            destination = destination.value
        )
    }
}