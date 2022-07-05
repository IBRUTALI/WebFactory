package com.example.webfactory.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.domain.models.User
import com.example.domain.usecases.login.SignInEmailUseCase
import com.example.domain.usecases.login.RegisterUseCase

class LoginFragmentViewModel(
    private val authUseCase: SignInEmailUseCase,
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    fun login(user: User, password: String): Boolean{
        Log.d("AAA", "LoginFragmentViewModel ${authUseCase.execute(user, password)}")
        return authUseCase.execute(user, password)
    }

    fun register(user: User, password: String): Boolean{
        registerUseCase.execute(user, password)
        return true
    }

}