package net.rafgpereira.transpoapp.domain.repository

import net.rafgpereira.transpoapp.data.model.EstimateResponse
import retrofit2.Response

interface IRepository {
    suspend fun getEstimate(
       userId: String,
       origin: String,
       destination: String
    ) : Response<EstimateResponse>
}