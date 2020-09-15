package com.android.gigvid.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.gigvid.R;
import com.android.gigvid.UserAuthFragmentCommunicator;
import com.google.android.material.textfield.TextInputLayout;

import timber.log.Timber;

public class LoginFragment extends Fragment {
    public static final String TAG = "LoginFragment";

    private TextInputLayout usernameTextInput;
    private TextInputLayout passwordTextInput;
    private Button proceedToLoginButton;
    private Button launchSignUpFragmentButton;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.tag(TAG).e("onCreate() called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Timber.tag(TAG).e("onCreateView() called");
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Timber.tag(TAG).e("onViewCreated() called");
        initializeUI(view);
    }

    /**
     * Method: Initialize UI elements
     * @param view: Refers to the Fragment View used to access view elements
     */
    private void initializeUI(View view) {
        Timber.tag(TAG).e("initializeUI() called");
        usernameTextInput = view.findViewById(R.id.name_text_field);
        passwordTextInput = view.findViewById(R.id.password_text_field);
        proceedToLoginButton = view.findViewById(R.id.login_action_button);
        launchSignUpFragmentButton = view.findViewById(R.id.sign_up_button);

        launchSignUpOnClick();
    }

    /**
     * Method: Launch SignUp fragment via Activity
     */
    private void launchSignUpOnClick() {
        launchSignUpFragmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity()!= null){
                    Timber.tag(TAG).e("onClick() of launchSignUpOnClick() called");
                    ((UserAuthFragmentCommunicator)getActivity()).launchSignUpFragment();
                }
            }
        });
    }
}