package com.android.gigvid.utils.network;


import com.android.gigvid.model.repository.networkRepo.loginsignup.LoginSignUpApi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Network Utility Manager:
 * Obtain Retrofit object to make API calls.
 *
 * Created by Kavya P S on 16/09/20.
 */
public class RetrofitUtils {

    private static RetrofitUtils INSTANCE;

    private static Retrofit mRetrofit;

    private static final String BASE_URL = "https://31yqq2xqt7.execute-api.ap-south-1.amazonaws.com";

    public static RetrofitUtils getInstance(){
        if(INSTANCE == null){
            INSTANCE = new RetrofitUtils();
        }
        return INSTANCE;
    }


    private static Retrofit getRetrofit(){
        if(mRetrofit == null){
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            OkHttpClient.Builder mHttpClientBuilder = new OkHttpClient.Builder();

            loggingInterceptor.setLevel( HttpLoggingInterceptor.Level.BODY);
            mHttpClientBuilder.addInterceptor(loggingInterceptor);

            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(mHttpClientBuilder.build())
                    .build();
        }
        return mRetrofit;
    }

    public LoginSignUpApi getLoginClient() {
        return getRetrofit().create(LoginSignUpApi.class);
    }

}



