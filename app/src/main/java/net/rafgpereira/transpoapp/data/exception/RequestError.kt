package net.rafgpereira.transpoapp.data.exception

import com.google.gson.annotations.SerializedName

data class RequestError(
    @SerializedName("error_code") val errorCode: String,
    @SerializedName("error_description") val errorDescription: String,
)
