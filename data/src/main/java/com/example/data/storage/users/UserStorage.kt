package com.example.data.storage.users

interface UserStorage {

    fun out()

    fun register(user: User, password: String): Boolean

    fun login(user: User, password: String): Boolean

}