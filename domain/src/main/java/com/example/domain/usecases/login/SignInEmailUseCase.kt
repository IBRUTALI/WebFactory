package com.example.domain.usecases.login

import com.example.domain.models.User
import com.example.domain.repository.AuthRepository

class SignInEmailUseCase(private val userRepository: AuthRepository) {

    fun execute(user: User, password: String): Boolean {
       return userRepository.login(user, password)
    }

}