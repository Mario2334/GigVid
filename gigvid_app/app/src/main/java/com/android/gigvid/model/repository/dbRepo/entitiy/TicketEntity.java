package com.android.gigvid.model.repository.dbRepo.entitiy;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Kavya P S on 18/09/20.
 */
@Entity(tableName = "ticket")
public class TicketEntity {
    @PrimaryKey
    public int mTransactionId;

    @ColumnInfo(name = "event_name")
    public String mEventName;

    @ColumnInfo(name="ticket_cost")
    public int mCost;

    @ColumnInfo(name="scheduled_time")
    public long mScheduledTime;
}
