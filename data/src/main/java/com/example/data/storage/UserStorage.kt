package com.example.data.storage

interface UserStorage {

    fun out()

    fun register(user: User, password: String): Boolean

    fun login(user: User, password: String): Boolean

}