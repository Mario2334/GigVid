package com.android.gigvid.model.repository.networkRepo.loginsignup;

import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.Token;
import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.LogIn;
import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.SignUp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginSignUpApi {
    @POST("/dev/user/login/")
    Call<Token> userLoginAuth(@Body LogIn logIn);


    @POST("/dev/user/create_user/")
    Call<Void> userSignUp(@Body SignUp signUp);
}
