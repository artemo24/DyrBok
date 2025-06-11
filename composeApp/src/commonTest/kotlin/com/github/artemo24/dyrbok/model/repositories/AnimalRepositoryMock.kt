package com.github.artemo24.dyrbok.model.repositories

import com.github.artemo24.dyrbok.model.domain.Animal
import com.github.artemo24.dyrbok.model.domain.AuditInfo
import com.github.artemo24.dyrbok.model.enumclasses.AdoptionStatus
import com.github.artemo24.dyrbok.model.enumclasses.AnimalSpecies


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
        auditInfo = AuditInfo(
            createdBy = userRepositoryMock.userJosh,
            createdAt = userRepositoryMock.mockDateTime,
        ),
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
        auditInfo = AuditInfo(
            createdBy = userRepositoryMock.userMelissa,
            createdAt = userRepositoryMock.mockDateTime,
        ),
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
        auditInfo = AuditInfo(
            createdBy = userRepositoryMock.userSophie,
            createdAt = userRepositoryMock.mockDateTime,
        ),
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
