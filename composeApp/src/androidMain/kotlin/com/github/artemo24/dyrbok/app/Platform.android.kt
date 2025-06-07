package com.github.artemo24.dyrbok.app

import android.os.Build


class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun isDesktop(): Boolean =
    false

actual fun createFullBackup() {
    // This is a desktop only function.
}

actual fun analyzeAppUsage() {
    // This is a desktop only function.
}
