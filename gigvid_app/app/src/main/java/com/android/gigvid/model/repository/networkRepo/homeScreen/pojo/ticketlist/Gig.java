package com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.ticketlist;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Kavya P S on 21/09/20.
 */
public class Gig {

    @SerializedName("id")
    public int mId;

    @SerializedName("url")
    public String mUrl;

    @SerializedName("is_active")
    public boolean mIsActive;

    @SerializedName("scheduled_time")
    public String mScheduledTime;

    @SerializedName("name")
    public String mName;

    @SerializedName("description")
    public String mDescription;

    @SerializedName("join_url")
    public String mJoinUrl;

    @SerializedName("host_url")
    public String mHostUrl;

    @SerializedName("duration")
    public int mDuration;

    @SerializedName("header_image")
    public String mHeaderImage;

    @SerializedName("created_at")
    public String mCreatedAt;

    @SerializedName("price")
    public int mPrice;

    @SerializedName("user")
    public int mUser;
}
