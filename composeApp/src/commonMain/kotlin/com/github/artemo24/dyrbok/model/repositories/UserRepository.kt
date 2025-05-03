package com.github.artemo24.dyrbok.model.repositories

import com.github.artemo24.dyrbok.model.domain.User


interface UserRepository {
    fun readAllUsers(): List<User>
    fun readAllActiveUsers(): List<User>
}
