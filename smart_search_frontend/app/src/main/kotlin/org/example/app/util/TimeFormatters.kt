package org.example.app.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object TimeFormatters {
    private val hhmm = SimpleDateFormat("HH:mm", Locale.getDefault())

    fun formatTimeRange(startMs: Long, endMs: Long): String {
        return "${hhmm.format(Date(startMs))} - ${hhmm.format(Date(endMs))}"
    }
}
