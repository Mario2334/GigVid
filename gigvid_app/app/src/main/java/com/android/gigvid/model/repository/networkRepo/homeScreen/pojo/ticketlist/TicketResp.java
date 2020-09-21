package com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.ticketlist;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Kavya P S on 21/09/20.
 */
public class TicketResp {
    @SerializedName("gig")
    public Gig gig;

    public Gig getGig() {
        return gig;
    }

    public void setGig(Gig gig) {
        this.gig = gig;
    }

    public int getmUser() {
        return mUser;
    }

    public void setmUser(int mUser) {
        this.mUser = mUser;
    }

    public int getmOrder() {
        return mOrder;
    }

    public void setmOrder(int mOrder) {
        this.mOrder = mOrder;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmCreatedAt() {
        return mCreatedAt;
    }

    public void setmCreatedAt(String mCreatedAt) {
        this.mCreatedAt = mCreatedAt;
    }

    @SerializedName("user")
    public int mUser;

    @SerializedName("order")
    public int mOrder;

    @SerializedName("id")
    public int mId;

    @SerializedName("created_at")
    public String mCreatedAt;
}
