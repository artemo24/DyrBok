package com.github.artemo24.dyrbok.animal.presentation.species_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.artemo24.dyrbok.animal.domain.AnimalSpecies
import com.github.artemo24.dyrbok.animal.presentation.species_list.components.AnimalSpeciesList
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun AnimalSpeciesListScreenRoot(
    viewModel: AnimalSpeciesListViewModel = koinViewModel(),
    onAnimalSpeciesClick: (AnimalSpecies) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    AnimalSpeciesListScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is AnimalSpeciesListAction.OnAnimalSpeciesClick -> onAnimalSpeciesClick(action.animalSpecies)
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun AnimalSpeciesListScreen(
    state: AnimalSpeciesListState,
    onAction: (AnimalSpeciesListAction) -> Unit,
) {
    Column {
        Spacer(modifier = Modifier.height(28.dp))

        AnimalSpeciesList(
            animalSpeciesList = state.animalSpeciesList,
            onAnimalSpeciesClick = { animalSpecies ->
                onAction(AnimalSpeciesListAction.OnAnimalSpeciesClick(animalSpecies))
            }
        )
    }
}
