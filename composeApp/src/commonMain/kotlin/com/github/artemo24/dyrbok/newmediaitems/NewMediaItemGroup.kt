package com.github.artemo24.dyrbok.newmediaitems

import com.github.artemo24.dyrbok.model.enumclasses.AnimalSpecies


data class NewMediaItemGroup(
    val userId: String, val animalSpecies: AnimalSpecies, val animalId: String, val newMediaItemIds: List<String>
)
