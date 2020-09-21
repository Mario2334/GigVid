package com.android.gigvid.view.homescreen.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gigvid.R;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.ScheduleDateTime;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.ticketlist.Gig;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.ticketlist.TicketResp;
import com.android.gigvid.utils.dateTime.DateTimeUtils;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gig_item_tickets, parent , false);
        return new TicketListAdapterVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketListAdapterVH holder, int position) {

        TicketResp ticketResp = gigList.get(position);
        Gig gigData = ticketResp.getGig();
        ScheduleDateTime scheduleDateTime = DateTimeUtils.getSchedDtTime(gigData.getmScheduledTime());

        holder.getGigTitle().setText(gigData.getmName());
        holder.getGigDescrip().setText(gigData.getmDescription());
        holder.getGigDuration().setText(String.valueOf(gigData.getmDuration()));
        holder.getGigDate().setText(scheduleDateTime.getDate());
        holder.getGigMonth().setText(scheduleDateTime.getMonth());
        holder.getGigTime().setText(scheduleDateTime.getTime());

    }

    @Override
    public int getItemCount() {
        return gigList != null ? gigList.size() : 0;
    }

    public static class TicketListAdapterVH extends RecyclerView.ViewHolder {

        private TextView gigDate;
        private TextView gigMonth;
        private TextView gigTitle;

        public TextView getGigDate() {
            return gigDate;
        }

        public TextView getGigMonth() {
            return gigMonth;
        }

        public TextView getGigTitle() {
            return gigTitle;
        }



        public TextView getGigDescrip() {
            return gigDescrip;
        }

        public TextView getGigDuration() {
            return gigDuration;
        }

        public TextView getGigTime() {
            return gigTime;
        }

        public Button getGigJoinBtn() {
            return gigJoinBtn;
        }

        private TextView gigDescrip;
        private TextView gigDuration;
        private TextView gigTime;
        private Button gigJoinBtn;

        public TicketListAdapterVH(@NonNull View itemView) {
            super(itemView);

            gigDate = (TextView) itemView.findViewById(R.id.ticket_date);
            gigMonth = (TextView) itemView.findViewById(R.id.ticket_month);
            gigTitle = (TextView) itemView.findViewById(R.id.ticket_title);
            gigDescrip = (TextView) itemView.findViewById(R.id.ticket_description);
            gigTime = (TextView) itemView.findViewById(R.id.ticket_time);
            gigDuration = (TextView) itemView.findViewById(R.id.ticket_duration);
            gigJoinBtn = (Button) itemView.findViewById(R.id.launch_event_button);
        }
    }
}
