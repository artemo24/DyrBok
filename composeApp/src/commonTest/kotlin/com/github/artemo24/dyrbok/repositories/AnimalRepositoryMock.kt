package com.github.artemo24.dyrbok.repositories

import com.github.artemo24.dyrbok.model.domain.Animal
import com.github.artemo24.dyrbok.model.enumclasses.AdoptionStatus
import com.github.artemo24.dyrbok.model.enumclasses.AnimalSpecies
import com.github.artemo24.dyrbok.model.repositories.AnimalRepository


class AnimalRepositoryMock(userRepositoryMock: UserRepositoryMock) : AnimalRepository {
    val animalIdBelle = "Belle-id"
    val animalBelle = Animal(
        animalId = animalIdBelle,
        name = "Belle",
        uniqueName = "",
        animalSpecies = AnimalSpecies.DOG,
        adoptionStatus = AdoptionStatus.AVAILABLE,
        description = "",
        photosWanted = false,
        visible = true,
        webpageUrl = "belle-url",
        createdBy = userRepositoryMock.userJosh,
        createdDateTime = userRepositoryMock.mockDateTime,
        updatedBy = userRepositoryMock.userJosh,
        updatedDateTime = userRepositoryMock.mockDateTime,
    )

    private val animalIdIce = "Ice-id"
    val animalIce = Animal(
        animalId = animalIdIce,
        name = "Ice",
        uniqueName = "",
        animalSpecies = AnimalSpecies.DOG,
        adoptionStatus = AdoptionStatus.AVAILABLE,
        description = "",
        photosWanted = false,
        visible = true,
        webpageUrl = "ice-url",
        createdBy = userRepositoryMock.userMelissa,
        createdDateTime = userRepositoryMock.mockDateTime,
        updatedBy = userRepositoryMock.userMelissa,
        updatedDateTime = userRepositoryMock.mockDateTime,
    )

    private val animalIdJojo = "Jojo-id"
    val animalJojo = Animal(
        animalId = animalIdJojo,
        name = "Jojo",
        uniqueName = "",
        animalSpecies = AnimalSpecies.RABBIT,
        adoptionStatus = AdoptionStatus.AVAILABLE,
        description = "",
        photosWanted = false,
        visible = true,
        webpageUrl = "jojo-url",
        createdBy = userRepositoryMock.userSophie,
        createdDateTime = userRepositoryMock.mockDateTime,
        updatedBy = userRepositoryMock.userSophie,
        updatedDateTime = userRepositoryMock.mockDateTime,
    )

    private val mockAnimals = mutableListOf(animalBelle, animalIce, animalJojo)

    override fun getAnimalById(animalId: String): Animal? =
        mockAnimals.find { it.animalId == animalId }

    override fun getAnimalsBySpecies(animalSpecies: AnimalSpecies): List<Animal> =
        mockAnimals.filter { it.animalSpecies == animalSpecies }

    override fun getAllAnimals(): List<Animal> =
        mockAnimals

    override fun getVisibleAnimals(): List<Animal> =
        mockAnimals.filter { it.visible }

    override fun addAnimal(animal: Animal) {
        mockAnimals.add(animal)
    }
}
