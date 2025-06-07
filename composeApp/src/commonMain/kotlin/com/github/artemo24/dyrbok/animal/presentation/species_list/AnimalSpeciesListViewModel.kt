@file:OptIn(FlowPreview::class)

package com.github.artemo24.dyrbok.animal.presentation.species_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.artemo24.dyrbok.animal.domain.AnimalRepository
import com.github.artemo24.dyrbok.animal.domain.AnimalSpecies
import com.github.artemo24.dyrbok.core.domain.onFailure
import com.github.artemo24.dyrbok.core.domain.onSuccess
import com.github.artemo24.dyrbok.core.presentation.toUiText
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class AnimalSpeciesListViewModel(private val animalRepository: AnimalRepository) : ViewModel() {
    private var searchJob: Job? = null

    private val _state = MutableStateFlow(AnimalSpeciesListState())
    val state = _state
        .onStart {
            observeSpeciesSelection()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onAction(action: AnimalSpeciesListAction) {
        when (action) {
            is AnimalSpeciesListAction.OnAnimalSpeciesClick -> {

            }
        }
    }

    private fun observeSpeciesSelection() {
        state
            .map { it.selectedAnimalSpecies }
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { selectedSpecies ->
                searchJob?.cancel()
                searchJob = searchAnimals(selectedSpecies)
            }
            .launchIn(viewModelScope)
    }

    private fun searchAnimals(animalSpecies: AnimalSpecies) =
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            animalRepository
                .getAnimalsBySpecies(animalSpecies)
                .onSuccess { searchResults ->
                    _state.update {
                        it.copy(isLoading = false, animals = searchResults, errorMessage = null)
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
