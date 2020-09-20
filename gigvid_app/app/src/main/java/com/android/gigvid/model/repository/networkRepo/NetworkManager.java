package com.android.gigvid.model.repository.networkRepo;


import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.gigvid.Constants;
import com.android.gigvid.model.contract.IManager;
import com.android.gigvid.model.repository.networkRepo.homeScreen.HomeScreenApi;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.BuyGigReqBody;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.BuyGigResp;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.CreateGigReqBody;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.CreateGigResp;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.GigListResp;
import com.android.gigvid.model.repository.networkRepo.loginsignup.LoginSignUpApi;
import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.LogInReqBody;
import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.LoginResp;
import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.SignUpReqBody;
import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.SignUpResp;
import com.android.gigvid.model.repository.reponseData.DataResponse;
import com.android.gigvid.model.repository.reponseData.ErrorData;
import com.android.gigvid.model.repository.reponseData.ListResponse;
import com.android.gigvid.model.repository.reponseData.StateDefinition;
import com.android.gigvid.utils.network.RetrofitUtils;
import com.android.gigvid.utils.sharedPref.SharedPrefUtils;

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

    private MutableLiveData<DataResponse<LoginResp>> mLogInMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<DataResponse<SignUpResp>> mSignUpResStatusMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<ListResponse<GigListResp>> gigListRespStatusMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<DataResponse<CreateGigResp>> createGigRespStatusMutableLiveData = new MutableLiveData<>();

    private MutableLiveData<DataResponse<BuyGigResp>> mBuyGugMutableLiveData = new MutableLiveData<>();

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

    public LiveData<DataResponse<LoginResp>> loginToGigVid(String username, String password) {
        LogInReqBody logInReqBody = new LogInReqBody(username, password);

        if (client == null) {
//            May not need to login on subsequent app usage,
//            hence `client` it is not initialized in the constructor.
            client = RetrofitUtils.getInstance().getLoginClient();
        }

//        TODO("Use Transformations for live data ")
        Call<LoginResp> call = client.userLoginAuth(logInReqBody);
        call.enqueue(new Callback<LoginResp>() {
            @Override
            public void onResponse(Call<LoginResp> call, Response<LoginResp> response) {
                DataResponse<LoginResp> loginResponseStatus;
                if (response.isSuccessful()) {

                    LoginResp loginResp = (LoginResp) response.body();
                    if (loginResp != null) {
                        Timber.d("onResponse: res" + response.code() + loginResp.getToken());
                        loginResponseStatus = new DataResponse<>(
                                StateDefinition.State.COMPLETED,
                                loginResp,
                                null);
                    } else {
                        ErrorData error = new ErrorData(
                                StateDefinition.ErrorState.INTERNAL_SERVER_ERROR,
                                response.message());

                        loginResponseStatus = new DataResponse<>(
                                StateDefinition.State.ERROR,
                                null,
                                error
                        );
                    }

                } else {
                    ErrorData error = new ErrorData(
                            StateDefinition.ErrorState.INTERNAL_SERVER_ERROR,
                            response.message());

                    loginResponseStatus = new DataResponse<>(
                            StateDefinition.State.ERROR,
                            null,
                            error
                    );

                }
                if (Thread.currentThread().equals(Looper.getMainLooper().getThread())) {
                    mLogInMutableLiveData.setValue(loginResponseStatus);
                } else {
                    mLogInMutableLiveData.postValue(loginResponseStatus);
                }
            }

            @Override
            public void onFailure(Call<LoginResp> call, Throwable t) {
                DataResponse<LoginResp> loginResponseStatus;
                ErrorData error = new ErrorData(
                        StateDefinition.ErrorState.INTERNAL_SERVER_ERROR,
                        t.getMessage());

                loginResponseStatus = new DataResponse<>(
                        StateDefinition.State.ERROR,
                        null,
                        error
                );
                Timber.d("onFailure: t %s", t.getMessage());
                if (Thread.currentThread().equals(Looper.getMainLooper().getThread())) {
                    mLogInMutableLiveData.setValue(loginResponseStatus);
                } else {
                    mLogInMutableLiveData.postValue(loginResponseStatus);
                }
            }
        });

        return mLogInMutableLiveData;
    }

    public LiveData<DataResponse<SignUpResp>> signUpForGigVid(SignUpReqBody signUpBody) {
        if (client == null) {
            client = RetrofitUtils.getInstance().getLoginClient();
        }
        //        TODO("Use Transformations for live data ")

        Call<SignUpResp> call = client.userSignUp(signUpBody);
        call.enqueue(new Callback<SignUpResp>() {
            @Override
            public void onResponse(Call<SignUpResp> call, Response<SignUpResp> response) {
                DataResponse<SignUpResp> signUpResponseStatus;
                if (response.isSuccessful()) {
                    SignUpResp signUpRes = (SignUpResp) response.body();

                    if (signUpRes != null) {
                        Timber.d("onResponse: res" + response.code() + signUpRes.getUsername());
                        signUpResponseStatus = new DataResponse<SignUpResp>(StateDefinition.State.COMPLETED, signUpRes, null);
                    } else {
                        ErrorData error = new ErrorData(
                                StateDefinition.ErrorState.INTERNAL_SERVER_ERROR,
                                response.message());

                        signUpResponseStatus = new DataResponse<>(
                                StateDefinition.State.ERROR,
                                null,
                                error
                        );
                    }

                } else {
                    Timber.d("onResponse: fail");
                    ErrorData error = new ErrorData(
                            StateDefinition.ErrorState.INTERNAL_SERVER_ERROR,
                            response.message());

                    signUpResponseStatus = new DataResponse<>(
                            StateDefinition.State.ERROR,
                            null,
                            error
                    );
                }
                if (Thread.currentThread().equals(Looper.getMainLooper().getThread())) {
                    mSignUpResStatusMutableLiveData.setValue(signUpResponseStatus);
                } else {
                    mSignUpResStatusMutableLiveData.postValue(signUpResponseStatus);
                }

            }


            @Override
            public void onFailure(Call<SignUpResp> call, Throwable t) {
                DataResponse<SignUpResp> signUpResponseStatus;
                ErrorData error = new ErrorData(
                        StateDefinition.ErrorState.INTERNAL_SERVER_ERROR,
                        t.getMessage());

                signUpResponseStatus = new DataResponse<>(
                        StateDefinition.State.ERROR,
                        null,
                        error
                );
                Timber.d("onFailure: t %s", t.getMessage());
                if (Thread.currentThread().equals(Looper.getMainLooper().getThread())) {
                    mSignUpResStatusMutableLiveData.setValue(signUpResponseStatus);
                } else {
                    mSignUpResStatusMutableLiveData.postValue(signUpResponseStatus);
                }
            }
        });
        return mSignUpResStatusMutableLiveData;
    }


    public LiveData<ListResponse<GigListResp>> getGigList() {
        if (homeScreenApiClient == null) {
            homeScreenApiClient = RetrofitUtils.getInstance().getHomeScreenApiClient();
        }

        Call<List<GigListResp>> callGigList = homeScreenApiClient.getGigList();
        callGigList.enqueue(new Callback<List<GigListResp>>() {
            @Override
            public void onResponse(Call<List<GigListResp>> call, Response<List<GigListResp>> response) {



                ListResponse<GigListResp> gigListRespStatus;
                if (response.isSuccessful()) {

                    List<GigListResp> gigList = response.body();
                    if (gigList != null) {
                        gigListRespStatus = new ListResponse<>(StateDefinition.State.COMPLETED, gigList, null);
                    } else {
                        ErrorData error = new ErrorData(
                                StateDefinition.ErrorState.INTERNAL_SERVER_ERROR,
                                response.message());

                        gigListRespStatus = new ListResponse<>(
                                StateDefinition.State.ERROR,
                                null,
                                error
                        );
                    }

                } else {
                    Timber.d("onResponse: fail");
                    ErrorData error = new ErrorData(
                            StateDefinition.ErrorState.INTERNAL_SERVER_ERROR,
                            response.message());

                    gigListRespStatus = new ListResponse<>(
                            StateDefinition.State.ERROR,
                            null,
                            error
                    );
                }
                if (Thread.currentThread().equals(Looper.getMainLooper().getThread())) {
                    gigListRespStatusMutableLiveData.setValue(gigListRespStatus);
                } else {
                    gigListRespStatusMutableLiveData.postValue(gigListRespStatus);
                }


            }

            @Override
            public void onFailure(Call<List<GigListResp>> call, Throwable t) {

                ListResponse<GigListResp> signUpResponseStatus;
                ErrorData error = new ErrorData(
                        StateDefinition.ErrorState.INTERNAL_SERVER_ERROR,
                        t.getMessage());

                signUpResponseStatus = new ListResponse<>(
                        StateDefinition.State.ERROR,
                        null,
                        error
                );
                Timber.d("onFailure: t %s", t.getMessage());
                if (Thread.currentThread().equals(Looper.getMainLooper().getThread())) {
                    gigListRespStatusMutableLiveData.setValue(signUpResponseStatus);
                } else {
                    gigListRespStatusMutableLiveData.postValue(signUpResponseStatus);
                }
            }
        });

        return gigListRespStatusMutableLiveData;
    }

    public LiveData<DataResponse<CreateGigResp>> createGig(CreateGigReqBody createGig) {
        if (homeScreenApiClient == null) {
            homeScreenApiClient = RetrofitUtils.getInstance().getHomeScreenApiClient();
        }

        String authToken = "Token " + SharedPrefUtils.getAuthToken();


        Call<CreateGigResp> createGigCall = homeScreenApiClient.createGig(authToken, createGig);

        createGigCall.enqueue(new Callback<CreateGigResp>() {
            @Override
            public void onResponse(Call<CreateGigResp> call, Response<CreateGigResp> response) {

                DataResponse<CreateGigResp> createGigRespDataResponse;
                if (response.isSuccessful()) {
                    CreateGigResp createGigResp = (CreateGigResp) response.body();

                    if (createGigResp != null) {
                        Timber.d("onResponse: res" + response.code() + createGigResp.getMessage());
                        createGigRespDataResponse = new DataResponse<CreateGigResp>(StateDefinition.State.COMPLETED, createGigResp, null);
                    } else {
                        ErrorData error = new ErrorData(
                                StateDefinition.ErrorState.INTERNAL_SERVER_ERROR,
                                response.message());

                        createGigRespDataResponse = new DataResponse<>(
                                StateDefinition.State.ERROR,
                                null,
                                error
                        );
                    }

                } else {
                    Timber.d("onResponse: fail");
                    ErrorData error = new ErrorData(
                            StateDefinition.ErrorState.INTERNAL_SERVER_ERROR,
                            response.message());

                    createGigRespDataResponse = new DataResponse<>(
                            StateDefinition.State.ERROR,
                            null,
                            error
                    );
                }
                if (Thread.currentThread().equals(Looper.getMainLooper().getThread())) {
                    createGigRespStatusMutableLiveData.setValue(createGigRespDataResponse);
                } else {
                    createGigRespStatusMutableLiveData.postValue(createGigRespDataResponse);
                }
            }

            @Override
            public void onFailure(Call<CreateGigResp> call, Throwable t) {

                DataResponse<CreateGigResp> createGigRespDataResponse;
                ErrorData error = new ErrorData(
                        StateDefinition.ErrorState.INTERNAL_SERVER_ERROR,
                        t.getMessage());

                createGigRespDataResponse = new DataResponse<>(
                        StateDefinition.State.ERROR,
                        null,
                        error
                );
                Timber.d("onFailure: t %s", t.getMessage());
                if (Thread.currentThread().equals(Looper.getMainLooper().getThread())) {
                    createGigRespStatusMutableLiveData.setValue(createGigRespDataResponse);
                } else {
                    createGigRespStatusMutableLiveData.postValue(createGigRespDataResponse);
                }
            }
        });

        return createGigRespStatusMutableLiveData;
    }




    public LiveData<DataResponse<BuyGigResp>> buyGigApiCall(BuyGigReqBody buyGigReqBody) {
        if (homeScreenApiClient == null) {
            homeScreenApiClient = RetrofitUtils.getInstance().getHomeScreenApiClient();
        }
        //        TODO("Use Transformations for live data ")
        String authToken = "Token " + SharedPrefUtils.getAuthToken();
        Call<BuyGigResp> call = homeScreenApiClient.buyGig(authToken, buyGigReqBody);
        call.enqueue(new Callback<BuyGigResp>() {
            @Override
            public void onResponse(Call<BuyGigResp> call, Response<BuyGigResp> response) {
                DataResponse<BuyGigResp> buyGigRespDataResponse;
                if (response.isSuccessful()) {
                    BuyGigResp signUpRes = (BuyGigResp) response.body();

                    if (signUpRes != null) {
                        Timber.d("onResponse: res" + response.code() + signUpRes.getOrderId());
                        buyGigRespDataResponse = new DataResponse<BuyGigResp>(StateDefinition.State.COMPLETED, signUpRes, null);
                    } else {
                        ErrorData error = new ErrorData(
                                StateDefinition.ErrorState.INTERNAL_SERVER_ERROR,
                                response.message());

                        buyGigRespDataResponse = new DataResponse<>(
                                StateDefinition.State.ERROR,
                                null,
                                error
                        );
                    }

                } else {
                    Timber.d("onResponse: fail");
                    ErrorData error = new ErrorData(
                            StateDefinition.ErrorState.INTERNAL_SERVER_ERROR,
                            response.message());

                    buyGigRespDataResponse = new DataResponse<>(
                            StateDefinition.State.ERROR,
                            null,
                            error
                    );
                }
                if (Thread.currentThread().equals(Looper.getMainLooper().getThread())) {
                    mBuyGugMutableLiveData.setValue(buyGigRespDataResponse);
                } else {
                    mBuyGugMutableLiveData.postValue(buyGigRespDataResponse);
                }

            }


            @Override
            public void onFailure(Call<BuyGigResp> call, Throwable t) {
                DataResponse<BuyGigResp> buyGigRespDataResponse;
                ErrorData error = new ErrorData(
                        StateDefinition.ErrorState.INTERNAL_SERVER_ERROR,
                        t.getMessage());

                buyGigRespDataResponse = new DataResponse<>(
                        StateDefinition.State.ERROR,
                        null,
                        error
                );
                Timber.d("onFailure: t %s", t.getMessage());
                if (Thread.currentThread().equals(Looper.getMainLooper().getThread())) {
                    mBuyGugMutableLiveData.setValue(buyGigRespDataResponse);
                } else {
                    mBuyGugMutableLiveData.postValue(buyGigRespDataResponse);
                }
            }
        });
        return mBuyGugMutableLiveData;
    }


    @Override
    public void clear() {

    }
}
