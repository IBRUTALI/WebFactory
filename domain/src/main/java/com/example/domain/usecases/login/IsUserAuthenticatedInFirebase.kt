package com.example.domain.usecases.login

import com.example.domain.repository.AuthRepository

class IsUserAuthenticatedInFirebase(private val userRepository: AuthRepository) {

    fun execute(): Boolean {
        return  userRepository.isUserAuthenticatedInFirebase()
    }

}