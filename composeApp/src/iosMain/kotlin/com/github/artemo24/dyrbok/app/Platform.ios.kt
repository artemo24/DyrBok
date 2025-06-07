package com.github.artemo24.dyrbok.app

import platform.UIKit.UIDevice


class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

actual fun isDesktop(): Boolean =
    false

actual fun createFullBackup() {
    // This is a desktop only function.
}

actual fun analyzeAppUsage() {
    // This is a desktop only function.
}
