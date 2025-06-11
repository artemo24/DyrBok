package com.github.artemo24.dyrbok.newmediaitems


interface NewMediaItemGroupRepository {
    fun createNewMediaItemGroup(newMediaItemGroup: NewMediaItemGroup)
    fun readAllNewMediaItemGroups(): List<NewMediaItemGroup>
    fun readNewMediaItemGroups(userId: String): List<NewMediaItemGroup>
    fun updateNewMediaItemGroup(newMediaItemGroup: NewMediaItemGroup)
    fun deleteNewMediaItemGroup(newMediaItemGroup: NewMediaItemGroup)
}
