package com.github.artemo24.dyrbok.usersettings


interface UserSettingsRepository {
    fun readUserSettings(userId: String): UserSettingsMap
    fun updateUserSettings(userSettingsMap: UserSettingsMap)
}
