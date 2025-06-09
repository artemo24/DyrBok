package com.github.freekdb.firebasetest1.model.settings

import com.github.artemo24.dyrbok.featureflags.domain.FeatureFlagStatus
import com.github.artemo24.dyrbok.featureflags.domain.featureFlagBadges
import com.github.freekdb.firebasetest1.model.dataclasses.Setting


// Note: this object contains a subset of the app's settings for development purposes.
object Settings {
    // Log: private val logTag = Settings::class.simpleName

    private lateinit var serverSettingsMap: MutableMap<String, Setting>

    init {
        refreshSettings()
    }

    private fun refreshSettings() {
        val settingValue = "${FeatureFlagStatus.TEST.name.lowercase()}:freekdb@gmail.com"
        val featureFlagBadgesSetting = Setting(id = "", key = featureFlagBadges, value = settingValue)
        serverSettingsMap = mutableMapOf(featureFlagBadgesSetting.key to featureFlagBadgesSetting)
    }

    fun getSetting(key: String): Setting? =
        serverSettingsMap[key]
}
