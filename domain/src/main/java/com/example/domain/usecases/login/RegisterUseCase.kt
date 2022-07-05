package com.example.domain.usecases.login

import com.example.domain.models.User
import com.example.domain.repository.AuthRepository

class RegisterUseCase(private val userRepository: AuthRepository) {

    fun execute(user: User, password: String): Boolean {
        userRepository.register(user, password)
        return true
    }

}