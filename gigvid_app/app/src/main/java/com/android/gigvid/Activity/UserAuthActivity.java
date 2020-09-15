package com.android.gigvid.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.android.gigvid.Fragment.LoginFragment;
import com.android.gigvid.Fragment.SignUpFragment;
import com.android.gigvid.R;
import com.android.gigvid.UserAuthFragmentCommunicator;

import timber.log.Timber;

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
        Timber.tag(TAG).e("loadLoginFragment() called");
        transaction = fragmentManager.beginTransaction();
        transaction.replace(fragmentContainer, loginFragment, loginFragment.getTag());
        transaction.commit();
    }

    /**
     * Method: Load SignUp fragment
     * TODO: ADD TAG
     */
    private void loadSignUpFragment() {
        Timber.tag(TAG).e("loadSignUpFragment() called");
        transaction = fragmentManager.beginTransaction();
        transaction.replace(fragmentContainer, signUpFragment, signUpFragment.getTag());
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
        loadLoginFragment();
    }

    @Override
    public void launchSignUpFragment() {
        loadSignUpFragment();
    }
}