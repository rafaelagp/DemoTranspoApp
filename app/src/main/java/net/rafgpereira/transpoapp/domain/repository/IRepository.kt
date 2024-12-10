package net.rafgpereira.transpoapp.domain.repository

import net.rafgpereira.transpoapp.data.model.EstimateResponse

interface IRepository {
    suspend fun getEstimate(
       userId: String,
       origin: String,
       destination: String
    ) : EstimateResponse
}