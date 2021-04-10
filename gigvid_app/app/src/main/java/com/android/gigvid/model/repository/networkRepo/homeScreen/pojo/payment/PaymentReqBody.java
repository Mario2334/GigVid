package com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.payment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentReqBody {

    public PaymentReqBody(int id, String orderID) {
        this.id = id;
        this.orderID = orderID;
    }

    @SerializedName("gig")
    @Expose
    private int id;

    @SerializedName("order_id")
    @Expose
    private String orderID;
}
