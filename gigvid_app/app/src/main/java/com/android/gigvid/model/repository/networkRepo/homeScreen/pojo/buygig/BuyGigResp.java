
package com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.buygig;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BuyGigResp {

    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("order_id")
    @Expose
    private Integer orderId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public BuyGigResp() {
    }

    /**
     * 
     * @param orderId
     * @param link
     */
    public BuyGigResp(String link, Integer orderId) {
        super();
        this.link = link;
        this.orderId = orderId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

}
