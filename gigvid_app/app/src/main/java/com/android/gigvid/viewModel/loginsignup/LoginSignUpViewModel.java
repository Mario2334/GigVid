package com.android.gigvid.viewModel.loginsignup;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.android.gigvid.model.DataRepository;
import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.LoginRespStatus;
import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.SignUp;
import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.SignUpResStatus;

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

    public LiveData<LoginRespStatus> login(String username, String pass) {
        LiveData<LoginRespStatus> source = mDataRepository.loginToGigVid(username, pass);

        return Transformations.map(source, new Function<LoginRespStatus, LoginRespStatus>() {
            @Override
            public LoginRespStatus apply(LoginRespStatus input) {
//                Modification to Data as per UI to be done here
                return input;
            }
        });
    }

    public LiveData<SignUpResStatus> signUp(SignUp signUpBody) {
        LiveData<SignUpResStatus> source = mDataRepository.signUpForGigVid(signUpBody);

        return Transformations.map(source, new Function<SignUpResStatus, SignUpResStatus>() {
            @Override
            public SignUpResStatus apply(SignUpResStatus input) {
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
