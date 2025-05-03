package com.github.artemo24.dyrbok.model.domain

import kotlinx.datetime.LocalDateTime


data class User(
    val userId: String,
    val name: String,
    val userStatus: String, // UserStatus,
    val roles: List<String>,
    val emailAddress: String,
    val firebaseUserId: String,
    // Base fields for all data objects.
    // We cannot use the User class as types here.
    val createdByUserId: String,
    val createdDateTime: LocalDateTime,
    val updatedByUserId: String,
    val updatedDateTime: LocalDateTime,
)
