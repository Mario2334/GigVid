package com.android.gigvid.utils.sharedPref;

import android.content.SharedPreferences;

import com.android.gigvid.Constants;
import com.android.gigvid.GigVidApplication;

import static android.content.Context.MODE_PRIVATE;

public class SharedPrefUtils {

    public static void saveTokenValueToSP(String token){
        SharedPreferences loginSP = GigVidApplication.getGigVidAppContext().getSharedPreferences(Constants.LOGIN_TOKEN_SP, MODE_PRIVATE);
        SharedPreferences.Editor editor = loginSP.edit();
        editor.putString(Constants.LOGIN_TOKEN_KEY, token);
        editor.apply();
    }

    public static boolean isUserAlreadyLoggedIn(){
        SharedPreferences loginSP = GigVidApplication.getGigVidAppContext().getSharedPreferences(Constants.LOGIN_TOKEN_SP, MODE_PRIVATE);
        return loginSP.getString(Constants.LOGIN_TOKEN_KEY, null) != null;
    }
}
