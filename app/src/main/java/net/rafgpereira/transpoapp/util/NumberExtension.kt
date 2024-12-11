package net.rafgpereira.transpoapp.util

fun Int.isValidDistanceFor(driverId: Int) =
    when (driverId) {
        1 -> this >= 1
        2 -> this >= 5
        3 -> this >= 10
        else -> false
    }

fun Int.driverIdToValidDistance() =
    when (this) {
        1 -> 1
        2 -> 5
        3 -> 10
        else -> 0
    }