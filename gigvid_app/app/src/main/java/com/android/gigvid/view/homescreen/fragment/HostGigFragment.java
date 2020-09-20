package com.android.gigvid.view.homescreen.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.gigvid.R;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.CreateGigRespStatus;
import com.android.gigvid.viewModel.homescreen.HostGigViewModel;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

import timber.log.Timber;

public class HostGigFragment extends DialogFragment {

    private HostGigViewModel hostGigViewModel;
    private MaterialDatePicker<?> picker;
    private TextView eventDate;
    private int year;
    private int month;
    private int day;

    private Observer<CreateGigRespStatus> createGigRespStatusObserver = new Observer<CreateGigRespStatus>() {
        @Override
        public void onChanged(CreateGigRespStatus createGigRespStatus) {
            Timber.d("created gig");
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        hostGigViewModel =
                ViewModelProviders.of(this).get(HostGigViewModel.class);
        View root = inflater.inflate(R.layout.fragment_hostgig, container, false);
//        final TextView textView = root.findViewById(R.id.text_hostgig);
//        hostGigViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
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

        eventDate = view.findViewById(R.id.event_date);

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
}