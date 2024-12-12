package net.rafgpereira.transpoapp.data.model

import com.google.gson.annotations.SerializedName

data class EstimateRequestBody(
    @SerializedName("customer_id") val userId: String?,
    val origin: String?,
    val destination: String?
)
