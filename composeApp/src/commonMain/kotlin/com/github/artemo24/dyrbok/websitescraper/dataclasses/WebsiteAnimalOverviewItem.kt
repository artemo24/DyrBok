package com.github.artemo24.dyrbok.websitescraper.dataclasses

import com.github.artemo24.dyrbok.model.enumclasses.AdoptionStatus


data class WebsiteAnimalOverviewItem(val animalName: String, val webpageUrl: String, val adoptionStatus: AdoptionStatus)
