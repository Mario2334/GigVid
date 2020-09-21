package com.android.gigvid.viewModel.homescreen;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.gigvid.model.DataRepository;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.GigListResp;
import com.android.gigvid.model.repository.reponseData.ListResponse;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;
import java.util.List;

public class MyGigViewModel extends AndroidViewModel {

    private final DataRepository mDataRepository;
    private MutableLiveData<String> mText;

    public MyGigViewModel(@NotNull Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mDataRepository = DataRepository.getInstance(new WeakReference<>(application.getApplicationContext()));
        mText.setValue("This is my gig fragment");
    }

    public LiveData<ListResponse<GigListResp>> getMyGigList() {
        return mDataRepository.getMyGigList();
    }

    public LiveData<String> getText() {
        return mText;
    }
}

