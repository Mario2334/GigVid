package com.android.gigvid.view.loginsignup.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.gigvid.Constants;
import com.android.gigvid.GigVidApplication;
import com.android.gigvid.R;
import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.SignUp;
import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.SignUpResStatus;
import com.android.gigvid.view.loginsignup.UserAuthFragmentCommunicator;
import com.android.gigvid.viewModel.loginsignup.LoginSignUpViewModel;
import com.google.android.material.textfield.TextInputLayout;

import timber.log.Timber;

public class SignUpFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = "SignUpFragment";

    private TextInputLayout usernameTextInput;
    private TextInputLayout emailTextInput;
    private TextInputLayout passwordTextInput;
    private TextInputLayout confirmPasswordTextInput;
    private Button proceedWithSignUpButton;
    private Button launchLoginFragmentButton;
    private LoginSignUpViewModel loginSignUpViewModel;

    public SignUpFragment() {
        // Required empty public constructor
    }

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
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
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Timber.tag(TAG).e("onViewCreated() called");
        super.onViewCreated(view, savedInstanceState);
        initializeUI(view);
    }

    /**
     * Method: Initialize UI elements
     *
     * @param view: Refers to the Fragment View used to access view elements
     */
    private void initializeUI(View view) {
        usernameTextInput = view.findViewById(R.id.user_name_text_field);
        emailTextInput = view.findViewById(R.id.mail_id_text_field);
        passwordTextInput = view.findViewById(R.id.password_text_field);
        confirmPasswordTextInput = view.findViewById(R.id.confirm_password_text_field);
        proceedWithSignUpButton = view.findViewById(R.id.sign_up_action_button);
        launchLoginFragmentButton = view.findViewById(R.id.launch_login_fragment_button);

        proceedWithSignUpButton.setOnClickListener(this);
        launchLoginFragmentButton.setOnClickListener(this);

    }

    /**
     * Method: Launch SignUp fragment via Activity
     */
    private void launchLoginOnClick() {
        Timber.d("onCLick sign up  ");
        if (getActivity() != null) {
            Timber.tag(TAG).e("onClick() of launchLoginOnClick() called");
            ((UserAuthFragmentCommunicator) getActivity()).launchLoginFragment();
        }

    }


    private Observer<SignUpResStatus> signUpRespObserver = new Observer<SignUpResStatus>() {
        @Override
        public void onChanged(SignUpResStatus signUpResStatus) {

            if (signUpResStatus.getStatus() == Constants.FAIL) {
                Toast.makeText(GigVidApplication.getGigVidAppContext(), "Please enter valid data", Toast.LENGTH_SHORT).show();
            } else {
                launchLoginOnClick();
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.launch_login_fragment_button:
                launchLoginOnClick();
                break;
            case R.id.sign_up_action_button:
                handleSignUpClick();
                break;
            default:
                break;
        }
    }

    private void handleSignUpClick() {
        String username = usernameTextInput.getEditText().getText().toString();
        String confirmPass = confirmPasswordTextInput.getEditText().getText().toString();
        String pass = passwordTextInput.getEditText().getText().toString();
        String emailId = emailTextInput.getEditText().getText().toString();

        if (isSignUpDataValid(username, emailId, pass, confirmPass)) {
            SignUp signUpBody = new SignUp();
            signUpBody.setUsername(usernameTextInput.getEditText().getText().toString());
            signUpBody.setEmail(emailTextInput.getEditText().getText().toString());
            signUpBody.setPassword(confirmPasswordTextInput.getEditText().getText().toString());
            loginSignUpViewModel.signUp(signUpBody).observe(this, signUpRespObserver);
        } else {
            Toast.makeText(GigVidApplication.getGigVidAppContext(), "Invalid data", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isSignUpDataValid(String username, String emailId, String pass, String cnfirmPass) {

        if (username.isEmpty() || emailId.isEmpty() || pass.isEmpty() || cnfirmPass.isEmpty()) {
            return false;
        } else {
            return pass.equals(cnfirmPass);
        }
    }
}