package com.skypan.myapplication.ui.Switch;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SwitchViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SwitchViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}