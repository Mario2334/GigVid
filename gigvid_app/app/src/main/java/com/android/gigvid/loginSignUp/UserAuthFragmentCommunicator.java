package com.android.gigvid.loginSignUp;

/**
 * This interface enables communication between UserAuthenticationActivity
 * with the Login and SignUp fragments.
 */

public interface UserAuthFragmentCommunicator {
    void launchLoginFragment();
    void launchSignUpFragment();
}
