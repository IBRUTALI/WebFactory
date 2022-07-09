package com.example.data.storage.reviews

interface ReviewStorage {

    fun addReview(review: Review)

    fun readReview(): ArrayList<Review>

}