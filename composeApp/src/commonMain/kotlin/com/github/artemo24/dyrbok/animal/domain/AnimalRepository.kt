package com.github.artemo24.dyrbok.animal.domain

import com.github.artemo24.dyrbok.core.domain.DataError
import com.github.artemo24.dyrbok.core.domain.Result


interface AnimalRepository {
    suspend fun getAnimalsBySpecies(animalSpecies: AnimalSpecies): Result<List<Animal>, DataError.Remote>
}
