package net.rafgpereira.transpoapp.data.repository

import net.rafgpereira.transpoapp.data.model.EstimateRequestBody
import net.rafgpereira.transpoapp.data.model.EstimateResponse
import net.rafgpereira.transpoapp.data.network.IApiService
import net.rafgpereira.transpoapp.domain.repository.IRepository
import retrofit2.Response
import javax.inject.Inject

//TODO change return model
class Repository @Inject constructor(private val apiService: IApiService) : IRepository {
    override suspend fun getEstimate(
        userId: String,
        origin: String,
        destination: String,
    ): Response<EstimateResponse> = apiService.getEstimate(EstimateRequestBody(userId, origin, destination))
}