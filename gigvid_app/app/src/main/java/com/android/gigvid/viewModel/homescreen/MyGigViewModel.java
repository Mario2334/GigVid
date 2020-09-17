package com.android.gigvid.viewModel.homescreen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyGigViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyGigViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is my gig fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}

