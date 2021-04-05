package com.android.gigvid.view.homescreen;

public interface AdapterEventCommunicator {

    void buyBtnClickEvent(int gigId);

    void joinEventBtnClick(String joinUrl);

    void launchVideoPlayer(String meetingName);
}
