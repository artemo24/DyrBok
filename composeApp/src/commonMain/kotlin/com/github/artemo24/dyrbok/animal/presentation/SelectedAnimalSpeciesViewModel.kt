package com.github.artemo24.dyrbok.animal.presentation

import androidx.lifecycle.ViewModel
import com.github.artemo24.dyrbok.animal.domain.AnimalSpecies
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class SelectedAnimalSpeciesViewModel: ViewModel() {
    private val _selectedAnimalSpecies = MutableStateFlow<AnimalSpecies?>(null)
    val selectedAnimalSpecies = _selectedAnimalSpecies.asStateFlow()

    fun onSelectAnimalSpecies(animalSpecies: AnimalSpecies?) {
        _selectedAnimalSpecies.value = animalSpecies
    }
}
