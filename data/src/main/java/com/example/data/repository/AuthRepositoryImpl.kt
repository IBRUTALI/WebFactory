package com.example.data.repository

import android.util.Log
import com.example.domain.models.User
import com.example.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*


class AuthRepositoryImpl : AuthRepository {
    private val auth: FirebaseAuth = Firebase.auth
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun isUserAuthenticatedInFirebase() = auth.currentUser != null

    override fun out() {
        auth.signOut()
    }

    override fun login(user: User, password: String) {
        auth.signInWithEmailAndPassword(user.getEmail(), password)
    }

    override fun register(user: User, password: String): Boolean {
        //   val userMap = mapToStorage(user)
        auth.createUserWithEmailAndPassword(user.getEmail(), password)
        return true
    }

//    private suspend fun authResult(user: User, password: String): Boolean =
//        coroutineScope.async(Dispatchers.IO) {
//            auth.signInWithEmailAndPassword(user.getEmail(), password)
//        return@async auth.signInWithEmailAndPassword(user.getEmail(), password).isSuccessful
//    }.await()




//    private fun mapToDomain(user: User): User {
//        return User(email = user.getEmail())
//    }

//    private fun mapToStorage(user: User): com.example.data.storage.users.User {
//        return com.example.data.storage.users.User(email = user.getEmail())
//    }

}