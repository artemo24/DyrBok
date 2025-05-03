package com.github.artemo24.dyrbok

import com.github.artemo24.dyrbok.analyzeusage.AnalyzeUsage
import com.github.artemo24.dyrbok.backupandrestore.main.DyrBokBackupAndRestore


class JVMPlatform(summaryText: String): Platform {
    override val name: String = "Java ${System.getProperty("java.version")} [$summaryText]"
}

actual fun getPlatform(): Platform {
    val summaryText = ""

    // Disabled: DyrBokBackupAndRestore().initializeAndCreateFullBackup(downloadPhotos = false)

    // Run the website scraper: val summaryText = RunWebsiteScraper().runIt()

    // Disabled for now: UpdateAnimals().printUpdatedAnimals()

    // Return the platform information.
    return JVMPlatform(summaryText)
}

actual fun isDesktop(): Boolean =
    true

actual fun createFullBackup() {
    DyrBokBackupAndRestore().initializeAndCreateFullBackup(downloadPhotos = true)
}

actual fun analyzeAppUsage() {
    AnalyzeUsage().analyzeUsage()
}
