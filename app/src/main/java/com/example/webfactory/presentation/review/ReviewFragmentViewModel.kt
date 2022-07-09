package com.example.webfactory.presentation.review

import androidx.lifecycle.ViewModel
import com.example.domain.models.Review
import com.example.domain.usecases.review.AddReviewUseCase
import com.example.domain.usecases.review.ReadDataReviewUseCase

class ReviewFragmentViewModel(
    private val addReviewUseCase: AddReviewUseCase,
    private val readDataReviewUseCase: ReadDataReviewUseCase
    ) : ViewModel(){

        fun addReview(review: Review){
            addReviewUseCase.execute(review = review)
        }

        fun readReview(): ArrayList<Review>{
           return readDataReviewUseCase.execute()
        }

    }