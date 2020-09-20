package com.android.gigvid.model.repository.dbRepo;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.android.gigvid.model.repository.dbRepo.dao.GigDao;
import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.GigListResp;

/**
 * Created by Kavya P S on 17/09/20.
 */
@Database(entities = {GigListResp.class}, version = 1)
public abstract class GigVidDB extends RoomDatabase {
    public abstract GigDao gigDao();

    private static volatile GigVidDB INSTANCE = null;

    public static GigVidDB getGigVidDB(Context context) {
        GigVidDB tempInstance = INSTANCE;
        if (tempInstance != null) {
            return tempInstance;
        }
        synchronized (GigVidDB.class) {
            GigVidDB instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    GigVidDB.class,
                    "gigvid_database"
            ).build();
            INSTANCE = instance;
            return instance;
        }
    }
}
