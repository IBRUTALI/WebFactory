package com.example.domain.usecases.login

import com.example.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope

class IsUserAuthenticatedInFirebase(
    private val userRepository: AuthRepository
) {

    fun execute(): Boolean {
            return userRepository.isUserAuthenticatedInFirebase()
    }

}