package com.example.domain.repository

import com.example.domain.models.User

interface AuthRepository {
    fun out(){}

    fun register(user: User, password: String): Boolean

    fun login(user: User, password: String): Boolean
}