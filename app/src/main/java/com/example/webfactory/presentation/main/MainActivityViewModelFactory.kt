package com.example.webfactory.presentation.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.repository.UserRepositoryImpl
import com.example.data.storage.SharedPrefUserStorage
import com.example.domain.usecases.main.OptionsItemUseCase

class MainActivityViewModelFactory(context: Context) : ViewModelProvider.Factory {

    private val userRepository by lazy(LazyThreadSafetyMode.NONE) {
        UserRepositoryImpl(userStorage = SharedPrefUserStorage(context = context))
    }

    private val optionsItemUseCase by lazy(LazyThreadSafetyMode.NONE) {
        OptionsItemUseCase(userRepository = userRepository)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainActivityViewModel(
            optionsItemUseCase = optionsItemUseCase
        ) as T
    }
}