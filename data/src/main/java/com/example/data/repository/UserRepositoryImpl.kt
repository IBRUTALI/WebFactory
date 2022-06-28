package com.example.data.repository

import com.example.data.storage.UserStorage
import com.example.domain.repository.UserRepository


class UserRepositoryImpl(private val userStorage: UserStorage): UserRepository{

    override fun out(){
        userStorage.out()
    }
}