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

import com.android.gigvid.Constants;
import com.android.gigvid.R;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.GigListRespStatus;
import com.android.gigvid.viewModel.homescreen.HomeViewModel;

import timber.log.Timber;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private Observer<GigListRespStatus> gigListRespStatusObserver = new Observer<GigListRespStatus>() {
        @Override
        public void onChanged(GigListRespStatus gigListRespStatus) {
            if(gigListRespStatus.getStatus() == Constants.FAIL){
                Timber.d("list gig api failed");
            } else{
                Timber.d("list gig success %d",gigListRespStatus.getGigListResp().size());
            }
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        homeViewModel.getGigListLiveData().observe(this, gigListRespStatusObserver);
        return root;
    }
}