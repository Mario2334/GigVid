
package com.android.gigvid.model.repository.networkRepo.homeScreen.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateGig {

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

    private String minutes;

    /**
     * No args constructor for use in serialization
     *
     */
    public CreateGig() {
    }

    public CreateGig(String name, String description, String scheduledTime, String headerImage, String minutes) {
        this.name = name;
        this.description = description;
        this.scheduledTime = scheduledTime;
        this.headerImage = headerImage;
        this.minutes = minutes;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
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

}
