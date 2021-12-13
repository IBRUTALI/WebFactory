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
    List<Polls> pollsList= new ArrayList<>();

    public void addItem(int count, String title, String var1, String var2, String var3) {
        pollsList.add(new Polls(count, title, var1, var2, var3));
    }
    public  List<Polls> getPollsList(){
        return pollsList;
    }
    public PollsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Окно анкетирования");
    }

    public LiveData<String> getText() {
        return mText;
    }
}