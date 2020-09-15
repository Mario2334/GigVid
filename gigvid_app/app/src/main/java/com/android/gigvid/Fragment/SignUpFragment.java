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

public class SignUpFragment extends Fragment {
    public static final String TAG = "SignUpFragment";

    private TextInputLayout usernameTextInput;
    private TextInputLayout emailTextInput;
    private TextInputLayout passwordTextInput;
    private TextInputLayout confirmPasswordTextInput;
    private Button proceedWithSignUpButton;
    private Button launchLoginFragmentButton;

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
     * @param view: Refers to the Fragment View used to access view elements
     */
    private void initializeUI(View view) {
        usernameTextInput = view.findViewById(R.id.user_name_text_field);
        emailTextInput = view.findViewById(R.id.mail_id_text_field);
        passwordTextInput = view.findViewById(R.id.password_text_field);
        confirmPasswordTextInput = view.findViewById(R.id.confirm_password_text_field);
        proceedWithSignUpButton = view.findViewById(R.id.sign_up_action_button);
        launchLoginFragmentButton = view.findViewById(R.id.launch_login_fragment_button);

        launchLoginOnClick();
    }

    /**
     * Method: Launch SignUp fragment via Activity
     */
    private void launchLoginOnClick() {
        launchLoginFragmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    Timber.tag(TAG).e("onClick() of launchLoginOnClick() called");
                    ((UserAuthFragmentCommunicator) getActivity()).launchLoginFragment();
                }
            }
        });
    }
}