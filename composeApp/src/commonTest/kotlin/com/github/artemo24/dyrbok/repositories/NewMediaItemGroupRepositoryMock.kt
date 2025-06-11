package com.github.artemo24.dyrbok.repositories

import com.github.artemo24.dyrbok.model.repositories.NewMediaItemGroupRepository
import com.github.artemo24.dyrbok.newmediaitems.NewMediaItemGroup


class NewMediaItemGroupRepositoryMock(animalRepositoryMock: AnimalRepositoryMock, mediaItemRepositoryMock: MediaItemRepositoryMock) :
    NewMediaItemGroupRepository {

    private val allMockNewMediaItemGroups = mutableListOf(
        NewMediaItemGroup(
            userId = "Melissa-id",
            animalRepositoryMock.animalBelle.animalSpecies,
            animalRepositoryMock.animalBelle.animalId,
            mediaItemRepositoryMock.getMediaItemIdsByAnimal(animalRepositoryMock.animalBelle.animalId)
        ),
        NewMediaItemGroup(
            userId = "Melissa-id",
            animalRepositoryMock.animalJojo.animalSpecies,
            animalRepositoryMock.animalJojo.animalId,
            mediaItemRepositoryMock.getMediaItemIdsByAnimal(animalRepositoryMock.animalJojo.animalId)
        ),
        NewMediaItemGroup(
            userId = "Sophie-id",
            animalRepositoryMock.animalBelle.animalSpecies,
            animalRepositoryMock.animalIdBelle,
            mediaItemRepositoryMock.getMediaItemIdsByAnimal(animalRepositoryMock.animalBelle.animalId)
        ),
        NewMediaItemGroup(
            userId = "Sophie-id",
            animalRepositoryMock.animalIce.animalSpecies,
            animalRepositoryMock.animalIce.animalId,
            mediaItemRepositoryMock.getMediaItemIdsByAnimal(animalRepositoryMock.animalIce.animalId)
        ),
    )

    // Replace by standard mock functionality later.
    private var usedUserId = ""

    fun getUsedUserId(): String =
        usedUserId

    override fun createNewMediaItemGroup(newMediaItemGroup: NewMediaItemGroup) {
        allMockNewMediaItemGroups.add(newMediaItemGroup)
    }

    override fun readAllNewMediaItemGroups(): List<NewMediaItemGroup> {
        return allMockNewMediaItemGroups
    }

    override fun readNewMediaItemGroups(userId: String): List<NewMediaItemGroup> {
        usedUserId = userId

        return allMockNewMediaItemGroups.filter { it.userId == userId }
    }

    override fun updateNewMediaItemGroup(newMediaItemGroup: NewMediaItemGroup) {
        val currentRecord = allMockNewMediaItemGroups.find { it.animalSpecies == newMediaItemGroup.animalSpecies && it.animalId == newMediaItemGroup.animalId }

        if (currentRecord != null) {
            allMockNewMediaItemGroups.remove(currentRecord)
            allMockNewMediaItemGroups.add(newMediaItemGroup)
        } else {
            // todo: Simultaneous edits are possible. Handle them by refreshing the data?
            throw IllegalArgumentException("No record found that matches $newMediaItemGroup.")
        }
    }

    override fun deleteNewMediaItemGroup(newMediaItemGroup: NewMediaItemGroup) {
        allMockNewMediaItemGroups.remove(newMediaItemGroup)
    }
}
