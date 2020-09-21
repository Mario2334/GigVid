package com.android.gigvid.view.loginsignup.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.airbnb.lottie.LottieAnimationView;
import com.android.gigvid.R;
import com.android.gigvid.model.repository.networkRepo.loginsignup.pojo.LoginResp;
import com.android.gigvid.model.repository.reponseData.DataResponse;
import com.android.gigvid.model.repository.reponseData.StateDefinition;
import com.android.gigvid.utils.sharedPref.SharedPrefUtils;
import com.android.gigvid.view.homescreen.HomeScreenActivity;
import com.android.gigvid.view.loginsignup.UserAuthActivity;
import com.android.gigvid.view.loginsignup.UserAuthFragmentCommunicator;
import com.android.gigvid.viewModel.loginsignup.LoginSignUpViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import timber.log.Timber;

public class LoginFragment extends Fragment {
    public static final String TAG = "LoginFragment";

    private String LOADING_ANIMATION = "progress_bar.json";
    private String ERROR_ANIMATION = "error.json";

    private TextInputLayout usernameTextInput;
    private TextInputLayout passwordTextInput;
    private Button proceedToLoginButton;
    private Button launchSignUpFragmentButton;
    private ConstraintLayout progressBarLayoutView;
    private Button retryButton;
    private LottieAnimationView progressBarLottieView;

    private LoginSignUpViewModel loginSignUpViewModel;

    private View.OnClickListener onLoginClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String username = usernameTextInput.getEditText().getText().toString();
            String pass = passwordTextInput.getEditText().getText().toString();
            if (isCredentialValid(username, pass)) {
                proceedToLoginButton.setOnClickListener(null);
                loginSignUpViewModel.login(username, pass).observe(LoginFragment.this, loginRespObserver);
                //Disable click listener to avoid multiple calls
            } else {
                Snackbar.make(retryButton, "Invalid Entry", Snackbar.LENGTH_SHORT).show();
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

        progressBarLayoutView = view.findViewById(R.id.progress_bar_view);
        progressBarLottieView = view.findViewById(R.id.display_current_progress);
        retryButton = view.findViewById(R.id.retry_button);

        usernameTextInput = view.findViewById(R.id.name_text_field);
        passwordTextInput = view.findViewById(R.id.password_text_field);
        proceedToLoginButton = view.findViewById(R.id.login_action_button);
        launchSignUpFragmentButton = view.findViewById(R.id.sign_up_button);
        TextView launchSignUpFragmentMsg = view.findViewById(R.id.sign_up_msg);

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

    private Observer<DataResponse<LoginResp>> loginRespObserver = new Observer<DataResponse<LoginResp>>() {
        @Override
        public void onChanged(DataResponse<LoginResp> loginResp) {
            Timber.d("onChanged: login response -- %s", loginResp.getStatus());

            String loadingAnimation;
            if (loginResp.getStatus() == StateDefinition.State.COMPLETED) {
                SharedPrefUtils.saveTokenValueToSP(loginResp.getData().getToken());

                progressBarLayoutView.setVisibility(View.GONE);
                Snackbar.make(retryButton, "Login Success", Snackbar.LENGTH_SHORT).show();
                launchHomeScreenActivity();

            } else if (loginResp.getStatus() == StateDefinition.State.ERROR) {

                if (progressBarLayoutView.getVisibility() != View.VISIBLE) {
                    progressBarLayoutView.setVisibility(View.VISIBLE);
                }
                loadingAnimation = ERROR_ANIMATION;
                loadLottieAnimations(loadingAnimation);
                retryButton.setVisibility(View.VISIBLE);
                handleRetryButtonClick();

                handleErrorScenario(loginResp.getError().getErrorStatus());
                if (!proceedToLoginButton.hasOnClickListeners()) {
                    Timber.d("No on click listeners. So adding them.");
                    proceedToLoginButton.setOnClickListener(onLoginClickListener);
                }

            } else {
                progressBarLayoutView.setVisibility(View.VISIBLE);
                retryButton.setVisibility(View.GONE);
                loadingAnimation = LOADING_ANIMATION;
                loadLottieAnimations(loadingAnimation);
            }
        }
    };

    private void handleErrorScenario(@StateDefinition.ErrorState int errorState) {
        switch (errorState) {
            case StateDefinition.ErrorState.NO_INTERNET_ERROR:
                Snackbar.make(retryButton, "Check internet connectivity", Snackbar.LENGTH_SHORT).show();
                break;
            case StateDefinition.ErrorState.INTERNAL_SERVER_ERROR:
                Snackbar.make(retryButton, "Something went wrong! Try again later.", Snackbar.LENGTH_SHORT).show();
                break;
            default:
                Snackbar.make(retryButton, "Invalid Credentials", Snackbar.LENGTH_SHORT).show();
                break;
        }
    }


    private void launchHomeScreenActivity() {
        Intent homeScreenIntent = new Intent(this.getActivity(), HomeScreenActivity.class);
        startActivity(homeScreenIntent);
        if (getActivity() != null) {
            ((UserAuthActivity) getActivity()).finish();
        }

    }

    /**
     * Method: Load Lottie Animation View to display progress
     */
    private void loadLottieAnimations(String animationName) {
        if (progressBarLottieView.isAnimating()) {
            progressBarLottieView.cancelAnimation();
        }
        progressBarLottieView.setAnimation(animationName);
        progressBarLottieView.loop(true);
        progressBarLottieView.playAnimation();
    }

    /**
     * Method: Retry Button
     * 1. Hides progress UI
     * 2. TODO: Will reconnect to network
     */
    private void handleRetryButtonClick() {
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBarLayoutView.setVisibility(View.GONE);
            }
        });
    }

}