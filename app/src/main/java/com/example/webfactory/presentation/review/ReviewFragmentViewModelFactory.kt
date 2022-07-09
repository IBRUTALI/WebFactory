package com.example.webfactory.presentation.review

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data.repository.ReviewRepositoryImpl
import com.example.data.storage.reviews.SharedPrefsReviewStorage
import com.example.domain.usecases.review.AddReviewUseCase
import com.example.domain.usecases.review.ReadDataReviewUseCase

class ReviewFragmentViewModelFactory(context: Context): ViewModelProvider.Factory {


    private val reviewRepository by lazy(LazyThreadSafetyMode.NONE) {
        ReviewRepositoryImpl(reviewStorage = SharedPrefsReviewStorage(context = context))
    }

    private val addReviewUseCase by lazy(LazyThreadSafetyMode.NONE) {
        AddReviewUseCase(reviewRepository = reviewRepository)
    }

    private val readDataReviewUseCase by lazy(LazyThreadSafetyMode.NONE) {
        ReadDataReviewUseCase(reviewRepository = reviewRepository)
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ReviewFragmentViewModel(
            addReviewUseCase = addReviewUseCase,
            readDataReviewUseCase = readDataReviewUseCase
        ) as T
    }

}