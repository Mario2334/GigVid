package com.android.gigvid.viewModel.homescreen;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.android.gigvid.model.DataRepository;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.CreateGigReqBody;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.CreateGigResp;
import com.android.gigvid.model.repository.reponseData.DataResponse;

import java.lang.ref.WeakReference;

public class HostGigViewModel extends AndroidViewModel {

    private MutableLiveData<String> mText;
    private DataRepository mDataRepository;


    public HostGigViewModel(@NonNull Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
        mDataRepository = DataRepository.getInstance(new WeakReference<>(application.getApplicationContext()));
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<DataResponse<CreateGigResp>> createGig(CreateGigReqBody createGig){
        LiveData<DataResponse<CreateGigResp>> createGigEvent = mDataRepository.createGig(createGig);
        return Transformations.map(createGigEvent, new Function<DataResponse<CreateGigResp>, DataResponse<CreateGigResp>>() {
            @Override
            public DataResponse<CreateGigResp> apply(DataResponse<CreateGigResp> input) {
//                Modification to Data as per UI to be done here
                return input;
            }
        });
    }
}