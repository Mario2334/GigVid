package com.android.gigvid.model;

import android.content.Context;
import android.os.Looper;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.android.gigvid.model.contract.IManager;
import com.android.gigvid.model.repository.dbRepo.DatabaseManager;
import com.android.gigvid.model.repository.networkRepo.NetworkManager;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.buygig.BuyGigReqBody;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.buygig.BuyGigResp;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.creategig.CreateGigReqBody;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.creategig.CreateGigResp;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.GigListResp;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.ticketlist.TicketResp;
import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.LoginResp;
import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.SignUpReqBody;
import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.SignUpResp;
import com.android.gigvid.model.repository.reponseData.DataResponse;
import com.android.gigvid.model.repository.reponseData.ErrorData;
import com.android.gigvid.model.repository.reponseData.ListResponse;
import com.android.gigvid.model.repository.reponseData.StateDefinition;
import com.android.gigvid.utils.network.NetworkUtils;

import java.lang.ref.WeakReference;
import java.util.List;

import timber.log.Timber;

/**
 * Contains methods to be called by ViewModel
 * This in turn collects data from dbRepo, networkRepo accordingly.
 * <p>
 * Also, In-RAM caching is maintained here.
 * <p>
 * <p>
 * Created by Kavya P S on 16/09/20.
 */
public class DataRepository implements IManager {
    private static DataRepository INSTANCE = null;

    private static NetworkManager mNetworkManager = null;
    private static DatabaseManager mDatabaseManager = null;

    private static NetworkUtils mNetworkUtils = null;

    private WeakReference<Context> mApplicationContextWeakRef;

    ErrorData mNoInternetError = new ErrorData(StateDefinition.ErrorState.NO_INTERNET_ERROR,
            "Cannot connect to internet at the moment.");

    public static DataRepository getInstance(WeakReference<Context> applicationCtxWeakRef) {
        if (INSTANCE == null) {
            INSTANCE = new DataRepository(applicationCtxWeakRef);
        }
        return INSTANCE;
    }

    private DataRepository(WeakReference<Context> applicationCtxWeakRef) {
        mApplicationContextWeakRef = applicationCtxWeakRef;

        init();
    }

    /***
     * Define constructors here
     */
    @Override
    public void init() {
        mNetworkManager = NetworkManager.getInstance();
        mDatabaseManager = DatabaseManager.getInstance(mApplicationContextWeakRef);
        mNetworkUtils = NetworkUtils.getInstance(mApplicationContextWeakRef);
    }

    public LiveData<DataResponse<LoginResp>> loginToGigVid(String username, String password) {

        if (mNetworkUtils.isConnectedToInternet()) {
            // Handle network data fetching
            Timber.d("Is connected to internet!");
            LiveData<DataResponse<LoginResp>> source = mNetworkManager.loginToGigVid(username, password);
            return source;
        } else {
            Timber.d("Is NOT connected to internet!");
            DataResponse<LoginResp> loginResp = new DataResponse<>(StateDefinition.State.ERROR, null, mNoInternetError);
            MutableLiveData<DataResponse<LoginResp>> loginLiveData = new MutableLiveData<>();

            if (Thread.currentThread().equals(Looper.getMainLooper().getThread())) {
                loginLiveData.setValue(loginResp);
            } else {
                loginLiveData.postValue(loginResp);
            }
            return loginLiveData;
        }

    }

    public LiveData<DataResponse<SignUpResp>> signUpForGigVid(SignUpReqBody signUpBody) {
        if (mNetworkUtils.isConnectedToInternet()) {
            // Handle network data fetching
            return mNetworkManager.signUpForGigVid(signUpBody);
        } else {
            Timber.d("Is NOT connected to internet!");
            MutableLiveData<DataResponse<SignUpResp>> signUpLiveData = new MutableLiveData<>();
            DataResponse<SignUpResp> signUpResp = new DataResponse<>(StateDefinition.State.ERROR, null, mNoInternetError);

            if (Thread.currentThread().equals(Looper.getMainLooper().getThread())) {
                signUpLiveData.setValue(signUpResp);
            } else {
                signUpLiveData.postValue(signUpResp);
            }
            return signUpLiveData;
        }
    }

