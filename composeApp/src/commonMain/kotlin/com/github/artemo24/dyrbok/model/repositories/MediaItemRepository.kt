package com.github.artemo24.dyrbok.model.repositories

import com.github.artemo24.dyrbok.model.domain.MediaItem


interface MediaItemRepository {
    fun getMediaItemIdsByAnimal(animalId: String): List<String>
    fun getMediaItemsByAnimal(animalId: String): List<MediaItem>
}
