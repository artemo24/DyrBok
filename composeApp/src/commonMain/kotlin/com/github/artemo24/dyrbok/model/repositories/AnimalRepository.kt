package com.github.artemo24.dyrbok.model.repositories

import com.github.artemo24.dyrbok.model.domain.Animal
import com.github.artemo24.dyrbok.model.enumclasses.AnimalSpecies


interface AnimalRepository {
    fun getAnimalById(animalId: String): Animal?
    fun getAnimalsBySpecies(animalSpecies: AnimalSpecies): List<Animal>
    fun getAllAnimals(): List<Animal>
    fun getVisibleAnimals(): List<Animal>
    fun addAnimal(animal: Animal)
}
