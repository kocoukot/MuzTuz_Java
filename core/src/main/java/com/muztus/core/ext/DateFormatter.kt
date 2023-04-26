package com.muztus.core.ext

import java.text.SimpleDateFormat
import java.util.Locale

class DateFormatter {

    private val timerFormat = SimpleDateFormat("mm:ss.SS", Locale.US)

    fun milsecondsToDuration(milSeconds: Long): String {
        return timerFormat.format(milSeconds)
    }
}