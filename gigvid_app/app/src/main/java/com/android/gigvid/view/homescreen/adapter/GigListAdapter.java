package com.android.gigvid.view.homescreen.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gigvid.R;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.GigListResp;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.ScheduleDateTime;
import com.android.gigvid.utils.dateTime.DateTimeUtils;

import java.util.List;

public class GigListAdapter extends RecyclerView.Adapter<GigListAdapter.GigListAdapterVH> {

    private List<GigListResp> gigList;
    public GigListAdapter(){

    }
    @NonNull
    @Override
    public GigListAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gig_item_home, parent , false);
        return new GigListAdapterVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GigListAdapterVH holder, int position) {
        GigListResp gigListResp = gigList.get(position);
        ScheduleDateTime scheduleDateTime = DateTimeUtils.getSchedDtTime(gigListResp.getScheduledTime());
        holder.getGigTitle().setText(gigListResp.getName());
        holder.getGigDescrip().setText(gigListResp.getDescription());

        holder.getGigPrice().setText(String.valueOf(gigListResp.getPrice()));
        holder.getGigDate().setText(scheduleDateTime.getDate());
        holder.getGigTime().setText(scheduleDateTime.getTime());
        holder.getGigMonth().setText(scheduleDateTime.getMonth());

//        Timber.d("date time == %s"+", duration == %d", gigListResp.getScheduledTime(), gigListResp.getDuration());

    }

    @Override
    public int getItemCount() {
        return gigList != null ? gigList.size() : 0;
    }

    public void setData(List<GigListResp> data) {
        gigList = data;
    }

    public static class GigListAdapterVH extends RecyclerView.ViewHolder {
        private ImageView gigImg;
        private TextView gigDate;
        private TextView gigMonth;
        private TextView gigTitle;
        private TextView gigPrice;
        private TextView gigDescrip;

        public ImageView getGigImg() {
            return gigImg;
        }

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

        public TextView getGigTime() {
            return gigTime;
        }

        public Button getGigBuyBtn() {
            return gigBuyBtn;
        }

        private TextView gigTime;
        private Button gigBuyBtn;

        public GigListAdapterVH(@NonNull View itemView) {
            super(itemView);

            gigImg = (ImageView) itemView.findViewById(R.id.gig_image_view);
            gigDate = (TextView) itemView.findViewById(R.id.gig_date);
            gigMonth = (TextView) itemView.findViewById(R.id.gig_month);
            gigTitle = (TextView) itemView.findViewById(R.id.gig_title);
            gigPrice = (TextView) itemView.findViewById(R.id.gig_price);
            gigDescrip = (TextView) itemView.findViewById(R.id.gig_description);
            gigTime = (TextView) itemView.findViewById(R.id.gig_time);
            gigBuyBtn = (Button) itemView.findViewById(R.id.buy_gig_button);
        }
    }



}
