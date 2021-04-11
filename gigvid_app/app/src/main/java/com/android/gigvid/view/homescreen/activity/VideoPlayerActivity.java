package com.android.gigvid.view.homescreen.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.android.gigvid.Constants;
import com.facebook.react.modules.core.PermissionListener;

import org.jitsi.meet.sdk.JitsiMeetActivityDelegate;
import org.jitsi.meet.sdk.JitsiMeetActivityInterface;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import org.jitsi.meet.sdk.JitsiMeetView;

import java.net.MalformedURLException;
import java.net.URL;

public class VideoPlayerActivity extends FragmentActivity implements JitsiMeetActivityInterface {
    private JitsiMeetView mView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String roomName = intent.getStringExtra("meetingName");

        mView = new JitsiMeetView(this);
        JitsiMeetConferenceOptions options = null;
        try {
            options = new JitsiMeetConferenceOptions.Builder()
                    .setServerURL(new URL("https://meet.jit.si"))
                    .setRoom(roomName + Constants.UNIQUE_MEETING_ID)
                    .setWelcomePageEnabled(false)
                    .build();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        mView.join(options);

        setContentView(mView);
    }

    @Override
    protected void onResume() {
        super.onResume();

        JitsiMeetActivityDelegate.onHostResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        JitsiMeetActivityDelegate.onHostPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mView.dispose();
        mView = null;

        JitsiMeetActivityDelegate.onHostDestroy(this);
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        JitsiMeetActivityDelegate.onActivityResult(this,
                requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        JitsiMeetActivityDelegate.onBackPressed();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        JitsiMeetActivityDelegate.onNewIntent(intent);
    }

    @Override
    public void onRequestPermissionsResult(
            final int requestCode,
            final String[] permissions,
            final int[] grantResults) {
        JitsiMeetActivityDelegate.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void requestPermissions(String[] permissions, int requestCode, PermissionListener permissionListener) {
        String[] PERMISSIONS = {
                Manifest.permission.RECORD_AUDIO,
                android.Manifest.permission.CAMERA
        };

        this.requestPermissions(permissions, requestCode);

        if (!hasPermissions(PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, 200);
        }
    }

    private boolean hasPermissions(String... permissions) {
        if (permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}