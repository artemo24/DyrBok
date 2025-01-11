package com.github.artemo24.dyrbok.websitescraper.dataclasses

import io.ktor.http.Url


data class WebsiteAnimalInformation(val description: String, val mediaItemUrls: List<Url>)
