package com.android.gigvid.utils.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.lang.ref.WeakReference;

/**
 * Created by Kavya P S on 16/09/20.
 */
public class NetworkUtils {
    private static NetworkUtils INSTANCE;

    public static NetworkUtils getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NetworkUtils();
        }
        return INSTANCE;
    }

    public boolean isConnectedToInternet(WeakReference<Context> contextWeakRef) {
        Context context = contextWeakRef.get();
        if (context != null) {
            context = context.getApplicationContext();
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
            return (activeNetwork != null && activeNetwork.isConnected());
        }
        return false;
    }

}
