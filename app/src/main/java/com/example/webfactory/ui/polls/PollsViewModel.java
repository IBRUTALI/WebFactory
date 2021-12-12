package com.example.webfactory.ui.polls;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PollsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PollsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is poll fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}