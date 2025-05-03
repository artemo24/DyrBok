package com.github.artemo24.dyrbok.repositories

import com.github.artemo24.dyrbok.model.domain.User
import com.github.artemo24.dyrbok.model.repositories.UserRepository
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
        createdDateTime = mockDateTime,
        updatedByUserId = "",
        updatedDateTime = mockDateTime,
    )

    val userJosh = User(
        userId = "Josh-id",
        name = "Josh",
        userStatus = "retired",
        roles = emptyList(),
        emailAddress = "",
        firebaseUserId = "",
        createdByUserId = userAdministrator.userId,
        createdDateTime = mockDateTime,
        updatedByUserId = userAdministrator.userId,
        updatedDateTime = mockDateTime,
    )

    val userMarianne = User(
        userId = "Marianne-id",
        name = "Marianne",
        userStatus = "active",
        roles = emptyList(),
        emailAddress = "",
        firebaseUserId = "",
        createdByUserId = userAdministrator.userId,
        createdDateTime = mockDateTime,
        updatedByUserId = userAdministrator.userId,
        updatedDateTime = mockDateTime,
    )

    val userSuzan = User(
        userId = "Suzan-id",
        name = "Suzan",
        userStatus = "active",
        roles = emptyList(),
        emailAddress = "",
        firebaseUserId = "",
        createdByUserId = userAdministrator.userId,
        createdDateTime = mockDateTime,
        updatedByUserId = userAdministrator.userId,
        updatedDateTime = mockDateTime,
    )

    private val mockUsers = listOf(userJosh, userMarianne, userSuzan)

    override fun readAllUsers(): List<User> =
        mockUsers

    override fun readAllActiveUsers(): List<User> =
        mockUsers.filter { it.userStatus == "active" }
}
