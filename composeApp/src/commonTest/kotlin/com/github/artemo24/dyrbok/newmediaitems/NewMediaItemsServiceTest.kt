package com.github.artemo24.dyrbok.newmediaitems

import com.github.artemo24.dyrbok.model.domain.Animal
import com.github.artemo24.dyrbok.model.enumclasses.AdoptionStatus
import com.github.artemo24.dyrbok.model.enumclasses.AnimalSpecies
import com.github.artemo24.dyrbok.repositories.AnimalRepositoryMock
import com.github.artemo24.dyrbok.repositories.MediaItemRepositoryMock
import com.github.artemo24.dyrbok.repositories.NewMediaItemGroupRepositoryMock
import com.github.artemo24.dyrbok.repositories.UserRepositoryMock
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


class NewMediaItemsServiceTest {
    private lateinit var newMediaItemService: NewMediaItemService

    private lateinit var userRepositoryMock: UserRepositoryMock
    private lateinit var animalRepositoryMock: AnimalRepositoryMock
    private lateinit var mediaItemRepositoryMock: MediaItemRepositoryMock
    private lateinit var newMediaItemGroupRepositoryMock: NewMediaItemGroupRepositoryMock

    private val animalIdAngel = "Angel-id"
    private lateinit var animalAngel: Animal

    private val animalIdBruce = "Bruce-id"
    private lateinit var animalBruce: Animal

    private val userIdSophie = "Sophie-id"

    @BeforeTest
    fun setUp() {
        userRepositoryMock = UserRepositoryMock()
        animalRepositoryMock = AnimalRepositoryMock(userRepositoryMock)
        mediaItemRepositoryMock = MediaItemRepositoryMock(userRepositoryMock, animalRepositoryMock)
        newMediaItemGroupRepositoryMock = NewMediaItemGroupRepositoryMock(animalRepositoryMock, mediaItemRepositoryMock)

        animalAngel = Animal(
            animalId = animalIdAngel,
            name = "Angel",
            uniqueName = "",
            animalSpecies = AnimalSpecies.CAT,
            adoptionStatus = AdoptionStatus.RESERVED,
            description = "",
            photosWanted = false,
            visible = true,
            webpageUrl = "angel-url",
            createdBy = userRepositoryMock.userMelissa,
            createdDateTime = userRepositoryMock.mockDateTime,
            updatedBy = userRepositoryMock.userMelissa,
            updatedDateTime = userRepositoryMock.mockDateTime,
        )

        animalBruce = Animal(
            animalId = animalIdBruce,
            name = "Bruce",
            uniqueName = "",
            animalSpecies = AnimalSpecies.CAT,
            adoptionStatus = AdoptionStatus.AVAILABLE,
            description = "",
            photosWanted = true,
            visible = true,
            webpageUrl = "bruce-url",
            createdBy = userRepositoryMock.userMelissa,
            createdDateTime = userRepositoryMock.mockDateTime,
            updatedBy = userRepositoryMock.userMelissa,
            updatedDateTime = userRepositoryMock.mockDateTime,
        )

        newMediaItemService = NewMediaItemService(newMediaItemGroupRepositoryMock, userRepositoryMock, animalRepositoryMock, mediaItemRepositoryMock)
    }

    @Test
    fun test_setUserId() {
        val userIdJosh = "Josh-id"
        newMediaItemService.setUserId(userIdJosh)
        animalRepositoryMock.addAnimal(animalAngel)
        newMediaItemService.addNewMediaItemIds(
            userIdJosh,
            AnimalSpecies.CAT,
            animalId = animalIdAngel,
            newMediaItemIds = listOf("media-item-g")
        )

        assertEquals(expected = userIdJosh, newMediaItemGroupRepositoryMock.getUsedUserId())
    }

    @Test
    fun test_getCountBySpecies() {
        newMediaItemService.setUserId(userIdSophie)

        val countBySpecies = newMediaItemService.getCountBySpecies(AnimalSpecies.DOG)

        assertEquals(expected = 4, countBySpecies)
    }

    @Test
    fun test_getCountByAnimal() {
        newMediaItemService.setUserId(userIdSophie)

        val animalIce = animalRepositoryMock.getAnimalById(animalId = "Ice-id")
        assertNotNull(animalIce)
        val countByAnimal = newMediaItemService.getCountByAnimal(animalIce)

        assertEquals(expected = 3, countByAnimal)
    }

    @Test
    fun test_handleViewMediaItemsAnimal() {
        newMediaItemService.setUserId(userIdSophie)
        assertEquals(expected = 4, newMediaItemService.getCountBySpecies(AnimalSpecies.DOG))

        val newMediaItemGroup = NewMediaItemGroup(userIdSophie, AnimalSpecies.DOG, animalId = "Ice-id", newMediaItemIds = listOf("media-item-b", "media-item-c", "media-item-d"))
        newMediaItemService.handleShowedMediaItemsAnimal(newMediaItemGroup)

        assertEquals(expected = 1, newMediaItemService.getCountBySpecies(AnimalSpecies.DOG))
    }

