package com.example.webfactory.presentation.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.domain.models.Response
import com.example.domain.models.User
import com.example.domain.usecases.login.IsUserAuthenticatedInFirebase
import com.example.domain.usecases.login.RegisterUseCase
import com.example.domain.usecases.login.SignInEmailUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class LoginFragmentViewModel(
    private val authUseCase: SignInEmailUseCase,
    private val registerUseCase: RegisterUseCase,
    private val isUserAuthenticatedInFirebase: IsUserAuthenticatedInFirebase

) : ViewModel() {
    val isUserAuthenticated get() = isUserAuthenticatedInFirebase.execute()
    //var signInResponse = MutableLiveData<Response<Boolean>>(Response(false))

    fun userAuthState(): Boolean {
        return isUserAuthenticatedInFirebase.execute()
    }

    fun login(user: User, password: String): Flow<Boolean> {
        Log.d("AAA", "TRUE")
        return authUseCase.execute(user, password)
    }

    fun register(user: User, password: String): Boolean {
        registerUseCase.execute(user, password)
        return true
    }
}
