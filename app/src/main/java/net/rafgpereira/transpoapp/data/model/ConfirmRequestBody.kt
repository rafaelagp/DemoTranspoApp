package net.rafgpereira.transpoapp.data.model

import com.google.gson.annotations.SerializedName

data class ConfirmRequestDriver(
    val id: Long,
    val name: String
)

data class ConfirmRequestBody(
    @SerializedName("customer_id") val userId: String,
    val origin: String,
    val destination: String,
    val distance: Long,
    val duration: String,
    val driver: ConfirmRequestDriver,
    val value: Double
)
