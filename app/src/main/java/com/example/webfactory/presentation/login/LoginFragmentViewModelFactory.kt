package com.example.webfactory.presentation.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.repository.AuthRepositoryImpl
import com.example.data.storage.users.SharedPrefUserStorage
import com.example.domain.usecases.login.IsUserAuthenticatedInFirebase
import com.example.domain.usecases.login.SignInEmailUseCase
import com.example.domain.usecases.login.RegisterUseCase

class LoginFragmentViewModelFactory(context: Context): ViewModelProvider.Factory {

    private val userRepository by lazy(LazyThreadSafetyMode.NONE) {
        AuthRepositoryImpl()
    }

    private val isUserAuthenticatedInFirebase by lazy(LazyThreadSafetyMode.NONE) {
        IsUserAuthenticatedInFirebase(userRepository = userRepository)
    }

    private val authUseCase by lazy(LazyThreadSafetyMode.NONE) {
        SignInEmailUseCase(userRepository = userRepository)
    }

    private val registerUseCase by lazy(LazyThreadSafetyMode.NONE) {
        RegisterUseCase(userRepository = userRepository)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginFragmentViewModel(
            isUserAuthenticatedInFirebase = isUserAuthenticatedInFirebase,
            authUseCase = authUseCase,
            registerUseCase = registerUseCase) as T
    }
}