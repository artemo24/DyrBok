package com.github.artemo24.dyrbok.model.domain

import kotlinx.datetime.LocalDateTime


data class User(
    val userId: String,
    val name: String,
    val userStatus: String, // UserStatus,
    val roles: List<String>,
    val emailAddress: String,
    val firebaseUserId: String,
    // We cannot use the AuditInfo and User classes here.
    val createdByUserId: String,
    val createdAt: LocalDateTime,
    val updatedByUserId: String = createdByUserId,
    val updatedAt: LocalDateTime = createdAt,
)
