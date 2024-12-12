package net.rafgpereira.transpoapp.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun String.replaceLast(oldValue: String, newValue: String): String {
    val lastIndex = lastIndexOf(oldValue)
    if (lastIndex == -1) {
        return this
    }
    val prefix = substring(0, lastIndex)
    val suffix = substring(lastIndex + oldValue.length)
    return "$prefix$newValue$suffix"
}

fun String.formatStringDate() =
    LocalDateTime.parse(this)
        .format(DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm"))