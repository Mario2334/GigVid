package com.android.gigvid.loginSignUp;

import com.android.gigvid.loginSignUp.model.Token;
import com.android.gigvid.loginSignUp.model.LogIn;
import com.android.gigvid.loginSignUp.model.SignUp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginSignUpApi {
    @POST("/user/login")
    Call<Token> userLoginAuth(@Body LogIn logIn);


    @POST("/user/create_user")
    Call<SignUp> userSignUp(@Body SignUp signUp);
}
