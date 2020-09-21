package com.android.gigvid.model.repository.dbRepo;


import android.content.Context;

import androidx.lifecycle.LiveData;

import com.android.gigvid.model.repository.dbRepo.dao.GigDao;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.GigListResp;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by Kavya P S on 18/09/20.
 */
public class DatabaseManager {
    private static DatabaseManager INSTANCE;
    private final GigDao mGigDao;

    public DatabaseManager(Context context) {
        mGigDao = GigVidDB.getGigVidDB(context).gigDao();
    }

    public static DatabaseManager getInstance(WeakReference<Context> contextWeakReference) {
        if (INSTANCE == null) {
            INSTANCE = new DatabaseManager(contextWeakReference.get());
        }
        return INSTANCE;
    }

    public LiveData<List<GigListResp>> getGigsList() {
        return mGigDao.getAll();
    }

    public List<Long> storeGigsList(List<GigListResp> gigs) {
        return mGigDao.deleteAndCreate(gigs);
    }

}
