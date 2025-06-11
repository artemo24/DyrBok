package com.github.artemo24.dyrbok.model.domain

import kotlinx.datetime.LocalDateTime


data class MediaItem(
    val mediaItemId: String,
    val creator: User,
    val captureDateTime: LocalDateTime,
    val fileSize: Long,
    val fileMd5: String,
    val mediaFile: MediaFile,
    val auditInfo: AuditInfo,
)
