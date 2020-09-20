package com.android.gigvid.view.homescreen.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.gigvid.Constants;
import com.android.gigvid.R;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.GigListResp;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.ScheduleDateTime;
import com.android.gigvid.utils.dateTime.DateTimeUtils;
import com.android.gigvid.view.homescreen.AdapterEventCommunicator;

import java.util.List;

import timber.log.Timber;

public class GigListAdapter extends RecyclerView.Adapter<GigListAdapter.GigListAdapterVH> {

    private List<GigListResp> gigList;
    private AdapterEventCommunicator mAdapterEventCommunicator;
    public GigListAdapter(AdapterEventCommunicator adapterEventCommunicator){
        mAdapterEventCommunicator = adapterEventCommunicator;
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
        holder.getGigDuration().setText(gigListResp.getDuration().toString()+"hr(s)");

        holder.getGigBuyBtn().setTag(Constants.BUY_BTN_TAG_KEY, gigListResp.getId());
        holder.getGigBuyBtn().setOnClickListener(buyBtnClickEvent);

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
        private TextView gigDuration;

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

        private TextView getGigDuration() {
            return gigDuration;
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
            gigDuration = (TextView) itemView.findViewById(R.id.gig_duration);
            gigBuyBtn = (Button) itemView.findViewById(R.id.buy_gig_button);
        }


    }

    private View.OnClickListener buyBtnClickEvent = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Timber.d("onClick: buy btn");
            Button btn = (Button) view;
            int gigId = (int)btn.getTag(Constants.BUY_BTN_TAG_KEY);
            mAdapterEventCommunicator.buyBtnClickEvent(gigId);
        }
    };



}
