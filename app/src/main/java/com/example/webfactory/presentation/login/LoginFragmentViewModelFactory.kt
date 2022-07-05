package com.example.webfactory.presentation.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.repository.AuthRepositoryImpl
import com.example.data.storage.SharedPrefUserStorage
import com.example.domain.usecases.login.SignInEmailUseCase
import com.example.domain.usecases.login.RegisterUseCase

class LoginFragmentViewModelFactory(context: Context): ViewModelProvider.Factory {

    private val userRepository by lazy(LazyThreadSafetyMode.NONE) {
        AuthRepositoryImpl(userStorage = SharedPrefUserStorage(context = context))
    }

    private val authUseCase by lazy(LazyThreadSafetyMode.NONE) {
        SignInEmailUseCase(userRepository = userRepository)
    }

    private val registerUseCase by lazy(LazyThreadSafetyMode.NONE) {
        RegisterUseCase(userRepository = userRepository)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginFragmentViewModel(
            authUseCase = authUseCase,
            registerUseCase = registerUseCase) as T
    }
}