package com.example.domain.repository

import com.example.domain.models.Review

interface ReviewRepository {
    fun addReview(review: Review)

    fun readReview(): ArrayList<Review>
}