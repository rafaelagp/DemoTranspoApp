package net.rafgpereira.transpoapp.data.network

import net.rafgpereira.transpoapp.data.model.ConfirmRequestBody
import net.rafgpereira.transpoapp.data.model.ConfirmResponse
import net.rafgpereira.transpoapp.data.model.EstimateRequestBody
import net.rafgpereira.transpoapp.data.model.EstimateResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface IApiService {

    @POST("/ride/estimate")
    suspend fun getEstimate(@Body requestBody: EstimateRequestBody) : Response<EstimateResponse>

    @PATCH("/ride/confirm")
    suspend fun confirm(@Body requestBody: ConfirmRequestBody) : Response<ConfirmResponse>

    @GET("/ride/{userId}?driver_id={driverId}")
    suspend fun getHistory(
        @Path("userId") userId: String,
        @Path("driverId") driverId: String
    )

    companion object {
        const val BASE_URL =
            "https://xd5zl5kk2yltomvw5fb37y3bm40vsyrx.lambda-url.sa-east-1.on.aws"
    }
}