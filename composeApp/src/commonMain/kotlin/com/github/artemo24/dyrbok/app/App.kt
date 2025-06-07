package com.github.artemo24.dyrbok.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.github.artemo24.dyrbok.animal.presentation.SelectedAnimalSpeciesViewModel
import com.github.artemo24.dyrbok.animal.presentation.animal_list.AnimalListAction
import com.github.artemo24.dyrbok.animal.presentation.animal_list.AnimalListScreenRoot
import com.github.artemo24.dyrbok.animal.presentation.animal_list.AnimalListViewModel
import com.github.artemo24.dyrbok.animal.presentation.species_list.AnimalSpeciesListScreenRoot
import com.github.artemo24.dyrbok.animal.presentation.species_list.AnimalSpeciesListViewModel
import dyrbok.composeapp.generated.resources.Res
import dyrbok.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel


@Composable
@Preview
fun App() {
    MaterialTheme {
        if (isDesktop()) {
            var showContent by remember { mutableStateOf(false) }

            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

                Button(onClick = { createFullBackup() }) {
                    Text("Create full backup")
                }

                Button(onClick = { analyzeAppUsage() }) {
                    Text("Analyze app usage")
                }

                if (showContent) {
                    Button(onClick = { showContent = !showContent }) {
                        Text("Show platform")
                    }

                    AnimatedVisibility(showContent) {
                        val greeting = remember { Main().greet() }

                        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                            Image(painterResource(Res.drawable.compose_multiplatform), null)
                            Text("Compose: $greeting")
                        }
                    }
                }

            }
        } else {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = Route.AnimalSpeciesGraph
            ) {
                navigation<Route.AnimalSpeciesGraph>(
                    startDestination = Route.AnimalSpeciesList
                ) {
                    composable<Route.AnimalSpeciesList>(
                        exitTransition = { slideOutHorizontally() },
                        popEnterTransition = { slideInHorizontally() }
                    ) {
                        val viewModel = koinViewModel<AnimalSpeciesListViewModel>()
                        val selectedAnimalSpeciesViewModel =
                            it.sharedKoinViewModel<SelectedAnimalSpeciesViewModel>(navController)

                        LaunchedEffect(true) {
                            selectedAnimalSpeciesViewModel.onSelectAnimalSpecies(null)
                        }

                        AnimalSpeciesListScreenRoot(
                            viewModel = viewModel,
                            onAnimalSpeciesClick = { animalSpecies ->
                                selectedAnimalSpeciesViewModel.onSelectAnimalSpecies(animalSpecies)
                                navController.navigate(
                                    Route.AnimalList(animalSpecies.ordinal)
                                )
                            }
                        )
                    }
                    composable<Route.AnimalList>(
                        enterTransition = {
                            slideInHorizontally { initialOffset ->
                                initialOffset
                            }
                        },
                        exitTransition = {
                            slideOutHorizontally { initialOffset ->
                                initialOffset
                            }
                        }
                    ) {
                        val selectedAnimalSpeciesViewModel =
                            it.sharedKoinViewModel<SelectedAnimalSpeciesViewModel>(navController)
                        val viewModel = koinViewModel<AnimalListViewModel>()
                        val selectedAnimalSpecies by selectedAnimalSpeciesViewModel.selectedAnimalSpecies.collectAsStateWithLifecycle()

                        LaunchedEffect(selectedAnimalSpecies) {
                            selectedAnimalSpecies?.let { animalSpecies ->
                                viewModel.onAction(AnimalListAction.OnAnimalSpeciesSelect(animalSpecies))
                            }
                        }

                        AnimalListScreenRoot(
                            viewModel = viewModel,
                            onAnimalClick = {

                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private inline fun <reified T : ViewModel> NavBackStackEntry.sharedKoinViewModel(
    navController: NavController
): T {
    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return koinViewModel(
        viewModelStoreOwner = parentEntry
    )
}
