package com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.payment;

public class Gig {
    private int id;

    private String url;

    public Gig(int id, String url, boolean is_active, String scheduled_time) {
        this.id = id;
        this.url = url;
        this.is_active = is_active;
        this.scheduled_time = scheduled_time;
    }

    private boolean is_active;

    private String scheduled_time;
}
