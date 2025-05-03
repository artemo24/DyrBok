package com.github.artemo24.dyrbok.model.domain

import kotlinx.datetime.LocalDateTime


data class MediaItem(
    val mediaItemId: String,
    val creator: User,
    val captureDateTime: LocalDateTime,
    val fileSize: Long,
    val fileMd5: ByteArray,
    val mediaFile: MediaFile,
    // Base fields for all data objects.
    val createdBy: User,
    val createdDateTime: LocalDateTime,
    val updatedBy: User,
    val updatedDateTime: LocalDateTime,
)
