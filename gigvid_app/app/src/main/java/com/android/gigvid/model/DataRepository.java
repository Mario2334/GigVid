package com.android.gigvid.model;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.gigvid.Constants;
import com.android.gigvid.model.contract.IManager;
import com.android.gigvid.model.repository.dbRepo.DatabaseManager;
import com.android.gigvid.model.repository.networkRepo.NetworkManager;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.GigListResp;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.GigListRespStatus;
import com.android.gigvid.model.repository.networkRepo.loginsignup.LoginSignUpApi;
import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.LogIn;
import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.LoginResp;
import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.LoginRespStatus;
import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.SignUp;
import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.SignUpResStatus;
import com.android.gigvid.utils.network.NetworkUtils;
import com.android.gigvid.utils.network.RetrofitUtils;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
        mDatabaseManager = DatabaseManager.getInstance();
        mNetworkUtils = NetworkUtils.getInstance(mApplicationContextWeakRef);
    }

    public LiveData<LoginRespStatus> loginToGigVid(String username, String password) {
        if (mNetworkUtils.isConnectedToInternet()) {
            // Handle network data fetching
            Timber.d("Is connected to internet!");
            return mNetworkManager.loginToGigVid(username, password);
        } else {
            Timber.d("Is NOT connected to internet!");
            //            TODO("Implement live data for DB")
            // Handle db data fetching OR handle unable to login state!
            return null;
        }
    }

    public LiveData<SignUpResStatus> signUpForGigVid(SignUp signUpBody) {
        if (mNetworkUtils.isConnectedToInternet()) {
            // Handle network data fetching
            return mNetworkManager.signUpForGigVid(signUpBody);
        } else {
            // Handle db data fetching OR handle unable to sign up state!
            Timber.d("Is NOT connected to internet!");

//            TODO("Implement live data for DB")
            return null;
        }
    }

    public LiveData<GigListRespStatus> getGigList(){
        if(mNetworkUtils.isConnectedToInternet()){
            return mNetworkManager.getGigList();
        } else{
            Timber.d("Is NOT connected to internet!");
            return null;
//            TODO("Implement live data for DB")
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