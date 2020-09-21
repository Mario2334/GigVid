package com.android.gigvid.view.homescreen.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.airbnb.lottie.LottieAnimationView;
import com.android.gigvid.GigVidApplication;
import com.android.gigvid.R;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.creategig.CreateGigReqBody;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.creategig.CreateGigResp;
import com.android.gigvid.model.repository.reponseData.DataResponse;
import com.android.gigvid.model.repository.reponseData.StateDefinition;
import com.android.gigvid.viewModel.homescreen.HostGigViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

import timber.log.Timber;

public class HostGigFragment extends DialogFragment {

    private String LOADING_ANIMATION = "progress_bar.json";
    private String ERROR_ANIMATION = "error.json";

    private HostGigViewModel hostGigViewModel;
    private TextView eventDate;
    private TextView eventTime;
    private MaterialButton submitBtn;
    private TextInputLayout eventName, eventDescrip, eventPrice, eventDuration;
    private ConstraintLayout progressBarLayoutView;
    private Button retryButton;
    private LottieAnimationView progressBarLottieView;
    String loadingAnimation;
    private int hour;
    private int minute;
    private int year;
    private int month;
    private int day;


    private Observer<DataResponse<CreateGigResp>> createGigRespStatusObserver = new Observer<DataResponse<CreateGigResp>>() {
        @Override
        public void onChanged(DataResponse<CreateGigResp> createGigRespStatus) {
            Timber.d("created gig");
            if (createGigRespStatus.getStatus() == StateDefinition.State.COMPLETED) {
                Timber.d("Create Gig response: %s", createGigRespStatus.getData().getMessage());
                progressBarLayoutView.setVisibility(View.GONE);
                Toast.makeText(GigVidApplication.getGigVidAppContext(), "Successfully hosted", Toast.LENGTH_SHORT).show();
                clearTextFieldsOnSuccess();
                
                if (!submitBtn.hasOnClickListeners()) {
                    Timber.d("No on click listeners. So adding them.");
                    submitBtn.setOnClickListener(submitBtnClickListener);
                }
            } else if (createGigRespStatus.getStatus() == StateDefinition.State.ERROR) {

                //Enable OnClickListener to allow retry
                if (progressBarLayoutView.getVisibility() != View.VISIBLE) {
                    progressBarLayoutView.setVisibility(View.VISIBLE);
                }
                loadingAnimation = ERROR_ANIMATION;
                loadLottieAnimations(loadingAnimation);
                retryButton.setVisibility(View.VISIBLE);
                handleRetryButtonClick();

                handleErrorScenario(createGigRespStatus.getError().getErrorStatus());
                if (!submitBtn.hasOnClickListeners()) {
                    Timber.d("No on click listeners. So adding them.");
                    submitBtn.setOnClickListener(submitBtnClickListener);
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
                Toast.makeText(getActivity(), "Check internet connectivity", Toast.LENGTH_SHORT).show();
                break;
            case StateDefinition.ErrorState.INTERNAL_SERVER_ERROR:
                Toast.makeText(getActivity(), "Something went wrong! Try again later.", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(getActivity(), "Unable to host this event at the moment", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        hostGigViewModel =
                ViewModelProviders.of(this).get(HostGigViewModel.class);
        View root = inflater.inflate(R.layout.fragment_hostgig, container, false);

//
//        //TODO call create Gig viewmodel api on click of submit button
////         hostGigViewModel.createGig(new CreateGig()).observe(this, createGigRespStatusObserver);

        return root;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeUI(view);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        return dialog;
    }

    /**
     * Method: Initialize DatePicker UI
     */
    private void initializeUI(View view) {

        eventName = view.findViewById(R.id.event_name_text_field);
        eventDescrip = view.findViewById(R.id.event_description_text_field);
        eventPrice = view.findViewById(R.id.event_price_text_field);
        eventDuration = view.findViewById(R.id.event_duration);
        eventDate = view.findViewById(R.id.event_date);
        submitBtn = view.findViewById(R.id.host_gig_button);
        submitBtn.setOnClickListener(submitBtnClickListener);
        eventTime = view.findViewById(R.id.event_time);
        progressBarLayoutView = view.findViewById(R.id.progress_bar_view);
        progressBarLottieView = view.findViewById(R.id.display_current_progress);
        retryButton = view.findViewById(R.id.retry_button);

        buildDatePicker();
        buildTimePicker();
        handleDatePickerClickEvent();
        handleTimePickerClickEvent();
    }

    /**
     * Method: Build Date Picker
     */
    private void buildDatePicker() {
        eventDate.setClickable(true);
        eventDate.setFocusableInTouchMode(true);
    }

    /**
     * Method: Build Time Picker
     */
    private void buildTimePicker() {
        eventTime.setClickable(true);
        eventTime.setFocusable(true);
    }

    /**
     * Method: Handle Date Picker button click
     */
    private void handleDatePickerClickEvent() {
        eventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);

                launchDatePicker();
            }
        });
    }

    /**
     * Method: Launch Date Picker
     */
    private void launchDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        eventDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    /**
     * Method: Handle Time Picker button click
     */
    private void handleTimePickerClickEvent() {
        eventTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                hour = c.get(Calendar.HOUR_OF_DAY);
                minute = c.get(Calendar.MINUTE);

                launchTimePicker();
            }
        });
    }

    /**
     * Method: Launch Time Picker
     */
    private void launchTimePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this.getContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        eventTime.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, false);
        timePickerDialog.show();
    }

    /**
     * Method: handle Submit button click
     */
    private View.OnClickListener submitBtnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String name = eventName.getEditText().getText().toString();
            String desc = eventDescrip.getEditText().getText().toString();
            String dur = eventDuration.getEditText().getText().toString();
            String price = eventPrice.getEditText().getText().toString();
            String date = eventDate.getText().toString();
            String time = eventTime.getText().toString();

            if (name.isEmpty() || desc.isEmpty() || dur.isEmpty() || price.isEmpty()
                    || date.isEmpty() || time.isEmpty()) {
                Toast.makeText(GigVidApplication.getGigVidAppContext(), "Enter all data", Toast.LENGTH_SHORT).show();
            } else {
                CreateGigReqBody createGigReqBody = new CreateGigReqBody();
                createGigReqBody.setDescription(desc);
                createGigReqBody.setDuration(Integer.parseInt(dur));
                createGigReqBody.setName(name);
                createGigReqBody.setPrice(Integer.parseInt(price));
                createGigReqBody.setScheduledTime(date + "T" + time + ":00+05:30");
                submitBtn.setOnClickListener(null);
                hostGigViewModel.createGig(createGigReqBody).observe(HostGigFragment.this, createGigRespStatusObserver);
            }
        }
    };

    /**
     * Method: Clear All editTexts on Success
     */
    private void clearTextFieldsOnSuccess() {
        eventTime.setText("");
        eventDate.setText("");
        eventName.getEditText().setText("");
        eventDescrip.getEditText().setText("");
        eventDuration.getEditText().setText("");
        eventPrice.getEditText().setText("");
    }

    /**
     * Method: Load Lottie Animation View to display progress
     */
    private void loadLottieAnimations(String animationName) {
        if(progressBarLottieView.isAnimating()) {
            progressBarLottieView.cancelAnimation();
        }
        progressBarLottieView.setAnimation(animationName);
        progressBarLottieView.loop(true);
        progressBarLottieView.playAnimation();
    }

    /**
     * Method: Retry Button
     *          1. Hides progress UI
     *          2. TODO: Will reconnect to network
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