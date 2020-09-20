package com.android.gigvid.view.homescreen.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.gigvid.GigVidApplication;
import com.android.gigvid.R;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.CreateGigReqBody;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.CreateGigResp;
import com.android.gigvid.model.repository.reponseData.DataResponse;
import com.android.gigvid.viewModel.homescreen.HostGigViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import timber.log.Timber;

public class HostGigFragment extends DialogFragment {

    private HostGigViewModel hostGigViewModel;
    private MaterialDatePicker<?> picker;
    private TextView eventDate;
    private int year;
    private int month;
    private int day;
    private MaterialButton submitBtn;
    private TextInputLayout eventName, eventDescrip, eventPrice, eventDuration;


    private Observer<DataResponse<CreateGigResp>> createGigRespStatusObserver = new Observer<DataResponse<CreateGigResp>>() {
        @Override
        public void onChanged(DataResponse<CreateGigResp> createGigRespStatus) {
            Timber.d("created gig");
        }
    };

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
        submitBtn.setOnClickListener(submitBtnClick);


        buildDatePicker();
        handleDatePickerClickEvent();
        handleDatePickerCallbacks(view);
    }

    /**
     * Method: Build Date Picker
     */
    private void buildDatePicker() {
        MaterialDatePicker.Builder<?> dateBuilder = MaterialDatePicker.Builder.datePicker();
        picker = dateBuilder.build();
    }

    /**
     * Method: Build Time Picker
     */
    private void buildTimePicker() {

    }

    /**
     * Method: Handle Date Picker button click
     */
    private void handleDatePickerClickEvent() {
        eventDate.setClickable(true);
        eventDate.setFocusableInTouchMode(true);

        eventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFragmentManager() != null) {
                    picker.show(getFragmentManager(),picker.toString());
                } else {
                    Timber.e("getFragmentManager() is Null!");
                }
            }
        });
    }

    /**
     * Method: Get TimeZone
     * NOTE: Perform Null check when receiving value
     *
     * TODO: Implement for API level <26
     */
    private LocalDateTime returnLocalDateTime() {
        LocalDateTime local = null;
        if (Build.VERSION.SDK_INT >= 26) {
            local = LocalDateTime.of(year, month, day, 0, 0);
            local.atZone(ZoneId.ofOffset("UTC", ZoneOffset.UTC)).toInstant().toEpochMilli();
        }
        return local;
    }

    /**
     * Method: Handle Date Picker callback events
     */
    private void handleDatePickerCallbacks(final View view) {

        // Handle Cancel Button Click
        picker.addOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Snackbar.make(view, "Date selection cancelled", Snackbar.LENGTH_SHORT).show();
            }
        });

        // Handle Dismiss
        picker.addOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Snackbar.make(view, "Dismissed", Snackbar.LENGTH_SHORT).show();

            }
        });

        // Handle Negative Button Click listener
        picker.addOnNegativeButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(view, "Cancelled", Snackbar.LENGTH_SHORT).show();
            }
        });

        // Handle Positive Button Click Listener
        picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Object>() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                eventDate.setText(picker.getHeaderText());
            }
        });
    }

    private View.OnClickListener submitBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String name = eventName.getEditText().getText().toString();
            String desc = eventDescrip.getEditText().getText().toString();
            String dur = eventDuration.getEditText().getText().toString();
            String price = eventPrice.getEditText().getText().toString();

            if(name.isEmpty() || desc.isEmpty() || dur.isEmpty() || price.isEmpty()){
                Toast.makeText(GigVidApplication.getGigVidAppContext(), "Enter all data", Toast.LENGTH_SHORT).show();
            } else{
                CreateGigReqBody createGigReqBody = new CreateGigReqBody();
                createGigReqBody.setDescription(desc);
                createGigReqBody.setDuration(Integer.parseInt(dur));
                createGigReqBody.setName(name);
                createGigReqBody.setPrice(Integer.parseInt(price));
                createGigReqBody.setScheduledTime("2020-09-26T10:30:44.665788+05:30");
                hostGigViewModel.createGig(createGigReqBody).observe(HostGigFragment.this, createGigRespStatusObserver);
                Toast.makeText(GigVidApplication.getGigVidAppContext(),"Successfully hosted", Toast.LENGTH_SHORT).show();
            }
        }
    };
}