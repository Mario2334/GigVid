package com.android.gigvid.model.repository.dbRepo;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.android.gigvid.model.repository.dbRepo.dao.EventDao;
import com.android.gigvid.model.repository.dbRepo.entitiy.EventEntity;

/**
 * Created by Kavya P S on 17/09/20.
 */
@Database(entities = {EventEntity.class}, version = 1)
abstract class GigVidDB extends RoomDatabase {
    public abstract EventDao eventDao();
}
