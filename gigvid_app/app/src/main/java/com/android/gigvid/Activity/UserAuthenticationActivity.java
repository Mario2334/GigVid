package com.android.gigvid.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.android.gigvid.Fragment.LoginFragment;
import com.android.gigvid.R;

public class UserAuthenticationActivity extends AppCompatActivity {
    private int fragmentContainer;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_authentication);

        initializeUI();
        loadLoginFragment();
    }

    /**
     * Method: Initialize UI elements
     */
    private void initializeUI() {
        fragmentContainer = R.id.user_authentication_fragment_container;
        fragmentManager = getSupportFragmentManager();
    }

    /**
     * Method: Load Login fragment:
     */
    private void loadLoginFragment() {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(fragmentContainer, LoginFragment.newInstance());
        transaction.commit();
    }

    /**
     * Method: Load SignUp fragment
     */
    private void loadSignUpFragment() {

    }

}