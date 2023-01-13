package com.example.data.repository

import android.util.Log
import com.example.domain.models.User
import com.example.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class AuthRepositoryImpl : AuthRepository {
    private val auth: FirebaseAuth = Firebase.auth

    override fun isUserAuthenticatedInFirebase() = auth.currentUser != null

    override fun out() {
        auth.signOut()
    }

    override fun login(user: User, password: String): Flow<Boolean> = flow {
        try {
            auth.signInWithEmailAndPassword(user.getEmail(), password).await()
            Log.d("AAA", "TRUE")
            emit(true)
        } catch (e: Exception) {
            Log.d("AAA", "FALSE")
            emit(false)
        }
    }

    override fun getFirebaseAuthState() = callbackFlow  {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser == null)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }

    override fun register(user: User, password: String): Boolean {
        auth.createUserWithEmailAndPassword(user.getEmail(), password)
        return true
    }
}