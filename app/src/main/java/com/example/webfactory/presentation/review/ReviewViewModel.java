package com.example.webfactory.presentation.review;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReviewViewModel extends ViewModel {


    private MutableLiveData<String> mText;

    public ReviewViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Оставьте ваш отзыв");
    }

    public LiveData<String> getText() {
        return mText;
    }
}