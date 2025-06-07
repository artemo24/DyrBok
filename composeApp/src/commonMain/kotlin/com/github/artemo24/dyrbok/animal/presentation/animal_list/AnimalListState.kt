package com.github.artemo24.dyrbok.animal.presentation.animal_list

import com.github.artemo24.dyrbok.animal.domain.Animal
import com.github.artemo24.dyrbok.core.presentation.UiText


data class AnimalListState(
    val isLoading: Boolean = false,
    val animals: List<Animal> = emptyList(),
    val errorMessage: UiText? = null,
)
