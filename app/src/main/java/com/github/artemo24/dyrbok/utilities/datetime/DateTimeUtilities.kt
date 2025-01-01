package com.github.artemo24.dyrbok.utilities.datetime

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class DateTimeUtilities {
    companion object {
        private val systemDateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US)

        fun getCurrentDateTime(): Date =
            Date()

        fun formatUserDateTime(dateTime: Date, formatPattern: String = "MMMM d, yyyy, H:mm:ss", locale: Locale? = null): String =
            SimpleDateFormat(formatPattern, locale ?: Locale.US).format(dateTime)

        fun formatSystemDateTime(dateTime: Date): String =
            systemDateTimeFormat.format(dateTime)
    }
}
