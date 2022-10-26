package com.example.domain.repository

import com.example.domain.models.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun isUserAuthenticatedInFirebase(): Boolean

    fun out(){}

    fun login(user: User, password: String)

    fun register(user: User, password: String): Boolean


}