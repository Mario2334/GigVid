package com.android.gigvid.viewModel.loginsignup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.gigvid.Constants;
import com.android.gigvid.model.repository.networkRepo.loginsignup.LoginSignUpApi;
import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.LogIn;
import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.LoginResp;
import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.Token;
import com.android.gigvid.utils.network.RetrofitUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class LoginSignUpViewModel extends ViewModel {

    public static final String TAG = "LoginSignUpViewModel";
    private MutableLiveData<LoginResp> logInMutableLiveData;

    public LiveData<LoginResp> getObservableLoginData(){
        if(logInMutableLiveData == null){
            logInMutableLiveData = new MutableLiveData<>();
        }
        return logInMutableLiveData;
    }


    public void callLoginApi(String username, String pass){
        LogIn logIn = new LogIn(username,pass);

        Call<Token> call = RetrofitUtils.getInstance().create(LoginSignUpApi.class).userLoginAuth(logIn);
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if(response.isSuccessful()){
                    Token token = (Token)response.body();
                    Timber.d("onResponse: res" + response.code() + token.getToken());
                    logInMutableLiveData.setValue(new LoginResp(token.getToken(), Constants.SUCCESS));
                }else{
                    Timber.d("onResponse: fail");
                    logInMutableLiveData.setValue(new LoginResp(null, Constants.FAIL));
                }

            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Timber.d("onFailure: t %s", t.getMessage());
                logInMutableLiveData.setValue(new LoginResp(null, Constants.FAIL));
            }
        });
    }
}
