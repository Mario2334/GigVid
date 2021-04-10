package com.android.gigvid.view.homescreen.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.buygig.BuyGigResp;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.payment.PaymentResp;
import com.android.gigvid.model.repository.reponseData.DataResponse;
import com.android.gigvid.model.repository.reponseData.ListResponse;
import com.android.gigvid.model.repository.reponseData.StateDefinition;
import com.android.gigvid.view.MainActivity;
import com.android.gigvid.view.homescreen.AdapterEventCommunicator;
import com.android.gigvid.view.homescreen.activity.HomeScreenActivity;
import com.android.gigvid.view.homescreen.adapter.GigListAdapter;
import com.android.gigvid.viewModel.homescreen.HomeViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.razorpay.Checkout;

import org.json.JSONException;
import org.json.JSONObject;

import timber.log.Timber;

public class HomeFragment extends Fragment implements AdapterEventCommunicator {

    private String LOADING_ANIMATION = "progress_bar.json";
    private String ERROR_ANIMATION = "error.json";
    private String NO_DATA_ANIMATION = "no_data.json";

    private HomeViewModel homeViewModel;
    private RecyclerView listGigsRecyclerView;
    private GigListAdapter gigListAdapter;
    private AdapterEventCommunicator mAdapterEventCommunicator;
    private ConstraintLayout progressBarLayoutView;
    private Button retryButton;
    private LottieAnimationView progressBarLottieView;
    String loadingAnimation;

    private int gigId = -100;

    private Observer<ListResponse<GigListResp>> gigListRespStatusObserver = new Observer<ListResponse<GigListResp>>() {
        @Override
        public void onChanged(ListResponse<GigListResp> gigListRespStatus) {
            if (gigListRespStatus.getStatus() == StateDefinition.State.COMPLETED) {
                if (gigListRespStatus.getData().size() > 0) {
                    Timber.d("list gig success %d", gigListRespStatus.getData().size());
                    progressBarLayoutView.setVisibility(View.GONE);

                    gigListAdapter.setData(gigListRespStatus.getData());
                    gigListAdapter.notifyDataSetChanged();
                } else {
                    progressBarLayoutView.setVisibility(View.VISIBLE);

                    loadingAnimation = NO_DATA_ANIMATION;
                    loadLottieAnimations(loadingAnimation);

                }
                gigListLiveData.removeObservers(HomeFragment.this);
            } else if (gigListRespStatus.getStatus() == StateDefinition.State.ERROR) {
                if (progressBarLayoutView.getVisibility() != View.VISIBLE) {
                    progressBarLayoutView.setVisibility(View.VISIBLE);
                }
                loadingAnimation = ERROR_ANIMATION;
                loadLottieAnimations(loadingAnimation);
                retryButton.setVisibility(View.VISIBLE);
                handleRetryButtonClick();
                gigListLiveData.removeObservers(HomeFragment.this);
            } else {
                Timber.d("list gig api LOADING");
                progressBarLayoutView.setVisibility(View.VISIBLE);
                retryButton.setVisibility(View.GONE);
                loadingAnimation = LOADING_ANIMATION;
                loadLottieAnimations(loadingAnimation);
            }
        }
    };
    private LinearLayoutManager mLayoutManager;
    private LiveData<ListResponse<GigListResp>> gigListLiveData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        gigListLiveData = homeViewModel.getGigListLiveData();
        gigListLiveData.observe(getViewLifecycleOwner(), gigListRespStatusObserver);

        mAdapterEventCommunicator = this;
        Timber.d("HomeFrag on create");
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBarLayoutView = view.findViewById(R.id.progress_bar_view);
        progressBarLottieView = view.findViewById(R.id.display_current_progress);
        retryButton = view.findViewById(R.id.retry_button);
        listGigsRecyclerView = view.findViewById(R.id.list_gigs_recycler_view);

