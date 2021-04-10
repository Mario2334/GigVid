package com.android.gigvid.view.homescreen;

import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.GigListResp;

public interface AdapterEventCommunicator {

    void buyBtnClickEvent(GigListResp gigId);

    void joinEventBtnClick(String joinUrl);

    void launchVideoPlayer(String meetingName);
}
