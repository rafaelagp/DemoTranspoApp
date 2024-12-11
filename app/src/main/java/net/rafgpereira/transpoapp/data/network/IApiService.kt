package net.rafgpereira.transpoapp.data.network

import net.rafgpereira.transpoapp.data.model.ConfirmRequestBody
import net.rafgpereira.transpoapp.data.model.ConfirmResult
import net.rafgpereira.transpoapp.data.model.EstimateRequestBody
import net.rafgpereira.transpoapp.data.model.EstimateResult
import net.rafgpereira.transpoapp.data.model.HistoryResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface IApiService {

    @POST("/ride/estimate")
    suspend fun getEstimate(@Body requestBody: EstimateRequestBody) : Response<EstimateResult>

    @PATCH("/ride/confirm")
    suspend fun confirm(@Body requestBody: ConfirmRequestBody) : Response<ConfirmResult>

    @GET("/ride/{userId}")
    suspend fun getHistory(
        @Path("userId") userId: String,
        @Query("driver_id") driverId: Int
    ) : Response<HistoryResult>

    companion object {
        const val BASE_URL =
            "https://xd5zl5kk2yltomvw5fb37y3bm40vsyrx.lambda-url.sa-east-1.on.aws"
    }
}