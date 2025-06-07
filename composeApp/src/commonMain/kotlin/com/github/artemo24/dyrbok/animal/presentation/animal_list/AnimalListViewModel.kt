package com.github.artemo24.dyrbok.animal.presentation.animal_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.artemo24.dyrbok.animal.domain.AnimalRepository
import com.github.artemo24.dyrbok.core.domain.onFailure
import com.github.artemo24.dyrbok.core.domain.onSuccess
import com.github.artemo24.dyrbok.core.presentation.toUiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class AnimalListViewModel(private val animalRepository: AnimalRepository) : ViewModel() {
    private val _state = MutableStateFlow(AnimalListState())
    val state = _state
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onAction(action: AnimalListAction) {
        when (action) {
            is AnimalListAction.OnAnimalSpeciesSelect -> {
                _state.update { it.copy(isLoading = true) }
                viewModelScope.launch {
                    animalRepository
                        .getAnimalsBySpecies(action.animalSpecies)
                        .onSuccess { searchResults ->
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    animals = searchResults
                                )
                            }
                        }
                        .onFailure { error ->
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    animals = emptyList(),
                                    errorMessage = error.toUiText()
                                )
                            }
                        }
                }
            }

            is AnimalListAction.OnAnimalClick -> {

            }
        }
    }
}
