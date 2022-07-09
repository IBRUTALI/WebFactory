package com.example.domain.usecases.review

import com.example.domain.models.Review
import com.example.domain.repository.ReviewRepository

class ReadDataReviewUseCase(private val reviewRepository: ReviewRepository) {

    fun execute(): ArrayList<Review>{
        return reviewRepository.readReview()
    }

}