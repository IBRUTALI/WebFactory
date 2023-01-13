package com.example.domain.models

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.lang.Exception

interface Response<T> {

    fun execute(callback: Callback<T>)

    fun cancel()

    interface Callback<T> {

        fun onSuccess(value: T)

        fun onError(e: Exception)

    }

}

fun <T> Response<T>.asFlow(): Flow<T> {
    return callbackFlow {
        execute(object: Response.Callback<T> {

            override fun onSuccess(value: T) {
                trySend(value)
                close()
            }

            override fun onError(e: Exception) {
                close(e)
            }

        })
        awaitClose { this@asFlow.cancel() }
    }
}