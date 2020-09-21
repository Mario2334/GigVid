package com.android.gigvid.view.homescreen.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.gigvid.GigVidApplication;
import com.android.gigvid.R;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.GigListResp;
import com.android.gigvid.model.repository.reponseData.ListResponse;
import com.android.gigvid.model.repository.reponseData.StateDefinition;
import com.android.gigvid.view.homescreen.adapter.GigListAdapter;
import com.android.gigvid.view.homescreen.adapter.MyGigAdapter;
import com.android.gigvid.viewModel.homescreen.MyGigViewModel;
import com.google.android.material.snackbar.Snackbar;

import timber.log.Timber;

public class MyGigFragment extends Fragment {

    private String LOADING_ANIMATION = "progress_bar.json";
    private String ERROR_ANIMATION = "error.json";

    private MyGigViewModel myGigViewModel;
    private RecyclerView mMyGigsRecyclerView;
    private MyGigAdapter mMyGigAdapter;
    private LinearLayoutManager mLayoutManager;
    private ConstraintLayout progressBarLayoutView;
    private Button retryButton;
    private LottieAnimationView progressBarLottieView;
    String loadingAnimation;

    private Observer<ListResponse<GigListResp>> mMyGigRespStatusObserver = new Observer<ListResponse<GigListResp>>() {
        @Override
        public void onChanged(ListResponse<GigListResp> gigListRespStatus) {
            if (gigListRespStatus.getStatus() == StateDefinition.State.COMPLETED) {
                progressBarLayoutView.setVisibility(View.GONE);

                if (gigListRespStatus.getData().size() > 0) {
                    Timber.d("my gig success %d", gigListRespStatus.getData().size());
                    mMyGigAdapter.setData(gigListRespStatus.getData());
                    mMyGigAdapter.notifyDataSetChanged();
                } else {
                    Snackbar.make(retryButton, "No data available!", Snackbar.LENGTH_LONG).show();
                }
                myGigLiveData.removeObservers(MyGigFragment.this);
            } else if (gigListRespStatus.getStatus() == StateDefinition.State.ERROR) {
                if (progressBarLayoutView.getVisibility() != View.VISIBLE) {
                    progressBarLayoutView.setVisibility(View.VISIBLE);
                }
                if (gigListRespStatus.getError().getErrorStatus() == StateDefinition.ErrorState.NO_INTERNET_ERROR) {
                    Snackbar.make(retryButton, gigListRespStatus.getError().getErrorMsg(), Snackbar.LENGTH_SHORT).show();

                } else {
                    Snackbar.make(retryButton, "Something went wrong! Try again later", Snackbar.LENGTH_SHORT).show();
                }
                loadingAnimation = ERROR_ANIMATION;
                loadLottieAnimations(loadingAnimation);
                retryButton.setVisibility(View.VISIBLE);
                handleRetryButtonClick();
                myGigLiveData.removeObservers(MyGigFragment.this);
            } else {
                Timber.d("my gig api loading");
                progressBarLayoutView.setVisibility(View.VISIBLE);
                retryButton.setVisibility(View.GONE);
                loadingAnimation = LOADING_ANIMATION;
                loadLottieAnimations(loadingAnimation);
            }
        }
    };
    private LiveData<ListResponse<GigListResp>> myGigLiveData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myGigViewModel =
                ViewModelProviders.of(this).get(MyGigViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mygig, container, false);
        myGigLiveData = myGigViewModel.getMyGigList();
        myGigLiveData.observe(this, mMyGigRespStatusObserver);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        mMyGigsRecyclerView = view.findViewById(R.id.my_gig_recycler_view);
        progressBarLayoutView = view.findViewById(R.id.progress_bar_view);
        progressBarLottieView = view.findViewById(R.id.display_current_progress);
        retryButton = view.findViewById(R.id.retry_button);
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
                myGigLiveData = myGigViewModel.getMyGigList();
                myGigLiveData.observe(MyGigFragment.this, mMyGigRespStatusObserver);
            }
        });
    }
}
