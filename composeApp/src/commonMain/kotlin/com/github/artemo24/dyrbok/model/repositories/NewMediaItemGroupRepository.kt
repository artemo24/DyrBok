package com.github.artemo24.dyrbok.model.repositories

import com.github.artemo24.dyrbok.newmediaitems.NewMediaItemGroup


interface NewMediaItemGroupRepository {
    fun createNewMediaItemGroup(newMediaItemGroup: NewMediaItemGroup)
    fun readAllNewMediaItemGroups(): List<NewMediaItemGroup>
    fun readNewMediaItemGroups(userId: String): List<NewMediaItemGroup>
    fun updateNewMediaItemGroup(newMediaItemGroup: NewMediaItemGroup)
    fun deleteNewMediaItemGroup(newMediaItemGroup: NewMediaItemGroup)
}
