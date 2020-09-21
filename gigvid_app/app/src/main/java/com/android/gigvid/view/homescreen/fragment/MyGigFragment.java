package com.android.gigvid.view.homescreen.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.android.gigvid.R;
import com.android.gigvid.viewModel.homescreen.MyGigViewModel;

public class MyGigFragment extends Fragment {

    private MyGigViewModel myGigViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myGigViewModel =
                ViewModelProviders.of(this).get(MyGigViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mygig, container, false);
        myGigViewModel.getMyGigList();
//        final TextView textView = root.findViewById(R.id.text_mygig);
//        myGigViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }
}
