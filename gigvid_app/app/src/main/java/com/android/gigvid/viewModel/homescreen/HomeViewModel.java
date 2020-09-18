package com.android.gigvid.viewModel.homescreen;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.android.gigvid.model.DataRepository;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.GigListRespStatus;

import java.lang.ref.WeakReference;

public class HomeViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;
    private DataRepository mDataRepository;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        mDataRepository = DataRepository.getInstance(new WeakReference<>(application.getApplicationContext()));
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<GigListRespStatus> getGigListLiveData(){
        LiveData<GigListRespStatus> gigList = mDataRepository.getGigList();
        return Transformations.map(gigList, new Function<GigListRespStatus, GigListRespStatus>() {
            @Override
            public GigListRespStatus apply(GigListRespStatus input) {
//                Modification to Data as per UI to be done here
                return input;
            }
        });
    }
    public LiveData<String> getText() {
        return mText;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        clear();
    }

    private void clear() {
        mDataRepository = null;
    }
}