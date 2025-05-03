package com.github.artemo24.dyrbok.model.domain

import io.ktor.http.Url
import kotlinx.datetime.LocalDateTime


// Media files can be moved from one storage system to another without the media item having to change.
data class MediaFile(
    val mediaFileId: String,
    val storageSystem: String,
    val fileUrl: Url,
    // Base fields for all data objects.
    val createdBy: User,
    val createdDateTime: LocalDateTime,
    val updatedBy: User,
    val updatedDateTime: LocalDateTime,
)
