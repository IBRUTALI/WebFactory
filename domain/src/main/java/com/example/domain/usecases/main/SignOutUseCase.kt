package com.example.domain.usecases.main

import com.example.domain.repository.AuthRepository

class SignOutUseCase(private val userRepository: AuthRepository) {


    fun execute()  {
        userRepository.out()
    }


}