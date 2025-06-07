package com.github.artemo24.dyrbok.model.domain

import kotlinx.datetime.LocalDateTime


data class AuditInfo(
    val createdBy: User,
    val createdAt: LocalDateTime,
    val updatedBy: User = createdBy,
    val updatedAt: LocalDateTime = createdAt,
)
