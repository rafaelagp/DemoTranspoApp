package net.rafgpereira.transpoapp.domain.repository

import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import net.rafgpereira.transpoapp.domain.model.Driver

interface IRepository {
    val drivers: StateFlow<List<Driver>>
    val errorMessage: SharedFlow<String?>

    suspend fun getEstimate(
       userId: String,
       origin: String,
       destination: String,
       onSuccess: () -> Unit,
       onFailure: () -> Unit,
    )

    suspend fun clearErrorMessage()
}