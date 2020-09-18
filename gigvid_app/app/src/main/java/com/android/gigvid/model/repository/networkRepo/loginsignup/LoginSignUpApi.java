package com.android.gigvid.model.repository.networkRepo.loginsignup;

import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.SignUpResp;
import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.LoginResp;
import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.LogInReqBody;
import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.SignUpReqBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginSignUpApi {
//    TODO("To be converted to LiveData from Call type")
    @POST("/dev/user/login/")
    Call<LoginResp> userLoginAuth(@Body LogInReqBody logInReqBody);


    @POST("/dev/user/")
    Call<SignUpResp> userSignUp(@Body SignUpReqBody signUp);
}
