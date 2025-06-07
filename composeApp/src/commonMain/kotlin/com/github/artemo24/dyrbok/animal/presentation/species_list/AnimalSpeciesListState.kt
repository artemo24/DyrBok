package com.github.artemo24.dyrbok.animal.presentation.species_list

import com.github.artemo24.dyrbok.animal.domain.Animal
import com.github.artemo24.dyrbok.animal.domain.AnimalSpecies
import com.github.artemo24.dyrbok.core.presentation.UiText


data class AnimalSpeciesListState(
    val isLoading: Boolean = false,
    val animalSpeciesList: List<AnimalSpecies> = AnimalSpecies.entries.filter { it != AnimalSpecies.UNKNOWN },
    val selectedAnimalSpecies: AnimalSpecies = AnimalSpecies.BIRD,
    val animals: List<Animal> = emptyList(),
    val errorMessage: UiText? = null,
)
