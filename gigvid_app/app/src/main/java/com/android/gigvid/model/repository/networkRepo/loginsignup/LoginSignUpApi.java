package com.android.gigvid.model.repository.networkRepo.loginsignup;

import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.SignUpResp;
import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.LoginResp;
import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.LogIn;
import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.SignUp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginSignUpApi {
//    TODO("To be converted to LiveData from Call type")
    @POST("/dev/user/login/")
    Call<LoginResp> userLoginAuth(@Body LogIn logIn);


    @POST("/dev/user/")
    Call<SignUpResp> userSignUp(@Body SignUp signUp);
}
