package com.android.gigvid.model.repository.networkRepo.homeScreen.pojo;

public class ScheduleDateTime {

    private String date;
    private String month;
    private String time;

    public ScheduleDateTime(String date, String month, String time) {
        this.date = date;
        this.month = month;
        this.time = time;
    }

    public ScheduleDateTime() {

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