    public LiveData<ListResponse<GigListResp>> getGigList() {
        if (mNetworkUtils.isConnectedToInternet()) {
            Timber.d("SMP Data Repo fetch gig list");
            return mNetworkManager.getGigList();
        } else {
            Timber.d("Is NOT connected to internet!");
            return Transformations.switchMap(mDatabaseManager.getGigsList(), new Function<List<GigListResp>, LiveData<ListResponse<GigListResp>>>() {

                @Override
                public LiveData<ListResponse<GigListResp>> apply(List<GigListResp> input) {
                    MutableLiveData<ListResponse<GigListResp>> gigListLiveData = new MutableLiveData<>();

                    ListResponse<GigListResp> liveDataResp = new ListResponse<>(StateDefinition.State.COMPLETED, input, null);

                    if (Thread.currentThread().equals(Looper.getMainLooper().getThread())) {
                        gigListLiveData.setValue(liveDataResp);
                    } else {
                        gigListLiveData.postValue(liveDataResp);
                    }
                    return gigListLiveData;
                }

            });
        }
    }


    public LiveData<DataResponse<CreateGigResp>> createGig(CreateGigReqBody createGig) {
        if (mNetworkUtils.isConnectedToInternet()) {
            return mNetworkManager.createGig(createGig);
        } else {
            Timber.d("Is NOT connected to internet!");
            DataResponse<CreateGigResp> createGigResp = new DataResponse<>(StateDefinition.State.ERROR, null, mNoInternetError);
            MutableLiveData<DataResponse<CreateGigResp>> createGigLiveData = new MutableLiveData<>();

            if (Thread.currentThread().equals(Looper.getMainLooper().getThread())) {
                createGigLiveData.setValue(createGigResp);
            } else {
                createGigLiveData.postValue(createGigResp);
            }
            return createGigLiveData;
        }
    }

    //    Need clarification on error scenario. KPS
    public LiveData<DataResponse<BuyGigResp>> callBuyGigApi(BuyGigReqBody buyGigReqBody) {
        if (mNetworkUtils.isConnectedToInternet()) {
            // Handle network data fetching
            return mNetworkManager.buyGigApiCall(buyGigReqBody);
        } else {
            Timber.d("Is NOT connected to internet!");
            DataResponse<BuyGigResp> buyGigResp = new DataResponse<>(StateDefinition.State.ERROR, null, mNoInternetError);
            MutableLiveData<DataResponse<BuyGigResp>> buyGigLiveData = new MutableLiveData<>();

            if (Thread.currentThread().equals(Looper.getMainLooper().getThread())) {
                buyGigLiveData.setValue(buyGigResp);
            } else {
                buyGigLiveData.postValue(buyGigResp);
            }
            return buyGigLiveData;
        }
    }

    public LiveData<ListResponse<TicketResp>> getTicketsApiCall() {
        if (mNetworkUtils.isConnectedToInternet()) {
            // Handle network data fetching
            return mNetworkManager.getTicketsApiCall();
        } else {
            Timber.d("Is NOT connected to internet!");
            ListResponse<TicketResp> ticketListResp = new ListResponse<>(StateDefinition.State.ERROR, null, mNoInternetError);
            MutableLiveData<ListResponse<TicketResp>> ticketListLiveData = new MutableLiveData<>();

            if (Thread.currentThread().equals(Looper.getMainLooper().getThread())) {
                ticketListLiveData.setValue(ticketListResp);
            } else {
                ticketListLiveData.postValue(ticketListResp);
            }
            return ticketListLiveData;
        }
    }


    /***
     * Clear memory here
     */
    @Override
    public void clear() {
        mNetworkManager = null;
        mDatabaseManager = null;
    }

}