        setUpRecyclerViewAdapter();

    }

    private void setUpRecyclerViewAdapter() {
        mLayoutManager = new LinearLayoutManager(GigVidApplication.getGigVidAppContext());
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        listGigsRecyclerView.setLayoutManager(mLayoutManager);
        listGigsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        if (gigListAdapter == null) {
            gigListAdapter = new GigListAdapter(mAdapterEventCommunicator);
        }
        listGigsRecyclerView.setAdapter(gigListAdapter);
    }

    @Override
    public void buyBtnClickEvent(GigListResp gigInfo) {

//        homeViewModel.buyGigTicket(new BuyGigReqBody(gigId)).observe(this, mBuyGigRespObsever);
        gigId = gigInfo.getId();
        Checkout checkout = new Checkout();

        checkout.setKeyID("rzp_test_2YOMyazqXuU6zk");

        checkout.setImage(R.drawable.rp_icon);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", gigInfo.getName());
            jsonObject.put("description", gigInfo.getDescription());
            jsonObject.put("theme.color", "#0093DD");
            jsonObject.put("currency", "INR");
            jsonObject.put("amount", gigInfo.getPrice() * 100);
            jsonObject.put("prefill.contact", "8124573519");
            jsonObject.put("prefill.email", "test@gmail.com");

            checkout.open(getActivity(), jsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void checkApiAfterRazorPaySuccess(String orderId) {
        Timber.d("KPS gigId = " + gigId + " | orderId =" + orderId);
        LiveData<DataResponse<PaymentResp>> liveData = homeViewModel.uploadPaymentInfoToServer(gigId, orderId);
        liveData.observe(this, paymentRespDataResponse -> {
            if (paymentRespDataResponse.getStatus() == StateDefinition.State.COMPLETED) {
                Timber.d("KPS COMPLETED=====\nresponse is -> gig_id=" + paymentRespDataResponse.getData().gig.id + " | created_at=" + paymentRespDataResponse.getData().createdAt + " | id=" + paymentRespDataResponse.getData().id + "\n");
            } else if (paymentRespDataResponse.getStatus() == StateDefinition.State.ERROR) {
                Timber.d("KPS error = %s", paymentRespDataResponse.getError().getErrorMsg());
            } else if (paymentRespDataResponse.getStatus() == StateDefinition.State.LOADING) {
                Timber.d("KPS Loading!!!");
            }
        });
    }

    @Override
    public void joinEventBtnClick(String joinUrl) {

    }

    @Override
    public void launchVideoPlayer(String meetingName) {

    }


    private Observer<DataResponse<BuyGigResp>> mBuyGigRespObsever = new Observer<DataResponse<BuyGigResp>>() {
        @Override
        public void onChanged(DataResponse<BuyGigResp> buyGigRespDataResponse) {
            if (buyGigRespDataResponse.getStatus() == StateDefinition.State.COMPLETED) {
                Log.d("SMP", "onChanged: buy gig on change observer" + buyGigRespDataResponse.getData().getLink());

                progressBarLayoutView.setVisibility(View.GONE);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(buyGigRespDataResponse.getData().getLink()));
                startActivity(browserIntent);
            } else if (buyGigRespDataResponse.getStatus() == StateDefinition.State.ERROR) {
                Log.d("SMP", "onChanged: error");
                if (progressBarLayoutView.getVisibility() != View.VISIBLE) {
                    progressBarLayoutView.setVisibility(View.VISIBLE);
                }
                loadingAnimation = ERROR_ANIMATION;
                loadLottieAnimations(loadingAnimation);
                retryButton.setVisibility(View.VISIBLE);
                handleRetryButtonClick();
                Snackbar.make(retryButton, buyGigRespDataResponse.getError().getErrorMsg(), Snackbar.LENGTH_SHORT).show();
//                Toast.makeText(GigVidApplication.getGigVidAppContext(), buyGigRespDataResponse.getError().getErrorMsg(), Toast.LENGTH_SHORT).show();
            } else {
                progressBarLayoutView.setVisibility(View.VISIBLE);
                retryButton.setVisibility(View.GONE);
                loadingAnimation = LOADING_ANIMATION;
                loadLottieAnimations(loadingAnimation);
            }

        }
    };

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
                gigListLiveData = homeViewModel.getGigListLiveData();
                gigListLiveData.observe(HomeFragment.this, gigListRespStatusObserver);
            }
        });
    }
}