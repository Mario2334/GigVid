package com.android.gigvid.model.repository.dbRepo.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.GigListResp;

import java.util.List;

/**
 * Created by Kavya P S on 18/09/20.
 */
@Dao
public interface GigDao {
    //    To be changed as per data & also add LiveData logic
    @Query("SELECT * FROM gig")
    LiveData<List<GigListResp>> getAll();

    @Query("SELECT * FROM gig WHERE id IN (:eventIds)")
    List<GigListResp> loadAllByIds(long[] eventIds);

    @Insert
    void insertAll(GigListResp... events);

}