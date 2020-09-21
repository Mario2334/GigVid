package com.android.gigvid.viewModel.homescreen;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.gigvid.model.DataRepository;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.profile.BankDetailsReqBody;
import com.android.gigvid.model.repository.reponseData.DataResponse;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;

public class ProfileViewModel extends AndroidViewModel {

    private final DataRepository mDataRepository;
    private MutableLiveData<String> mText;

    public ProfileViewModel(@NotNull Application application) {
        super(application);
        mText = new MutableLiveData<>();
        mDataRepository = DataRepository.getInstance(new WeakReference<>(application.getApplicationContext()));
        mText.setValue("This is profile fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<DataResponse<String>> addBankDetails() {
        BankDetailsReqBody bankDetailsReqBody = new BankDetailsReqBody("Gaurav Kumar", "HDFC0000053", "765432123456789");
        return mDataRepository.updateBankDetails(bankDetailsReqBody);
    }
}
