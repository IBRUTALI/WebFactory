package com.example.webfactory.API

import retrofit2.Retrofit
import com.example.webfactory.API.ApiInterface
import com.example.webfactory.API.ApiUtilities
import retrofit2.converter.gson.GsonConverterFactory

object ApiUtilities {
    private var retrofit: Retrofit? = null
    @JvmStatic
    val apiInterface: ApiInterface
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(ApiInterface.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!.create(ApiInterface::class.java)
        }
}