    @Test
    fun test_handleRemoveMediaItem_mediaItemsRemaining() {
        newMediaItemService.setUserId(userIdSophie)
        assertEquals(expected = 4, newMediaItemService.getCountBySpecies(AnimalSpecies.DOG))

        newMediaItemService.handleRemoveMediaItem(mediaItemId = "media-item-d")

        assertEquals(expected = 3, newMediaItemService.getCountBySpecies(AnimalSpecies.DOG))
    }

    @Test
    fun test_handleRemoveMediaItem_lastMediaItem() {
        newMediaItemService.setUserId(userIdSophie)
        val animalBelle = animalRepositoryMock.getAnimalById(animalId = "Belle-id")
        assertNotNull(animalBelle)
        assertEquals(expected = 1, newMediaItemService.getCountByAnimal(animalBelle))
        assertEquals(expected = 4, newMediaItemService.getCountBySpecies(AnimalSpecies.DOG))

        newMediaItemService.handleRemoveMediaItem(mediaItemId = "media-item-a")

        assertEquals(expected = 0, newMediaItemService.getCountByAnimal(animalBelle))
        assertEquals(expected = 3, newMediaItemService.getCountBySpecies(AnimalSpecies.DOG))
    }

    @Test
    fun test_handleRemoveMediaItem_lastMediaItems() {
        newMediaItemService.setUserId(userIdSophie)
        animalRepositoryMock.addAnimal(animalAngel)
        animalRepositoryMock.addAnimal(animalBruce)
        listOf(animalAngel, animalBruce).forEach { animal ->
            newMediaItemService.addNewMediaItemIds(
                userIdSophie,
                AnimalSpecies.CAT,
                animalId = animal.animalId,
                newMediaItemIds = listOf("media-item-g")
            )
        }
        assertEquals(expected = 1, newMediaItemService.getCountByAnimal(animalAngel))
        assertEquals(expected = 1, newMediaItemService.getCountByAnimal(animalBruce))
        assertEquals(expected = 2, newMediaItemService.getCountBySpecies(AnimalSpecies.CAT))

        newMediaItemService.handleRemoveMediaItem(mediaItemId = "media-item-g")

        assertEquals(expected = 0, newMediaItemService.getCountByAnimal(animalAngel))
        assertEquals(expected = 0, newMediaItemService.getCountByAnimal(animalBruce))
        assertEquals(expected = 0, newMediaItemService.getCountBySpecies(AnimalSpecies.CAT))
    }

    @Test
    fun test_handleAddAnimal() {
        newMediaItemService.setUserId(userIdSophie)
        animalRepositoryMock.addAnimal(animalAngel)
        assertEquals(expected = 0, newMediaItemService.getCountByAnimal(animalAngel))
        assertEquals(expected = 0, newMediaItemService.getCountBySpecies(AnimalSpecies.CAT))

        newMediaItemService.handleAddAnimal(animalIdAngel, AnimalSpecies.CAT, listOf("media-item-g", "media-item-h"))

        assertEquals(expected = 2, newMediaItemService.getCountByAnimal(animalAngel))
        assertEquals(expected = 2, newMediaItemService.getCountBySpecies(AnimalSpecies.CAT))
    }

    @Test
    fun test_handleHideAnimal() {
        newMediaItemService.setUserId(userIdSophie)
        animalRepositoryMock.addAnimal(animalAngel)
        newMediaItemService.handleAddAnimal(animalIdAngel, AnimalSpecies.CAT, listOf("media-item-g", "media-item-h"))
        assertEquals(expected = 2, newMediaItemService.getCountByAnimal(animalAngel))
        assertEquals(expected = 2, newMediaItemService.getCountBySpecies(AnimalSpecies.CAT))

        newMediaItemService.handleHideAnimal(animalIdAngel)

        assertEquals(expected = 0, newMediaItemService.getCountByAnimal(animalAngel))
        assertEquals(expected = 0, newMediaItemService.getCountBySpecies(AnimalSpecies.CAT))
    }

    @Test
    fun test_handleShowAnimal() {
        newMediaItemService.setUserId(userIdSophie)
        animalRepositoryMock.addAnimal(animalAngel)
        newMediaItemService.handleAddAnimal(animalIdAngel, AnimalSpecies.CAT, listOf("media-item-g", "media-item-h"))
        newMediaItemService.handleHideAnimal(animalIdAngel)
        assertEquals(expected = 0, newMediaItemService.getCountByAnimal(animalAngel))
        assertEquals(expected = 0, newMediaItemService.getCountBySpecies(AnimalSpecies.CAT))

        // Showing an animal after hiding it: the media items are not treated as new.
        newMediaItemService.handleShowAnimal(animalIdAngel, AnimalSpecies.CAT, listOf("media-item-g", "media-item-h"))

        assertEquals(expected = 0, newMediaItemService.getCountByAnimal(animalAngel))
        assertEquals(expected = 0, newMediaItemService.getCountBySpecies(AnimalSpecies.CAT))
    }

    @Test
    fun test_handleAddUser() {
        val userIdSharleen = "Sharleen-id"
        newMediaItemService.setUserId(userIdSharleen)

        newMediaItemService.handleAddUser(userIdSharleen)

        assertEquals(expected = 1, newMediaItemService.getCountByAnimal(animalRepositoryMock.animalBelle))
        assertEquals(expected = 4, newMediaItemService.getCountBySpecies(AnimalSpecies.DOG))
    }
}
