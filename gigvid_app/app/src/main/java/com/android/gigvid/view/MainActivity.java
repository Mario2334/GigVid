package com.android.gigvid.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.android.gigvid.R;
import com.android.gigvid.utils.sharedPref.SharedPrefUtils;
import com.android.gigvid.view.homescreen.activity.HomeScreenActivity;
import com.android.gigvid.view.loginsignup.UserAuthActivity;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Handler uiHandler = new Handler();
        uiHandler.postDelayed(uiLoading, 3000);

        //uncomment to logout if already sign up is successful
//        SharedPrefUtils.saveTokenValueToSP(null);
    }

    private Runnable uiLoading = new Runnable() {
        @Override
        public void run() {
            if (SharedPrefUtils.isUserAlreadyLoggedIn()) {
                Timber.d("SMP launch home screen as token available");
                //To save latest token value
//                SharedPrefUtils.saveTokenValueToSP("f6b91256f463149b982de6e658718506ae134e5f");
                launchHomeScreenActivity();

            } else {
                Intent loginActivity = new Intent(getApplicationContext(), UserAuthActivity.class);
                startActivity(loginActivity);
            }
            finish();
        }
    };

    private void launchHomeScreenActivity() {
        Intent homeScreenIntent = new Intent(this, HomeScreenActivity.class);
        startActivity(homeScreenIntent);

    }
}