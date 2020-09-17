package com.android.gigvid.model.repository.dbRepo.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.android.gigvid.model.repository.dbRepo.entitiy.EventEntity;

import java.util.List;

/**
 * Created by Kavya P S on 18/09/20.
 */
@Dao
public interface EventDao {
//    To be changed as per data & also add LiveData logic
    @Query("SELECT * FROM event")
    List<EventEntity> getAll();

    @Query("SELECT * FROM event WHERE mEventId IN (:eventIds)")
    List<EventEntity> loadAllByIds(long[] eventIds);

    @Insert
    void insertAll(EventEntity... events);

}