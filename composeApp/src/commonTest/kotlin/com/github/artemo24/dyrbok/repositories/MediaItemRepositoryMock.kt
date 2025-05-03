package com.github.artemo24.dyrbok.repositories

import com.github.artemo24.dyrbok.model.domain.MediaFile
import com.github.artemo24.dyrbok.model.domain.MediaItem
import com.github.artemo24.dyrbok.model.repositories.MediaItemRepository
import com.github.artemo24.dyrbok.utilities.md5.MD5
import io.ktor.http.Url
import io.ktor.utils.io.core.toByteArray


class MediaItemRepositoryMock(userRepositoryMock: UserRepositoryMock, animalRepositoryMock: AnimalRepositoryMock): MediaItemRepository {
    private val mediaItemsBelle = listOf(createMediaItem(userRepositoryMock, "media-item-a", "1234"))
    private val mediaItemsIce = listOf(
        createMediaItem(userRepositoryMock, "media-item-b", "2345"),
        createMediaItem(userRepositoryMock, "media-item-c", "3456"),
        createMediaItem(userRepositoryMock, "media-item-d", "4567"),
    )
    private val mediaItemsJojo = listOf(
        createMediaItem(userRepositoryMock, "media-item-e", "5678"),
        createMediaItem(userRepositoryMock, "media-item-f", "6789"),
    )

    private val mockMediaItems = mapOf(
        animalRepositoryMock.animalBelle.animalId to mediaItemsBelle,
        animalRepositoryMock.animalIce.animalId to mediaItemsIce,
        animalRepositoryMock.animalJojo.animalId to mediaItemsJojo,
    )

    override fun getMediaItemIdsByAnimal(animalId: String): List<String> =
        getMediaItemsByAnimal(animalId).map { it.mediaItemId }

    override fun getMediaItemsByAnimal(animalId: String): List<MediaItem> =
        mockMediaItems[animalId] ?: emptyList()

    private fun createMediaItem(userRepositoryMock: UserRepositoryMock, mediaItemId: String, mediaFileId: String) = MediaItem(
        mediaItemId = mediaItemId,
        creator = userRepositoryMock.userJosh,
        captureDateTime = userRepositoryMock.mockDateTime,
        fileSize = 123456L,
        fileMd5 = MD5.computeMD5Hash("$mediaFileId-$mediaFileId".toByteArray()),
        mediaFile = MediaFile(
            mediaFileId = mediaFileId,
            storageSystem = "Firebase Storage",
            fileUrl = Url("url-$mediaFileId"),
            createdBy = userRepositoryMock.userMarianne,
            createdDateTime = userRepositoryMock.mockDateTime,
            updatedBy = userRepositoryMock.userMarianne,
            updatedDateTime = userRepositoryMock.mockDateTime
        ),
        createdBy = userRepositoryMock.userMarianne,
        createdDateTime = userRepositoryMock.mockDateTime,
        updatedBy = userRepositoryMock.userMarianne,
        updatedDateTime = userRepositoryMock.mockDateTime
    )
}
