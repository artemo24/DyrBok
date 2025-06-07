package com.github.artemo24.dyrbok.animal.presentation.animal_list

import com.github.artemo24.dyrbok.animal.domain.Animal
import com.github.artemo24.dyrbok.animal.domain.AnimalSpecies


sealed interface AnimalListAction {
    data class OnAnimalSpeciesSelect(val animalSpecies: AnimalSpecies): AnimalListAction
    data class OnAnimalClick(val animal: Animal): AnimalListAction
}
