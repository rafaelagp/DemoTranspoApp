package net.rafgpereira.transpoapp.domain.model

data class Ride(
    val id: Long,
    val date: String,
    val origin: String,
    val destination: String,
    val distance: Int,
    val duration: Int,
    val driver: Driver,
    val value: Double,
)