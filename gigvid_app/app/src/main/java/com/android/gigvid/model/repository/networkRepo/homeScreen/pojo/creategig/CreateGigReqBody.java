
package com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.creategig;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateGigReqBody {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("scheduled_time")
    @Expose
    private String scheduledTime;
    @SerializedName("header_image")
    @Expose
    private String headerImage;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("price")
    @Expose
    private Integer price;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CreateGigReqBody() {
    }

    /**
     * 
     * @param duration
     * @param scheduledTime
     * @param headerImage
     * @param price
     * @param name
     * @param description
     */
    public CreateGigReqBody(String name, String description, String scheduledTime, String headerImage, Integer duration, Integer price) {
        super();
        this.name = name;
        this.description = description;
        this.scheduledTime = scheduledTime;
        this.headerImage = headerImage;
        this.duration = duration;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(String scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public String getHeaderImage() {
        return headerImage;
    }

    public void setHeaderImage(String headerImage) {
        this.headerImage = headerImage;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

}
