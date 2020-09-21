package com.android.gigvid.view.homescreen.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gigvid.R;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.GigListResp;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.ScheduleDateTime;
import com.android.gigvid.utils.dateTime.DateTimeUtils;

import java.util.List;

public class MyGigAdapter extends RecyclerView.Adapter<MyGigAdapter.MyGigAdapterVH> {

    private List<GigListResp> gigList;
    @NonNull
    @Override
    public MyGigAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gig_item_my_gigs, parent , false);
        return new MyGigAdapterVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyGigAdapterVH holder, int position) {
        GigListResp myGigData = gigList.get(position);

        ScheduleDateTime scheduleDateTime = DateTimeUtils.getSchedDtTime(myGigData.getScheduledTime());
        holder.getGigTitle().setText(myGigData.getName());
        holder.getGigDescrip().setText(myGigData.getDescription());

//        holder.getGigPrice().setText(String.valueOf(myGigData.getPrice()));
        holder.getGigDate().setText(scheduleDateTime.getDate());
        holder.getGigTime().setText(scheduleDateTime.getTime());
        holder.getGigMonth().setText(scheduleDateTime.getMonth());
        holder.getGigDuration().setText(myGigData.getDuration().toString()+"hr(s)");

//        holder.getGigBuyBtn().setTag(Constants.BUY_BTN_TAG_KEY, gigListResp.getId());
    }

    @Override
    public int getItemCount() {
        return gigList != null ? gigList.size() : 0;
    }

    public void setData(List<GigListResp> data) {
        gigList = data;
    }

    public class MyGigAdapterVH extends RecyclerView.ViewHolder {

        private TextView gigDate;
        private TextView gigMonth;
        private TextView gigTitle;
        private TextView gigPrice;
        private TextView gigDescrip;
        private TextView gigDuration;

        public TextView getGigDate() {
            return gigDate;
        }

        public TextView getGigMonth() {
            return gigMonth;
        }

        public TextView getGigTitle() {
            return gigTitle;
        }

        public TextView getGigPrice() {
            return gigPrice;
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

        public Button getStartGigBtn() {
            return startGigBtn;
        }

        private TextView gigTime;
        private Button startGigBtn;


        public MyGigAdapterVH(@NonNull View itemView) {
            super(itemView);

            gigDate = (TextView) itemView.findViewById(R.id.my_gigs_date);
            gigMonth = (TextView) itemView.findViewById(R.id.my_gigs_month);
            gigTitle = (TextView) itemView.findViewById(R.id.my_gigs_title);
//            gigPrice = (TextView) itemView.findViewById(R.id.gig_price);
            gigDescrip = (TextView) itemView.findViewById(R.id.my_gigs_description);
            gigTime = (TextView) itemView.findViewById(R.id.my_gigs_time);
            gigDuration = (TextView) itemView.findViewById(R.id.my_gigs_duration);
            startGigBtn = (Button) itemView.findViewById(R.id.start_event_button);
        }
    }
}
