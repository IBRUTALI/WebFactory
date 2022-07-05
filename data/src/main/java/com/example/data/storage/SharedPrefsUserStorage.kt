package com.example.data.storage

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SharedPrefUserStorage(private val context: Context) : UserStorage {

    private var auth: FirebaseAuth = Firebase.auth

    override fun out() {
        auth.signOut()
    }

    override fun login(user: User, password: String): Boolean {
        auth.signInWithEmailAndPassword(user.getEmail(), password)
        return true
    }

    override fun register(user: User, password: String): Boolean {
        auth.createUserWithEmailAndPassword(user.getEmail(), password)
        return true
    }


}