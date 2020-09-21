package com.android.gigvid.model;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.android.gigvid.model.repository.dbRepo.DatabaseManager;

import java.lang.ref.WeakReference;

/**
 * Created by Kavya P S on 21/09/20.
 */
public class BackupService extends IntentService {
    private static DataRepository mDataRepository;

    public BackupService() {
        super("BackupService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mDataRepository = DataRepository.getInstance(new WeakReference<>(getApplicationContext()));

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        mDataRepository.cacheContents();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDataRepository = null;
    }
}
