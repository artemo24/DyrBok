package com.github.artemo24.dyrbok.animal.presentation.species_list

import com.github.artemo24.dyrbok.animal.domain.AnimalSpecies


sealed interface AnimalSpeciesListAction {
    data class OnAnimalSpeciesClick(val animalSpecies: AnimalSpecies): AnimalSpeciesListAction
}
