package com.android.gigvid.view.homescreen.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gigvid.GigVidApplication;
import com.android.gigvid.R;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.GigListResp;
import com.android.gigvid.model.repository.reponseData.ListResponse;
import com.android.gigvid.model.repository.reponseData.StateDefinition;
import com.android.gigvid.view.homescreen.adapter.GigListAdapter;
import com.android.gigvid.view.homescreen.adapter.MyGigAdapter;
import com.android.gigvid.viewModel.homescreen.MyGigViewModel;

import timber.log.Timber;

public class MyGigFragment extends Fragment {

    private MyGigViewModel myGigViewModel;
    private RecyclerView mMyGigsRecyclerView;
    private MyGigAdapter mMyGigAdapter;
    private LinearLayoutManager mLayoutManager;

    private Observer<ListResponse<GigListResp>> mMyGigRespStatusObserver = new Observer<ListResponse<GigListResp>>() {
        @Override
        public void onChanged(ListResponse<GigListResp> gigListRespStatus) {
            if (gigListRespStatus.getStatus() == StateDefinition.State.COMPLETED) {
                Timber.d("my gig success %d", gigListRespStatus.getData().size());

                mMyGigAdapter.setData(gigListRespStatus.getData());
                mMyGigAdapter.notifyDataSetChanged();
            } else {
                Timber.d("my gig api failed");
            }
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myGigViewModel =
                ViewModelProviders.of(this).get(MyGigViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mygig, container, false);
        myGigViewModel.getMyGigList().observe(this,mMyGigRespStatusObserver);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        mMyGigsRecyclerView = view.findViewById(R.id.my_gig_recycler_view);
        setUpRecyclerViewAdapter();
    }

    private void setUpRecyclerViewAdapter() {
        mLayoutManager = new LinearLayoutManager(GigVidApplication.getGigVidAppContext());
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mMyGigsRecyclerView.setLayoutManager(mLayoutManager);
        mMyGigsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        if (mMyGigAdapter == null) {
            mMyGigAdapter = new MyGigAdapter();
        }
        mMyGigsRecyclerView.setAdapter(mMyGigAdapter);
    }
}
