package com.github.artemo24.dyrbok.model.domain

import com.github.artemo24.dyrbok.model.enumclasses.AdoptionStatus
import com.github.artemo24.dyrbok.model.enumclasses.AnimalSpecies
import kotlinx.datetime.LocalDateTime


data class Animal(
    val animalId: String,
    val name: String,
    val uniqueName: String,
    val animalSpecies: AnimalSpecies,
    val adoptionStatus: AdoptionStatus,
    val description: String,
    val photosWanted: Boolean,
    val visible: Boolean,
    val webpageUrl: String,
    val auditInfo: AuditInfo,
)
