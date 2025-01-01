package com.github.artemo24.dyrbok.utilities.logging

// Android: import android.util.Log
import com.github.artemo24.dyrbok.utilities.datetime.DateTimeUtilities


object Log {
    fun d(tag: String?, message: String) {
        // Android: Log.d(tag, message)

        logMessage(level = 'D', tag, message)
    }

    fun e(tag: String?, message: String, throwable: Throwable?) {
        // Android: Log.e(tag, message, throwable)

        logMessage(level = 'E', tag, message, throwable)
    }

    private fun logMessage(level: Char, tag: String?, message: String, throwable: Throwable? = null) {
        val dateTime = DateTimeUtilities.formatSystemDateTime(DateTimeUtilities.getCurrentDateTime())
        println("$dateTime ${(tag ?: "").padEnd(length = 28)}  $level  $message")

        throwable?.printStackTrace()
    }
}
