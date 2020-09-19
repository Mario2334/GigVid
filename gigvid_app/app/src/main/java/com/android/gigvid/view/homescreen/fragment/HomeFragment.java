package com.android.gigvid.view.homescreen.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gigvid.Constants;
import com.android.gigvid.R;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.GigListResp;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.GigListRespStatus;
import com.android.gigvid.model.repository.reponseData.ListResponse;
import com.android.gigvid.model.repository.reponseData.StateDefinition;
import com.android.gigvid.viewModel.homescreen.HomeViewModel;

import timber.log.Timber;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView listGigsRecyclerView;

    private Observer<ListResponse<GigListResp>> gigListRespStatusObserver = new Observer<ListResponse<GigListResp>>() {
        @Override
        public void onChanged(ListResponse<GigListResp> gigListRespStatus) {
            if(gigListRespStatus.getStatus() == StateDefinition.State.COMPLETED){
                Timber.d("list gig success %d",gigListRespStatus.getData().size());
            } else{
                Timber.d("list gig api failed");
            }
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
//
        homeViewModel.getGigListLiveData().observe(this, gigListRespStatusObserver);

        Timber.d("HomeFrag on create");
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listGigsRecyclerView = view.findViewById(R.id.list_gigs_recycler_view);
    }
}