
package com.android.gigvid.model.repository.networkRepo.homeScreen.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GigListResp {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("is_active")
    @Expose
    private Boolean isActive;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("join_url")
    @Expose
    private String joinUrl;
    @SerializedName("host_url")
    @Expose
    private String hostUrl;
    @SerializedName("scheduled_time")
    @Expose
    private String scheduledTime;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("header_image")
    @Expose
    private String headerImage;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
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
     * @param duration
     * @param createdAt
     * @param scheduledTime
     * @param headerImage
     * @param name
     * @param description
     * @param id
     * @param hostUrl
     * @param isActive
     * @param joinUrl
     * @param user
     */
    public GigListResp(Integer id, Boolean isActive, String name, String description, String joinUrl, String hostUrl, String scheduledTime, Integer duration, String headerImage, String createdAt, Integer user) {
        super();
        this.id = id;
        this.isActive = isActive;
        this.name = name;
        this.description = description;
        this.joinUrl = joinUrl;
        this.hostUrl = hostUrl;
        this.scheduledTime = scheduledTime;
        this.duration = duration;
        this.headerImage = headerImage;
        this.createdAt = createdAt;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getJoinUrl() {
        return joinUrl;
    }

    public void setJoinUrl(String joinUrl) {
        this.joinUrl = joinUrl;
    }

    public String getHostUrl() {
        return hostUrl;
    }

    public void setHostUrl(String hostUrl) {
        this.hostUrl = hostUrl;
    }

    public String getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(String scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getHeaderImage() {
        return headerImage;
    }

    public void setHeaderImage(String headerImage) {
        this.headerImage = headerImage;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }
}
