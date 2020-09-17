package com.android.gigvid.view.loginsignup;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.android.gigvid.R;
import com.android.gigvid.view.loginsignup.fragment.LoginFragment;
import com.android.gigvid.view.loginsignup.fragment.SignUpFragment;

public class UserAuthActivity extends AppCompatActivity
        implements UserAuthFragmentCommunicator {

    public static final String TAG = "UserAuthActivity";

    private int fragmentContainer;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private LoginFragment loginFragment = LoginFragment.newInstance();
    private SignUpFragment signUpFragment = SignUpFragment.newInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_authentication);

        initializeUI();
        loadFragment(loginFragment);
    }

    /**
     * Method: Initialize UI elements
     */
    private void initializeUI() {
        fragmentContainer = R.id.user_authentication_fragment_container;
        fragmentManager = getSupportFragmentManager();
    }


    private void loadFragment(Fragment frag){
        transaction = fragmentManager.beginTransaction();
        transaction.replace(fragmentContainer, frag, frag.getTag());
        transaction.commit();
    }

    /**
     * Overridden methods of UserAuthenticationFragmentCommunicator Interface:
     * 1. launchLoginFragment() -> Will Launch Login Fragment via activity on Click of
     *                             'Login' button from  SignUpFragment
     * 2. launchSignUpFragment() -> Will Launch SignUp Fragment via activity on Click of
     *                             'Sign Up' button from LoginFragment
     */
    @Override
    public void launchLoginFragment() {
        loadFragment(loginFragment);
    }

    @Override
    public void launchSignUpFragment() {
        loadFragment(signUpFragment);
    }
}