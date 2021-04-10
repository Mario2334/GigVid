package com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentResp {

    public PaymentResp(String id, String createdAt, Gig gig) {
        this.id = id;
        this.createdAt = createdAt;
        this.gig = gig;
    }

    @SerializedName("id")
    @Expose
    public String id;

    @SerializedName("created_at")
    @Expose
    public String createdAt;

    @SerializedName("gig")
    public Gig gig;
}
