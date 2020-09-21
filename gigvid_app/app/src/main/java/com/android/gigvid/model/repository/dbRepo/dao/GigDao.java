package com.android.gigvid.model.repository.dbRepo.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.GigListResp;

import java.util.List;

import timber.log.Timber;

/**
 * Created by Kavya P S on 18/09/20.
 */
@Dao
public abstract class GigDao {
    //    To be changed as per data & also add LiveData logic
    @Query("SELECT * FROM gig")
    public abstract LiveData<List<GigListResp>> getAll();

    @Query("SELECT * FROM gig WHERE id IN (:eventIds)")
    public abstract List<GigListResp> loadAllByIds(long[] eventIds);

    @Transaction
    public List<Long> deleteAndCreate(List<GigListResp> gigs) {
        clearGigs();
        return insertAll(gigs);
    }

    @Query("DELETE FROM gig")
    public abstract void clearGigs();

    @Insert
    public abstract List<Long> insertAll(List<GigListResp> gigs);

}