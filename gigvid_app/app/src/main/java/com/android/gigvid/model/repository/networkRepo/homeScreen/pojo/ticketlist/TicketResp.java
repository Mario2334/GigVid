package com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.ticketlist;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Kavya P S on 21/09/20.
 */
public class TicketResp {
    @SerializedName("gig")
    public Gig gig;

    @SerializedName("user")
    public int mUser;

    @SerializedName("order")
    public int mOrder;

    @SerializedName("id")
    public int mId;

    @SerializedName("created_at")
    public String mCreatedAt;
}
