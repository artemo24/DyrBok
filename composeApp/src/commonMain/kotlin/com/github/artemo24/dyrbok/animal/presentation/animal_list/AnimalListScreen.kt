package com.github.artemo24.dyrbok.animal.presentation.animal_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.github.artemo24.dyrbok.animal.domain.Animal
import com.github.artemo24.dyrbok.animal.presentation.animal_list.components.AnimalList
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun AnimalListScreenRoot(
    viewModel: AnimalListViewModel = koinViewModel(),
    onAnimalClick: (Animal) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    AnimalListScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is AnimalListAction.OnAnimalSpeciesSelect -> {}
                is AnimalListAction.OnAnimalClick -> onAnimalClick(action.animal)
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun AnimalListScreen(
    state: AnimalListState,
    onAction: (AnimalListAction) -> Unit,
) {
    Column {
        Spacer(modifier = Modifier.height(28.dp))

        AnimalList(
            animals = state.animals,
            onAnimalClick = { animal ->
                onAction(AnimalListAction.OnAnimalClick(animal))
            }
        )
    }
}
