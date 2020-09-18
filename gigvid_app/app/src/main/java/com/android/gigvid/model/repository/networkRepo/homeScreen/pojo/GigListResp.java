
package com.android.gigvid.model.repository.networkRepo.homeScreen.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GigListResp {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("is_active")
    @Expose
    private Boolean isActive;
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
    @SerializedName("user")
    @Expose
    private Integer user;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GigListResp() {
    }

    /**
     * 
     * @param scheduledTime
     * @param headerImage
     * @param name
     * @param description
     * @param id
     * @param isActive
     * @param user
     * @param url
     */
    public GigListResp(Integer id, String url, Boolean isActive, String name, String description, String scheduledTime, String headerImage, Integer user) {
        super();
        this.id = id;
        this.url = url;
        this.isActive = isActive;
        this.name = name;
        this.description = description;
        this.scheduledTime = scheduledTime;
        this.headerImage = headerImage;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

}
