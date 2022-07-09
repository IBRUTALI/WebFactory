package com.example.data.repository

import com.example.data.storage.reviews.ReviewStorage
import com.example.domain.models.Review
import com.example.domain.repository.ReviewRepository

class ReviewRepositoryImpl(private val reviewStorage: ReviewStorage) : ReviewRepository {

    override fun addReview(review: Review) {
        val reviewMap = mapToStorage(review)
        reviewStorage.addReview(reviewMap)
    }

    override fun readReview(): ArrayList<Review> {
        val reviewList = reviewStorage.readReview()
        val reviewListMap = ArrayList<Review>()
        for (`object` in reviewList) {
            reviewListMap.add(mapToDomain(`object`))
        }

        return reviewListMap
    }

    private fun mapToStorage(review: Review): com.example.data.storage.reviews.Review {
        return com.example.data.storage.reviews.Review(
            id = review.id,
            title = review.title,
            description = review.description,
            category = review.category
        )
    }

    private fun mapToDomain(review: com.example.data.storage.reviews.Review): Review {
        return Review(
            id = review.id,
            title = review.title,
            description = review.description,
            category = review.category
        )
    }


}