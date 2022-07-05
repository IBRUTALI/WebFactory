package com.example.data.repository

import com.example.data.storage.UserStorage
import com.example.domain.models.User
import com.example.domain.repository.AuthRepository


class AuthRepositoryImpl(private val userStorage: UserStorage) : AuthRepository {

    override fun out() {
        userStorage.out()
    }

    override fun login(user: User, password: String): Boolean {
        val userMap = mapToStorage(user)
        return userStorage.login(userMap, password)
    }

    override fun register(user: User, password: String): Boolean {
        val userMap = mapToStorage(user)
        userStorage.register(userMap, password)
        return true
    }

    private fun mapToDomain(user: User): User {
        return User(email = user.getEmail())
    }

    private fun mapToStorage(user: User): com.example.data.storage.User {
        return com.example.data.storage.User(email = user.getEmail())
    }

}