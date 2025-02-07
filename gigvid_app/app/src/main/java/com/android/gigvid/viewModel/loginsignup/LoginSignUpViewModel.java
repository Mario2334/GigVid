package com.android.gigvid.viewModel.loginsignup;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.android.gigvid.model.DataRepository;
import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.LoginResp;
import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.SignUpReqBody;
import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.SignUpResp;
import com.android.gigvid.model.repository.reponseData.DataResponse;

import java.lang.ref.WeakReference;


/***
 * Purpose: Prep the data for Login & Sign Up UI
 */
public class LoginSignUpViewModel extends AndroidViewModel {
    private DataRepository mDataRepository;

    public LoginSignUpViewModel(@NonNull Application application) {
        super(application);
        mDataRepository = DataRepository.getInstance(new WeakReference<>(application.getApplicationContext()));
    }

    public LiveData<DataResponse<LoginResp>> login(String username, String pass) {

        LiveData<DataResponse<LoginResp>> source = mDataRepository.loginToGigVid(username, pass);
        return Transformations.map(source, new Function<DataResponse<LoginResp>, DataResponse<LoginResp>>() {
            @Override
            public DataResponse<LoginResp> apply(DataResponse<LoginResp> input) {
//                Modification to Data as per UI to be done here
                return input;
            }
        });
    }

    public LiveData<DataResponse<SignUpResp>> signUp(SignUpReqBody signUpBody) {
        LiveData<DataResponse<SignUpResp>> source = mDataRepository.signUpForGigVid(signUpBody);

        return Transformations.map(source, new Function<DataResponse<SignUpResp>, DataResponse<SignUpResp>>() {
            @Override
            public DataResponse<SignUpResp> apply(DataResponse<SignUpResp> input) {
                //                Modification to Data as per UI to be done here
                return input;
            }
        });
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
