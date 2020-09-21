package com.android.gigvid.view.homescreen.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.ticketlist.TicketResp;
import com.android.gigvid.view.homescreen.AdapterEventCommunicator;

import java.util.List;

public class TicketListAdapter extends RecyclerView.Adapter<TicketListAdapter.TicketListAdapterVH> {
    private List<TicketResp> gigList;
    private AdapterEventCommunicator mAdapterEventCommunicator;

    public TicketListAdapter(AdapterEventCommunicator adapterEventCommunicator) {
        mAdapterEventCommunicator = adapterEventCommunicator;
    }

    public void setData(List<TicketResp> data) {
        gigList = data;
    }

    @NonNull
    @Override
    public TicketListAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull TicketListAdapterVH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class TicketListAdapterVH extends RecyclerView.ViewHolder {
        public TicketListAdapterVH(@NonNull View itemView) {
            super(itemView);
        }
    }
}
