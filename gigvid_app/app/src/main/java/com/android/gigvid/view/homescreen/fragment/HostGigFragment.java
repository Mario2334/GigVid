package com.android.gigvid.view.homescreen.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.gigvid.R;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.CreateGig;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.CreateGigRespStatus;
import com.android.gigvid.viewModel.homescreen.HostGigViewModel;

import timber.log.Timber;

public class HostGigFragment extends Fragment {

    private HostGigViewModel hostGigViewModel;

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
        final TextView textView = root.findViewById(R.id.text_hostgig);
        hostGigViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        //TODO call create Gig viewmodel api on click of submit button
//         hostGigViewModel.createGig(new CreateGig()).observe(this, createGigRespStatusObserver);

        return root;

    }
}