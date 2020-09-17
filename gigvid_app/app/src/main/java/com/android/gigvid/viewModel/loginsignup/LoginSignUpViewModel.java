package com.android.gigvid.viewModel.loginsignup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.gigvid.Constants;
import com.android.gigvid.model.repository.networkRepo.loginsignup.LoginSignUpApi;
import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.LogIn;
import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.LoginRespStatus;
import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.LoginResp;
import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.SignUp;
import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.SignUpResp;
import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.SignUpResStatus;
import com.android.gigvid.utils.network.RetrofitUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class LoginSignUpViewModel extends ViewModel {

    public static final String TAG = "LoginSignUpViewModel";
    private MutableLiveData<LoginRespStatus> logInMutableLiveData;

    private MutableLiveData<SignUpResStatus> signUpResStatusMutableLiveData;

    public LiveData<LoginRespStatus> getObservableLoginData() {
        if (logInMutableLiveData == null) {
            logInMutableLiveData = new MutableLiveData<>();
        }
        return logInMutableLiveData;
    }

    private LoginSignUpApi client;

    public void callLoginApi(String username, String pass) {
        LogIn logIn = new LogIn(username, pass);

        if (client == null) {
            client = RetrofitUtils.getInstance().getLoginClient();
        }
        Call<LoginResp> call = client.userLoginAuth(logIn);
        call.enqueue(new Callback<LoginResp>() {
            @Override
            public void onResponse(Call<LoginResp> call, Response<LoginResp> response) {
                if (response.isSuccessful()) {
                    LoginResp token = (LoginResp) response.body();
                    Timber.d("onResponse: res" + response.code() + token.getToken());
                    logInMutableLiveData.setValue(new LoginRespStatus(token.getToken(), Constants.SUCCESS));
                } else {
                    Timber.d("onResponse: fail");
                    logInMutableLiveData.setValue(new LoginRespStatus(null, Constants.FAIL));
                }

            }

            @Override
            public void onFailure(Call<LoginResp> call, Throwable t) {
                Timber.d("onFailure: t %s", t.getMessage());
                logInMutableLiveData.setValue(new LoginRespStatus(null, Constants.FAIL));
            }
        });
    }


    public LiveData<SignUpResStatus> getObservableSignUpData() {
        if (signUpResStatusMutableLiveData == null) {
            signUpResStatusMutableLiveData = new MutableLiveData<>();
        }
        return signUpResStatusMutableLiveData;
    }


    public void callSignUpApi(SignUp signUp) {
        if (client == null) {
            client = RetrofitUtils.getInstance().getLoginClient();
        }
        Call<SignUpResp> call = client.userSignUp(signUp);
        call.enqueue(new Callback<SignUpResp>() {
            @Override
            public void onResponse(Call<SignUpResp> call, Response<SignUpResp> response) {
                if (response.isSuccessful()) {
                    SignUpResp signUpRes = (SignUpResp) response.body();
                    Timber.d("onResponse: res" + response.code() + signUpRes.getUsername());
                    signUpResStatusMutableLiveData.setValue(new SignUpResStatus(signUpRes.getUsername(), signUpRes.getEmail(), Constants.SUCCESS, null));
                } else {
                    Timber.d("onResponse: fail");
                    signUpResStatusMutableLiveData.setValue(new SignUpResStatus(null, null, Constants.FAIL, null));
                }

            }


            @Override
            public void onFailure(Call<SignUpResp> call, Throwable t) {
                Timber.d("onFailure: t %s", t.getMessage());
                signUpResStatusMutableLiveData.setValue(new SignUpResStatus(null, null, Constants.FAIL, null));
            }
        });
    }

}
