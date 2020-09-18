package com.android.gigvid.view.loginsignup.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.gigvid.Constants;
import com.android.gigvid.R;
import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.LoginRespStatus;
import com.android.gigvid.utils.sharedPref.SharedPrefUtils;
import com.android.gigvid.view.homescreen.HomeScreenActivity;
import com.android.gigvid.view.loginsignup.UserAuthActivity;
import com.android.gigvid.view.loginsignup.UserAuthFragmentCommunicator;
import com.android.gigvid.viewModel.loginsignup.LoginSignUpViewModel;
import com.google.android.material.textfield.TextInputLayout;

import timber.log.Timber;

public class LoginFragment extends Fragment {
    public static final String TAG = "LoginFragment";

    private TextInputLayout usernameTextInput;
    private TextInputLayout passwordTextInput;
    private Button proceedToLoginButton;
    private Button launchSignUpFragmentButton;
    private TextView launchSignUpFragmentMsg;

    private LoginSignUpViewModel loginSignUpViewModel;
    private View.OnClickListener onLoginClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            String username = usernameTextInput.getEditText().getText().toString();
            String pass = passwordTextInput.getEditText().getText().toString();

            if (isCredentialValid(username, pass)) {
                loginSignUpViewModel.login(username, pass).observe(LoginFragment.this, loginRespObserver);
                //Disable click listener to avoid multiple calls
                proceedToLoginButton.setOnClickListener(null);
            } else {
                Toast.makeText(getActivity(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }

        }
    };

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
        loginSignUpViewModel = ViewModelProviders.of(this).get(LoginSignUpViewModel.class);
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
     *
     * @param view: Refers to the Fragment View used to access view elements
     */
    private void initializeUI(View view) {
        Timber.tag(TAG).e("initializeUI() called");
        usernameTextInput = view.findViewById(R.id.name_text_field);
        passwordTextInput = view.findViewById(R.id.password_text_field);
        proceedToLoginButton = view.findViewById(R.id.login_action_button);
        launchSignUpFragmentButton = view.findViewById(R.id.sign_up_button);
        launchSignUpFragmentMsg = view.findViewById(R.id.sign_up_msg);

        //To enable marquee
        launchSignUpFragmentMsg.setSelected(true);

        launchSignUpOnClick();
        verifyLoginDetail();
    }

    private void verifyLoginDetail() {
        proceedToLoginButton.setOnClickListener(onLoginClickListener);
    }

    private boolean isCredentialValid(String username, String pass) {
        if (username.isEmpty() && pass.isEmpty()) {
            return false;
        } else if (username.isEmpty()) {
            return false;
        } else return !pass.isEmpty();
    }

    /**
     * Method: Launch SignUp fragment via Activity
     */
    private void launchSignUpOnClick() {
        launchSignUpFragmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    Timber.tag(TAG).e("onClick() of launchSignUpOnClick() called");
                    ((UserAuthFragmentCommunicator) getActivity()).launchSignUpFragment();
                }
            }
        });
    }

    private Observer<LoginRespStatus> loginRespObserver = new Observer<LoginRespStatus>() {
        @Override
        public void onChanged(LoginRespStatus loginResp) {
            Timber.d("onChanged: login response -- %s", loginResp.getStatus());

            if (loginResp.getStatus() == Constants.FAIL) {
                //Enable OnClickListener to allow retry
                proceedToLoginButton.setOnClickListener(onLoginClickListener);
                Toast.makeText(getActivity(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
            } else {
                SharedPrefUtils.saveTokenValueToSP(loginResp.getToken());
                Toast.makeText(getActivity(), "Login Success", Toast.LENGTH_SHORT).show();
                launchHomeScreenActivity();
            }
        }
    };

    private void launchHomeScreenActivity() {
        Intent homeScreenIntent = new Intent(this.getActivity(), HomeScreenActivity.class);
        startActivity(homeScreenIntent);
        if (getActivity() != null) {
            ((UserAuthActivity) getActivity()).finish();
        }

    }

}