package com.android.gigvid.viewModel.homescreen;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.android.gigvid.model.DataRepository;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.CreateGig;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.CreateGigRespStatus;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.GigListRespStatus;

public class HostGigViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;
    private DataRepository mDataRepository;


    public HostGigViewModel(@NonNull Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<CreateGigRespStatus> createGig(CreateGig createGig){
        LiveData<CreateGigRespStatus> createGigEvent = mDataRepository.createGig(createGig);
        return Transformations.map(createGigEvent, new Function<CreateGigRespStatus, CreateGigRespStatus>() {
            @Override
            public CreateGigRespStatus apply(CreateGigRespStatus input) {
//                Modification to Data as per UI to be done here
                return input;
            }
        });
    }
}