package com.example.domain.usecases.review

import com.example.domain.models.Review
import com.example.domain.repository.ReviewRepository

class AddReviewUseCase(private val reviewRepository: ReviewRepository) {

    fun execute(review: Review){
        reviewRepository.addReview(review = review)
    }
}