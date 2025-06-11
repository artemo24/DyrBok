package com.github.artemo24.dyrbok.newmediaitems

import com.github.artemo24.dyrbok.model.domain.Animal
import com.github.artemo24.dyrbok.model.enumclasses.AnimalSpecies
import com.github.artemo24.dyrbok.model.repositories.AnimalRepository
import com.github.artemo24.dyrbok.model.repositories.MediaItemRepository
import com.github.artemo24.dyrbok.model.repositories.UserRepository
import kotlinx.datetime.LocalDateTime


/**
 * This service keeps track of the new media item markers for all users, animals and media items.
 */
class NewMediaItemService(
    private val newMediaItemGroupRepository: NewMediaItemGroupRepository,
    private val userRepository: UserRepository,
    private val animalRepository: AnimalRepository,
    private val mediaItemRepository: MediaItemRepository,
) {
    private lateinit var userId: String

    /**
     * Set the user ID to be used by other methods of this service.
     */
    fun setUserId(userId: String) {
        this.userId = userId
    }

    /**
     * Add initial media item markers for all active users. This method is meant to be called once when the media item
     * markers functionality is activated.
     */
    fun addInitialMediaItems(thresholdDateTime: LocalDateTime) {
        userRepository.readAllActiveUsers().forEach { user ->
            animalRepository.getVisibleAnimals().forEach { animal ->
                val mediaItems = mediaItemRepository.getMediaItemsByAnimal(animal.animalId)
                val mediaItemIds = mediaItems.filter { it.captureDateTime >= thresholdDateTime }.map { it.mediaItemId }
                addNewMediaItemIds(user.userId, animal.animalSpecies, animal.animalId, mediaItemIds)
            }
        }
    }

    /**
     * Add new media item markers for a specific animal. Either create new or update existing markers.
     */
    fun addNewMediaItemIds(userId: String, animalSpecies: AnimalSpecies, animalId: String, newMediaItemIds: List<String>) {
        val currentItems = newMediaItemGroupRepository.readNewMediaItemGroups(userId)
        val currentRecord = currentItems.find { it.animalSpecies == animalSpecies && it.animalId == animalId }

        if (currentRecord == null) {
            newMediaItemGroupRepository.createNewMediaItemGroup(NewMediaItemGroup(userId, animalSpecies, animalId, newMediaItemIds))
        } else {
            val currentMediaItemIds = currentRecord.newMediaItemIds.toMutableList()
            currentMediaItemIds.addAll(newMediaItemIds)
            newMediaItemGroupRepository.updateNewMediaItemGroup(currentRecord.copy(newMediaItemIds = currentMediaItemIds))
        }
    }

    /**
     * Count the number of new media item markers by animal species.
     */
    fun getCountBySpecies(animalSpecies: AnimalSpecies): Int =
        newMediaItemGroupRepository
            .readNewMediaItemGroups(userId)
            .filter { it.animalSpecies == animalSpecies }
            .sumOf { it.newMediaItemIds.size }

    /**
     * Count the number of new media item markers by animal.
     */
    fun getCountByAnimal(animal: Animal): Int =
        newMediaItemGroupRepository
            .readNewMediaItemGroups(userId)
            .filter { it.animalId == animal.animalId }
            .sumOf { it.newMediaItemIds.size }

    /**
     * Handle media items of a specific animal that have been shown to a specific user.
     */
    fun handleShowedMediaItemsAnimal(newMediaItemGroup: NewMediaItemGroup) {
        newMediaItemGroupRepository.deleteNewMediaItemGroup(newMediaItemGroup)
    }

    /**
     * Handle removal of a media item: remove it for all animals. A media item normally contains only one animal,
     * but it theoretically can contain multiple animals.
     *
     * Note: currently, a media item refers to a single animal.
     */
    fun handleRemoveMediaItem(mediaItemId: String) {
        val newMediaItemGroupsToBeDeleted = mutableListOf<NewMediaItemGroup>()

        newMediaItemGroupRepository.readAllNewMediaItemGroups()
            .forEach { newMediaItemGroup ->
                if (newMediaItemGroup.newMediaItemIds.contains(mediaItemId)) {
                    if (newMediaItemGroup.newMediaItemIds.size == 1) {
                        newMediaItemGroupsToBeDeleted.add(newMediaItemGroup)
                    } else {
                        val updatedMediaItemIds = newMediaItemGroup.newMediaItemIds.toMutableList()
                        updatedMediaItemIds.remove(mediaItemId)
                        newMediaItemGroupRepository.updateNewMediaItemGroup(newMediaItemGroup.copy(newMediaItemIds = updatedMediaItemIds))
                    }
                }
            }

        newMediaItemGroupsToBeDeleted.forEach { newMediaItemGroup -> newMediaItemGroupRepository.deleteNewMediaItemGroup(newMediaItemGroup) }
    }

    /**
     * Handle adding a new animal: add new media item markers for all media items from the website.
     */
    fun handleAddAnimal(animalId: String, animalSpecies: AnimalSpecies, mediaItemIds: List<String>) {
        userRepository.readAllActiveUsers().forEach { user ->
            addNewMediaItemIds(user.userId, animalSpecies, animalId, mediaItemIds)
        }
    }

    /**
     * Handle hiding an animal: remove all new media items markers for all users.
     */
    fun handleHideAnimal(animalId: String) {
        userRepository.readAllUsers().forEach { user ->
            newMediaItemGroupRepository.readNewMediaItemGroups(user.userId)
                .filter { it.animalId == animalId }
                .forEach { newMediaItemGroupRepository.deleteNewMediaItemGroup(it) }
        }
    }

    /**
     * Handle making an animal visible again (which is a rare situation). Treat all media items as old: do not handle
     * this the same way as adding a new animal.
     */
    @Suppress("UNUSED_PARAMETER")
    fun handleShowAnimal(animalId: String, animalSpecies: AnimalSpecies, mediaItemIds: List<String>) {
        // handleAddAnimal(animalId, animalSpecies, mediaItemIds)
    }

    /**
     * Handle adding a new user. All media items of all visible animals will be marked as new for this user.
     */
    fun handleAddUser(userId: String) {
        animalRepository.getVisibleAnimals()
            .forEach { animal ->
                val newMediaItemIds = mediaItemRepository.getMediaItemIdsByAnimal(animal.animalId)
                addNewMediaItemIds(userId, animal.animalSpecies, animal.animalId, newMediaItemIds)
            }
    }
}
