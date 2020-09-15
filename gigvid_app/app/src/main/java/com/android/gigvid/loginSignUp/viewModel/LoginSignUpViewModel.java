package com.android.gigvid.loginSignUp.viewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.gigvid.RetrofitClient;
import com.android.gigvid.Utils;
import com.android.gigvid.loginSignUp.LoginSignUpApi;
import com.android.gigvid.loginSignUp.model.LogIn;
import com.android.gigvid.loginSignUp.model.LoginResp;
import com.android.gigvid.loginSignUp.model.Token;

import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class LoginSignUpViewModel extends ViewModel {

    private MutableLiveData<LoginResp> logInMutableLiveData;

    public LiveData<LoginResp> getObservableLoginData(){
        if(logInMutableLiveData == null){
            logInMutableLiveData = new MutableLiveData<>();
        }
        return logInMutableLiveData;
    }


    public void callLoginApi(String mailId, String pass){
        LogIn logIn = new LogIn(mailId,pass);

        Call<Token> call = RetrofitClient.getInstance().create(LoginSignUpApi.class).userLoginAuth(logIn);
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if(response.isSuccessful()){
                    Token token = (Token)response.body();
                    Timber.d("onResponse: res" + response.code() + token.getToken());
                    logInMutableLiveData.setValue(new LoginResp(token.getToken(), Utils.SUCCESS));
                }else{
                    Timber.d("onResponse: fail");
                    logInMutableLiveData.setValue(new LoginResp(null, Utils.FAIL));
                }

            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Log.d("SMP", "onFailure: t "+t.getMessage());
                logInMutableLiveData.setValue(new LoginResp(null, Utils.FAIL));
            }
        });
    }
}
