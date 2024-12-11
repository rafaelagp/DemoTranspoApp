package net.rafgpereira.transpoapp.data.model

import com.google.gson.annotations.SerializedName
import net.rafgpereira.transpoapp.domain.model.Ride

data class HistoryResult(
    @SerializedName("customer_id") val userId: String,
    val rides: List<Ride>
)
