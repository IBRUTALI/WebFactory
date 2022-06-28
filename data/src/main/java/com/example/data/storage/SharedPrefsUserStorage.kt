package com.example.data.storage

import android.content.Context
import com.google.firebase.auth.FirebaseAuth

private const val SHARED_PREFS_NAME = "shared_prefs_name"


class SharedPrefUserStorage(context: Context): UserStorage {


    private val sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    override fun out() {
        FirebaseAuth.getInstance().signOut()
    }


}