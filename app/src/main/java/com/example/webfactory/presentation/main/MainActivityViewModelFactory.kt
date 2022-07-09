package com.example.webfactory.presentation.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.repository.AuthRepositoryImpl
import com.example.data.storage.users.SharedPrefUserStorage
import com.example.domain.usecases.main.SignOutUseCase

class MainActivityViewModelFactory(context: Context) : ViewModelProvider.Factory {

    private val userRepository by lazy(LazyThreadSafetyMode.NONE) {
        AuthRepositoryImpl(userStorage = SharedPrefUserStorage(context = context))
    }

    private val optionsItemUseCase by lazy(LazyThreadSafetyMode.NONE) {
        SignOutUseCase(userRepository = userRepository)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainActivityViewModel(
            optionsItemUseCase = optionsItemUseCase
        ) as T
    }
}