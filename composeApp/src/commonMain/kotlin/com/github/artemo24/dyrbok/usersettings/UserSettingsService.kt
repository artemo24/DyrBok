package com.github.artemo24.dyrbok.usersettings

import com.github.artemo24.dyrbok.model.enumclasses.AnimalSpecies


data class UserSettingsMap(val userId: String, val settings: MutableMap<String, Any>)


fun getDefaultUserSettingsMap(userId: String): UserSettingsMap =
    UserSettingsMap(
        userId,
        settings = AnimalSpecies.entries
            .filter { animalSpecies -> animalSpecies != AnimalSpecies.UNKNOWN_ANIMAL_SPECIES }
            .associate { animalSpecies -> UserSettingsService.badgesEnabledKeyName(animalSpecies) to "true" }
            .toMutableMap()
    )


class UserSettingsService(private val userSettingsRepository: UserSettingsRepository) {
    private val userSettingsMaps = mutableMapOf<String, UserSettingsMap>()

    suspend fun getBadgesEnabledForSpecies(userId: String, animalSpecies: AnimalSpecies): Boolean {
        val userSettingsMap = userSettingsMaps.getOrPut(userId) { userSettingsRepository.readUserSettings(userId) }

        return userSettingsMap.settings[badgesEnabledKeyName(animalSpecies)] == "true"
    }

    suspend fun setBadgesEnabledForSpecies(userId: String, animalSpecies: AnimalSpecies, enabled: Boolean) {
        val userSettingsMap = userSettingsMaps.getOrPut(userId) { getDefaultUserSettingsMap(userId) }
        userSettingsMap.settings[badgesEnabledKeyName(animalSpecies)] = enabled.toString()

        userSettingsRepository.updateUserSettings(userSettingsMap)
    }

    companion object {
        internal fun badgesEnabledKeyName(animalSpecies: AnimalSpecies): String =
            "badges_enabled_${animalSpecies.databaseName()}"
    }
}
