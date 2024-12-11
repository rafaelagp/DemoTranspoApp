package net.rafgpereira.transpoapp.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

@OptIn(ExperimentalStdlibApi::class)
fun Color.toHexString() = this.toArgb().toHexString(HexFormat { number.prefix = "0x" })