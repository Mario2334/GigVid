package com.android.gigvid.view.homescreen.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.android.gigvid.R;

import org.jitsi.meet.sdk.JitsiMeetActivity;

public class VideoPlayerActivity extends JitsiMeetActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
    }
}