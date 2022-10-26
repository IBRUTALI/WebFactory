package com.example.webfactory.presentation.login

import androidx.lifecycle.ViewModel
import com.example.domain.models.User
import com.example.domain.usecases.login.IsUserAuthenticatedInFirebase
import com.example.domain.usecases.login.RegisterUseCase
import com.example.domain.usecases.login.SignInEmailUseCase

class LoginFragmentViewModel(
    private val authUseCase: SignInEmailUseCase,
    private val registerUseCase: RegisterUseCase,
    private val isUserAuthenticatedInFirebase: IsUserAuthenticatedInFirebase
) : ViewModel() {

    fun userAuthState(): Boolean {
        return isUserAuthenticatedInFirebase.execute()
    }

    fun login(user: User, password: String) {
        authUseCase.execute(user, password)
    }

    fun register(user: User, password: String): Boolean {
        registerUseCase.execute(user, password)
        return true
    }

}