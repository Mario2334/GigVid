package com.android.gigvid.model.repository.networkRepo;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.gigvid.Constants;
import com.android.gigvid.model.contract.IManager;
import com.android.gigvid.model.repository.networkRepo.homeScreen.HomeScreenApi;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.GigListResp;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.GigListRespStatus;
import com.android.gigvid.model.repository.networkRepo.loginsignup.LoginSignUpApi;
import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.LogIn;
import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.LoginResp;
import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.LoginRespStatus;
import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.SignUp;
import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.SignUpResStatus;
import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.SignUpResp;
import com.android.gigvid.utils.network.RetrofitUtils;

import java.net.HttpCookie;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by Kavya P S on 18/09/20.
 */
public class NetworkManager implements IManager {
    private static NetworkManager INSTANCE;

    private LoginSignUpApi client;
    private HomeScreenApi homeScreenApiClient;

    private MutableLiveData<LoginRespStatus> mLogInMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<SignUpResStatus> mSignUpResStatusMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<GigListRespStatus> gigListRespStatusMutableLiveData = new MutableLiveData<>();

    public static NetworkManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NetworkManager();
        }
        return INSTANCE;
    }

    public NetworkManager() {
        init();
    }

    @Override
    public void init() {
        // Define constructors here
    }

    public LiveData<LoginRespStatus> loginToGigVid(String username, String password) {
        LogIn logIn = new LogIn(username, password);

        if (client == null) {
//            May not need to login on subsequent app usage,
//            hence `client` it is not initialized in the constructor.
            client = RetrofitUtils.getInstance().getLoginClient();
        }

//        TODO("Use Transformations for live data ")
        Call<LoginResp> call = client.userLoginAuth(logIn);
        call.enqueue(new Callback<LoginResp>() {
            @Override
            public void onResponse(Call<LoginResp> call, Response<LoginResp> response) {
                if (response.isSuccessful()) {
                    LoginResp token = (LoginResp) response.body();
                    Timber.d("onResponse: res" + response.code() + token.getToken());
                    mLogInMutableLiveData.setValue(new LoginRespStatus(token.getToken(), Constants.SUCCESS));
                } else {
                    Timber.d("onResponse: fail");
                    mLogInMutableLiveData.setValue(new LoginRespStatus(null, Constants.FAIL));
                }

            }

            @Override
            public void onFailure(Call<LoginResp> call, Throwable t) {
                Timber.d("onFailure: t %s", t.getMessage());
                mLogInMutableLiveData.setValue(new LoginRespStatus(null, Constants.FAIL));
            }
        });

        return mLogInMutableLiveData;
    }

    public LiveData<SignUpResStatus> signUpForGigVid(SignUp signUpBody) {
        if (client == null) {
            client = RetrofitUtils.getInstance().getLoginClient();
        }
        //        TODO("Use Transformations for live data ")

        Call<SignUpResp> call = client.userSignUp(signUpBody);
        call.enqueue(new Callback<SignUpResp>() {
            @Override
            public void onResponse(Call<SignUpResp> call, Response<SignUpResp> response) {
                if (response.isSuccessful()) {
                    SignUpResp signUpRes = (SignUpResp) response.body();
                    Timber.d("onResponse: res" + response.code() + signUpRes.getUsername());
                    mSignUpResStatusMutableLiveData.setValue(new SignUpResStatus(signUpRes.getUsername(), signUpRes.getEmail(), Constants.SUCCESS, null));
                } else {
                    Timber.d("onResponse: fail");
                    mSignUpResStatusMutableLiveData.setValue(new SignUpResStatus(null, null, Constants.FAIL, null));
                }

            }


            @Override
            public void onFailure(Call<SignUpResp> call, Throwable t) {
                Timber.d("onFailure: t %s", t.getMessage());
                mSignUpResStatusMutableLiveData.setValue(new SignUpResStatus(null, null, Constants.FAIL, null));
            }
        });
        return mSignUpResStatusMutableLiveData;
    }


    public LiveData<GigListRespStatus> getGigList(){
        if(homeScreenApiClient == null){
            homeScreenApiClient = RetrofitUtils.getInstance().getHomeScreenApiClient();
        }

        Call<List<GigListResp>> callGigList = homeScreenApiClient.getGigList();
        callGigList.enqueue(new Callback<List<GigListResp>>() {
            @Override
            public void onResponse(Call<List<GigListResp>> call, Response<List<GigListResp>> response) {
                if(response.isSuccessful()){
                    GigListRespStatus gigListRespStatus = new GigListRespStatus(response.body(), Constants.SUCCESS,null);
                    gigListRespStatusMutableLiveData.setValue(gigListRespStatus);
                } else{
                    GigListRespStatus gigListRespStatus = new GigListRespStatus(null, Constants.FAIL,null);
                    gigListRespStatusMutableLiveData.setValue(gigListRespStatus);
                }
            }

            @Override
            public void onFailure(Call<List<GigListResp>> call, Throwable t) {
                GigListRespStatus gigListRespStatus = new GigListRespStatus(null, Constants.FAIL,null);
                gigListRespStatusMutableLiveData.setValue(gigListRespStatus);
            }
        });

        return gigListRespStatusMutableLiveData;
    }
    @Override
    public void clear() {

    }
}
