package com.example.webfactory.ui.polls;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.webfactory.model.Category;
import com.example.webfactory.model.Polls;

import java.util.ArrayList;
import java.util.List;

public class PollsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PollsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Окно анкетирования");
    }

    public LiveData<String> getText() {
        return mText;
    }
}