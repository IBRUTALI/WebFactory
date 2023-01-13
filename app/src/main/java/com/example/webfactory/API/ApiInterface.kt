package com.example.webfactory.API

import retrofit2.http.GET
import com.example.webfactory.model.mainNews
import retrofit2.Call
import retrofit2.http.Query

interface ApiInterface {
    @GET("top-headlines")
    fun getNews(
        @Query("country") country: String?,
        @Query("category") category: String?,
        @Query("pageSize") pageSize: Int,
        @Query("apiKey") apiKey: String?
    ): Call<mainNews?>?

    companion object {
        const val BASE_URL = "https://newsapi.org/v2/"
    }
}