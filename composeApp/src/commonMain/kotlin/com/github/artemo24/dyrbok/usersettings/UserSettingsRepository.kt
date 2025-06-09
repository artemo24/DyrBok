package com.github.artemo24.dyrbok.usersettings


interface UserSettingsRepository {
    suspend fun readUserSettings(userId: String): UserSettingsMap
    suspend fun updateUserSettings(userSettingsMap: UserSettingsMap)
}
