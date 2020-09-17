package com.android.gigvid.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.android.gigvid.R;
import com.android.gigvid.utils.sharedPref.SharedPrefUtils;
import com.android.gigvid.view.homescreen.HomeScreenActivity;
import com.android.gigvid.view.loginsignup.UserAuthActivity;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Handler uiHandler = new Handler();
        uiHandler.postDelayed(uiLoading, 5000);

        //uncomment to logout if already sign up is successfull
//        SharedPrefUtils.saveTokenValueToSP(null);
    }

    private Runnable uiLoading = new Runnable() {
        @Override
        public void run() {
            if(SharedPrefUtils.isUserAlreadyLoggedIn()){
                //TODO launch home activity
                Timber.d("SMP launch home screen astoken available");
              launchHomeScreenActivity();

            } else{
                //TODO launch login page
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