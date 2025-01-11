package com.github.artemo24.dyrbok

import com.github.artemo24.dyrbok.websitescraper.RunWebsiteScraper


class JVMPlatform(summaryText: String): Platform {
    override val name: String = "Java ${System.getProperty("java.version")} [$summaryText]"
}

actual fun getPlatform(): Platform {
    // Run the website scraper before returning the platform information.
    val summaryText = RunWebsiteScraper().runIt()

    return JVMPlatform(summaryText)
}
