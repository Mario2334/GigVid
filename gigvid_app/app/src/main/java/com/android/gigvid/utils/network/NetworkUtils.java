package com.android.gigvid.utils.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.lang.ref.WeakReference;

/**
 * Purpose:
 * Network related Utility class:
 *
 * Created by Kavya P S on 16/09/20.
 */
public class NetworkUtils {
    private static NetworkUtils INSTANCE;
    private ConnectivityManager mConnectivityManager;

    public static NetworkUtils getInstance(WeakReference<Context> contextWeakRef) {
        if (INSTANCE == null) {
            INSTANCE = new NetworkUtils(contextWeakRef);
        }
        return INSTANCE;
    }

    private NetworkUtils(WeakReference<Context> contextWeakRef) {
        Context ctx = contextWeakRef.get();
        if (ctx != null) {
            mConnectivityManager = (ConnectivityManager) ctx.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        }
    }

    /**
     * Method: Check internet connectivity
     *
     * @return boolean: Returns true if connected to internet
     */
    public boolean isConnectedToInternet() {
        if (mConnectivityManager != null) {
            NetworkInfo activeNetwork = mConnectivityManager.getActiveNetworkInfo();
            return (activeNetwork != null && activeNetwork.isConnected());
        }
        return false;
    }

    public void clear() {
        mConnectivityManager = null;
        INSTANCE = null;
    }
}
