package com.android.gigvid.model.repository.networkRepo.homeScreen.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BuyGigReqBody {
    public BuyGigReqBody(Integer id) {
        this.id = id;
    }

    @SerializedName("gig")
    @Expose
    private Integer id;
}
