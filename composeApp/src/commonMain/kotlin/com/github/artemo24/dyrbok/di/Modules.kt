package com.github.artemo24.dyrbok.di

import com.github.artemo24.dyrbok.animal.data.repository.DefaultAnimalRepository
import com.github.artemo24.dyrbok.animal.domain.AnimalRepository
import com.github.artemo24.dyrbok.animal.presentation.SelectedAnimalSpeciesViewModel
import com.github.artemo24.dyrbok.animal.presentation.animal_list.AnimalListViewModel
import com.github.artemo24.dyrbok.animal.presentation.species_list.AnimalSpeciesListViewModel
// import com.github.artemo24.dyrbok.core.data.HttpClientFactory
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module


// expect val platformModule: Module

val sharedModule = module {
    // single { HttpClientFactory.create(get()) }
    // singleOf(::KtorRemoteBookDataSource).bind<RemoteBookDataSource>()
    singleOf(::DefaultAnimalRepository).bind<AnimalRepository>()

    viewModelOf(::AnimalSpeciesListViewModel)
    viewModelOf(::AnimalListViewModel)
    viewModelOf(::SelectedAnimalSpeciesViewModel)
}
