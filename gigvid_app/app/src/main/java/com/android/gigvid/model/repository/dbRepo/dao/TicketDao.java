package com.android.gigvid.model.repository.dbRepo.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.android.gigvid.model.repository.dbRepo.entitiy.EventEntity;
import com.android.gigvid.model.repository.dbRepo.entitiy.TicketEntity;

import java.util.List;

/**
 * Created by Kavya P S on 18/09/20.
 */
@Dao
public interface TicketDao {
    //    To be changed as per data & also add LiveData logic
    @Query("SELECT * FROM ticket")
    List<EventEntity> getAll();

    @Query("SELECT * FROM ticket WHERE mTransactionId IN (:transactionIds)")
    List<EventEntity> loadAllByIds(long[] transactionIds);

    @Insert
    void insertAll(TicketEntity... tickets);

}
