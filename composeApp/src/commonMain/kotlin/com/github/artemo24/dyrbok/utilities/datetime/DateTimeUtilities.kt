package com.github.artemo24.dyrbok.utilities.datetime

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime


class DateTimeUtilities {
    companion object {
        private lateinit var monthNames: List<String>

        fun setMonthNames(monthNames: List<String>) {
            this.monthNames = monthNames
        }

        private val systemDateTimeFormat = LocalDateTime.Format {
            year()
            char('-')
            monthNumber()
            char('-')
            dayOfMonth()
            char(' ')
            hour()
            char(':')
            minute()
            char(':')
            second()
            char('.')
            secondFraction(fixedLength = 3)
        }

        fun getCurrentDateTime(): LocalDateTime =
            Clock.System.now().toLocalDateTime(TimeZone.of("Europe/Paris"))

        fun formatUserDateTime(dateTime: LocalDateTime, formatPattern: String = "MMMM d, yyyy, H:mm:ss", insertMonthName: Boolean = false): String {
            @OptIn(FormatStringsInDatetimeFormats::class)
            val dateTimeFormat = LocalDateTime.Format {
                byUnicodePattern(formatPattern)
            }

            val dateTimeString = dateTimeFormat.format(dateTime)

            return if (insertMonthName) {
                val monthMarkerIndex = dateTimeString.indexOf("****")
                val monthIndex = dateTimeString.substring(monthMarkerIndex + 4, monthMarkerIndex + 6).toInt()

                "${dateTimeString.take(monthMarkerIndex)}${monthNames[monthIndex - 1]}${dateTimeString.drop(monthMarkerIndex + 6)}"
            } else {
                dateTimeString
            }
        }

        fun parseUserDateTime(dateTimeText: String, formatPattern: String = "MMMM d, yyyy, H:mm:ss"): LocalDateTime {
            @OptIn(FormatStringsInDatetimeFormats::class)
            val dateTimeFormat = LocalDateTime.Format {
                byUnicodePattern(formatPattern)
            }

            return dateTimeFormat.parse(dateTimeText)
        }

        fun formatSystemDateTime(dateTime: LocalDateTime): String =
            systemDateTimeFormat.format(dateTime)
    }
}
