package com.github.artemo24.dyrbok.model.repositories

import com.github.artemo24.dyrbok.model.domain.User
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.Month


class UserRepositoryMock : UserRepository {
    val mockDateTime = LocalDateTime(
        year = 2025,
        month = Month.FEBRUARY,
        dayOfMonth = 1,
        hour = 15,
        minute = 38,
        second = 28
    )

    private val userAdministrator = User(
        userId = "Administrator-id",
        name = "Administrator",
        userStatus = "active",
        roles = listOf("administrator"),
        emailAddress = "",
        firebaseUserId = "",
        createdByUserId = "",
        createdAt = mockDateTime,
    )

    val userJosh = User(
        userId = "Josh-id",
        name = "Josh",
        userStatus = "retired",
        roles = emptyList(),
        emailAddress = "",
        firebaseUserId = "",
        createdByUserId = userAdministrator.userId,
        createdAt = mockDateTime,
    )

    val userMelissa = User(
        userId = "Melissa-id",
        name = "Melissa",
        userStatus = "active",
        roles = emptyList(),
        emailAddress = "",
        firebaseUserId = "",
        createdByUserId = userAdministrator.userId,
        createdAt = mockDateTime,
    )

    val userSophie = User(
        userId = "Sophie-id",
        name = "Sophie",
        userStatus = "active",
        roles = emptyList(),
        emailAddress = "",
        firebaseUserId = "",
        createdByUserId = userAdministrator.userId,
        createdAt = mockDateTime,
        updatedByUserId = userAdministrator.userId,
        updatedAt = mockDateTime,
    )

    private val mockUsers = listOf(userJosh, userMelissa, userSophie)

    override fun readAllUsers(): List<User> =
        mockUsers

    override fun readAllActiveUsers(): List<User> =
        mockUsers.filter { it.userStatus == "active" }
}
