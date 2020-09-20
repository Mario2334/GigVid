package com.android.gigvid.view.homescreen.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

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

    private HostGigViewModel hostGigViewModel;
    private TextView eventDate;
    private TextView eventTime;
    private MaterialButton submitBtn;
    private TextInputLayout eventName, eventDescrip, eventPrice, eventDuration;
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
                Toast.makeText(GigVidApplication.getGigVidAppContext(), "Successfully hosted", Toast.LENGTH_SHORT).show();
                if (!submitBtn.hasOnClickListeners()) {
                    Timber.d("No on click listeners. So adding them.");
                    submitBtn.setOnClickListener(submitBtnClickListener);
                }
            } else if (createGigRespStatus.getStatus() == StateDefinition.State.ERROR) {

                //Enable OnClickListener to allow retry
                handleErrorScenario(createGigRespStatus.getError().getErrorStatus());
                if (!submitBtn.hasOnClickListeners()) {
                    Timber.d("No on click listeners. So adding them.");
                    submitBtn.setOnClickListener(submitBtnClickListener);
                }
            } else {
//               TODO("Handle loading screen here.")
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
                createGigReqBody.setScheduledTime(date + "T" + time + ":44.665788+05:30");
                submitBtn.setOnClickListener(null);
                hostGigViewModel.createGig(createGigReqBody).observe(HostGigFragment.this, createGigRespStatusObserver);
            }
        }
    };
}