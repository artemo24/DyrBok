package com.github.artemo24.dyrbok.featureflags.domain

import com.github.freekdb.firebasetest1.model.settings.Settings


enum class FeatureFlagStatus {
    OFF,
    TEST,
    ON,
}


object FeatureFlags {
    fun isFeatureBadgesEnabled(userEmailAddress: String): Boolean =
        isFeatureEnabled(featureFlagName = "feature_flag_badges", userEmailAddress)

    private fun isFeatureEnabled(featureFlagName: String, userEmailAddress: String): Boolean {
        val featureFlagSetting = Settings.getSetting(key = featureFlagName)
        val featureFlagValue = (featureFlagSetting?.value ?: FeatureFlagStatus.OFF.name.lowercase()) as String

        return when {
            featureFlagValue == FeatureFlagStatus.ON.name.lowercase() -> true

            featureFlagValue.startsWith(prefix = "${FeatureFlagStatus.TEST.name.lowercase()}:") -> {
                val testUserEmailAddresses = featureFlagValue.substringAfter(delimiter = ":").split("|")
                testUserEmailAddresses.contains(userEmailAddress)
            }

            else -> false
        }
    }
}
