package com.example.webfactory.ui.slideshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.webfactory.model.Category;

import java.util.ArrayList;
import java.util.List;

public class SlideshowViewModel extends ViewModel {


    private MutableLiveData<String> mText;

    public SlideshowViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Оставьте ваш отзыв");
    }

    public LiveData<String> getText() {
        return mText;
    }
}