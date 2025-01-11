package com.github.artemo24.dyrbok.backupandrestore.dataclasses


data class FirestoreObjects(
    val logging: List<Logging>,
    val mediaItems: List<MediaItem>,
    val pets: List<Pet>,
    val screenSwitches: List<ScreenSwitch>,
    val settings: List<Settings>,
    val users: List<User>,
)
