package com.github.artemo24.dyrbok.app

import kotlinx.serialization.Serializable


sealed interface Route {
    @Serializable
    data object AnimalSpeciesGraph: Route

    @Serializable
    data object AnimalSpeciesList: Route

    @Serializable
    data class AnimalList(val animalSpeciesOrdinal: Int): Route
}
