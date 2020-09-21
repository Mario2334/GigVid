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
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.ticketlist.TicketResp;
import com.android.gigvid.model.repository.reponseData.ListResponse;
import com.android.gigvid.model.repository.reponseData.StateDefinition;
import com.android.gigvid.view.homescreen.AdapterEventCommunicator;
import com.android.gigvid.view.homescreen.adapter.TicketListAdapter;
import com.android.gigvid.viewModel.homescreen.TicketsViewModel;
import com.google.android.material.snackbar.Snackbar;

import timber.log.Timber;


public class TicketsFragment extends Fragment implements AdapterEventCommunicator {

    private String LOADING_ANIMATION = "progress_bar.json";
    private String ERROR_ANIMATION = "error.json";

    private TicketsViewModel ticketsViewModel;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView mTicketGigsRecyclerView;
    private TicketListAdapter mTicketGigListAdapter;
    private AdapterEventCommunicator mAdapterEventCommunicator;
    private ConstraintLayout progressBarLayoutView;
    private Button retryButton;
    private LottieAnimationView progressBarLottieView;
    String loadingAnimation;

    private Observer<ListResponse<TicketResp>> ticketListRespStatusObserver = new Observer<ListResponse<TicketResp>>() {
        @Override
        public void onChanged(ListResponse<TicketResp> ticketRespListResponse) {
            if (ticketRespListResponse.getStatus() == StateDefinition.State.COMPLETED) {
                progressBarLayoutView.setVisibility(View.GONE);

                if (ticketRespListResponse.getData().size() > 0) {
                    Timber.d("ticket gig success %d", ticketRespListResponse.getData().size());
                    mTicketGigListAdapter.setData(ticketRespListResponse.getData());
                    mTicketGigListAdapter.notifyDataSetChanged();
                } else {
                    Snackbar.make(retryButton, "No data available!", Snackbar.LENGTH_LONG).show();
                }
                ticketsLiveData.removeObservers(TicketsFragment.this);
            } else if (ticketRespListResponse.getStatus() == StateDefinition.State.ERROR) {
                if (progressBarLayoutView.getVisibility() != View.VISIBLE) {
                    progressBarLayoutView.setVisibility(View.VISIBLE);
                }
                if (ticketRespListResponse.getError().getErrorStatus() == StateDefinition.ErrorState.NO_INTERNET_ERROR) {
                    Snackbar.make(retryButton, ticketRespListResponse.getError().getErrorMsg(), Snackbar.LENGTH_SHORT).show();

                } else {
                    Snackbar.make(retryButton, "Something went wrong! Try again later", Snackbar.LENGTH_SHORT).show();
                }
                loadingAnimation = ERROR_ANIMATION;
                loadLottieAnimations(loadingAnimation);
                retryButton.setVisibility(View.VISIBLE);
                handleRetryButtonClick();
                ticketsLiveData.removeObservers(TicketsFragment.this);
            } else {
                Timber.d("ticket gig api loading");
                progressBarLayoutView.setVisibility(View.VISIBLE);
                retryButton.setVisibility(View.GONE);
                loadingAnimation = LOADING_ANIMATION;
                loadLottieAnimations(loadingAnimation);
            }
        }
    };
    private LiveData<ListResponse<TicketResp>> ticketsLiveData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ticketsViewModel =
                ViewModelProviders.of(this).get(TicketsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tickets, container, false);

        ticketsLiveData = ticketsViewModel.getTicketList();
        ticketsLiveData.observe(this, ticketListRespStatusObserver);


        mAdapterEventCommunicator = this;
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mTicketGigsRecyclerView = view.findViewById(R.id.tickets_gig_recycler_view);
        progressBarLayoutView = view.findViewById(R.id.progress_bar_view);
        progressBarLottieView = view.findViewById(R.id.display_current_progress);
        retryButton = view.findViewById(R.id.retry_button);
        setUpRecyclerViewAdapter();
    }

    private void setUpRecyclerViewAdapter() {
        mLayoutManager = new LinearLayoutManager(GigVidApplication.getGigVidAppContext());
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mTicketGigsRecyclerView.setLayoutManager(mLayoutManager);
        mTicketGigsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        if (mTicketGigListAdapter == null) {
            mTicketGigListAdapter = new TicketListAdapter(mAdapterEventCommunicator);
        }
        mTicketGigsRecyclerView.setAdapter(mTicketGigListAdapter);
    }

    @Override
    public void buyBtnClickEvent(int gigId) {

    }

    @Override
    public void joinEventBtnClick(String joinUrl) {
        //TODO launch zoom meeting
        Timber.d("Launch zoom meeting URL -- %s", joinUrl);
//        Intent launchMeeting = new Intent();
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
                ticketsLiveData = ticketsViewModel.getTicketList();
                ticketsLiveData.observe(TicketsFragment.this, ticketListRespStatusObserver);

            }
        });
    }
}