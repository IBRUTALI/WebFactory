package com.example.domain.usecases.main

import com.example.domain.repository.UserRepository

class OptionsItemUseCase(private val userRepository: UserRepository) {


    fun execute()  {
        userRepository.out()
    }


}