package com.android.gigvid.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.android.gigvid.R;
import com.android.gigvid.Constants;
import com.android.gigvid.view.loginsignup.UserAuthActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Handler uiHandler = new Handler();
        uiHandler.postDelayed(uiLoading, 5000);
    }

    private Runnable uiLoading = new Runnable() {
        @Override
        public void run() {
            if(isUserAlreadyLoggedIn()){
                //TODO launch home activity
            } else{
                //TODO launch login page
                Intent loginActivity = new Intent(getApplicationContext(), UserAuthActivity.class);
                startActivity(loginActivity);
            }
            finish();
        }
    };

    private boolean isUserAlreadyLoggedIn(){
        SharedPreferences loginSP = getSharedPreferences(Constants.LOGIN_TOKEN_SP, MODE_PRIVATE);
        return loginSP.getString(Constants.LOGIN_TOKEN_KEY, null) != null;
    }
}