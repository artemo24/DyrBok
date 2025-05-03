package com.github.artemo24.dyrbok.model.enumclasses


enum class AdoptionStatus(val websiteName: String) {
    AVAILABLE(websiteName = ""),
    RESERVED(websiteName = "Gereserveerd"),
    ADOPTED(websiteName = "Geplaatst"),
    UNKNOWN_ADOPTION_STATUS(websiteName = "");

    companion object {
        private val resourceIdMap = mutableMapOf<AdoptionStatus, Int>()

        fun initializeResourceIdMap(map: Map<AdoptionStatus, Int>) =
            resourceIdMap.putAll(map)

        fun getStringResourceId(adoptionStatus: AdoptionStatus): Int =
            resourceIdMap[adoptionStatus] ?: -1

        fun databaseValueToAdoptionStatus(databaseValue: String): AdoptionStatus {
            return try {
                AdoptionStatus.valueOf(databaseValue.uppercase())

            } catch (e: IllegalArgumentException) {
                UNKNOWN_ADOPTION_STATUS
            }
        }
    }
}
