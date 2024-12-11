package net.rafgpereira.transpoapp.domain.model

const val MAX_RATING = 5

data class DriverReview(
    val rating: Long,
    val comment: String,
)
