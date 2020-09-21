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
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.ticketlist.TicketResp;
import com.android.gigvid.model.repository.reponseData.ListResponse;
import com.android.gigvid.model.repository.reponseData.StateDefinition;
import com.android.gigvid.view.homescreen.AdapterEventCommunicator;
import com.android.gigvid.view.homescreen.adapter.TicketListAdapter;
import com.android.gigvid.viewModel.homescreen.TicketsViewModel;

import timber.log.Timber;


public class TicketsFragment extends Fragment implements AdapterEventCommunicator {

    private TicketsViewModel ticketsViewModel;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView mTicketGigsRecyclerView;
    private TicketListAdapter mTicketGigListAdapter;
    private AdapterEventCommunicator mAdapterEventCommunicator;

    private Observer<ListResponse<TicketResp>> ticketListRespStatusObserver = new Observer<ListResponse<TicketResp>>() {
        @Override
        public void onChanged(ListResponse<TicketResp> ticketRespListResponse) {
            if (ticketRespListResponse.getStatus() == StateDefinition.State.COMPLETED) {
                Timber.d("ticket gig success %d", ticketRespListResponse.getData().size());

                mTicketGigListAdapter.setData(ticketRespListResponse.getData());
                mTicketGigListAdapter.notifyDataSetChanged();
            } else {
                Timber.d("ticket gig api failed");
            }
        }
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ticketsViewModel =
                ViewModelProviders.of(this).get(TicketsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tickets, container, false);

        ticketsViewModel.getTicketList().observe(this, ticketListRespStatusObserver);



        mAdapterEventCommunicator = this;
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mTicketGigsRecyclerView = view.findViewById(R.id.tickets_gig_recycler_view);
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
    public void joinEventBtnClick() {

    }
}