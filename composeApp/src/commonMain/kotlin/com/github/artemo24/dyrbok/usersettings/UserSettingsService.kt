package com.github.artemo24.dyrbok.usersettings

import com.github.artemo24.dyrbok.model.enumclasses.AnimalSpecies


data class UserSettingsMap(val userId: String, val settings: MutableMap<String, Any>)


object UserSettingsService {
    private lateinit var userSettingsRepository: UserSettingsRepository

    private val userSettingsMaps = mutableMapOf<String, UserSettingsMap>()

    fun initialize(userSettingsRepository: UserSettingsRepository) {
        this.userSettingsRepository = userSettingsRepository
    }

    fun areBadgesEnabledForSpecies(userId: String, animalSpecies: AnimalSpecies): Boolean {
        val userSettingsMap = userSettingsMaps.getOrPut(userId, { userSettingsRepository.readUserSettings(userId) })

        return userSettingsMap.settings.getOrElse(badgesEnabledKeyName(animalSpecies), { "true" }) == "true"
    }

    fun setBadgesEnabledForSpecies(userId: String, animalSpecies: AnimalSpecies, enabled: Boolean) {
        val userSettingsMap = userSettingsMaps.getOrPut(userId, { UserSettingsMap(userId, mutableMapOf()) })
        userSettingsMap.settings.put(badgesEnabledKeyName(animalSpecies), enabled.toString())

        userSettingsRepository.updateUserSettings(userSettingsMap)
    }

    private fun badgesEnabledKeyName(animalSpecies: AnimalSpecies): String =
        "badges_enabled_${animalSpecies.databaseName()}"
}
