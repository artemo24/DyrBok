package com.github.artemo24.dyrbok


interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
expect fun isDesktop(): Boolean
expect fun createFullBackup()
expect fun analyzeAppUsage()
