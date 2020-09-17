package com.android.gigvid.model.repository.dbRepo.entitiy;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Kavya P S on 18/09/20.
 */

@Entity(tableName = "event")
public class EventEntity {
    @PrimaryKey
    public int mEventId;

    @ColumnInfo(name = "event_name")
    public String mEventName;

    @ColumnInfo(name = "host_username")
    public String mHostUsername;

    @ColumnInfo(name="ticket_cost")
    public int mCost;

    @ColumnInfo(name="scheduled_time")
    public long mScheduledTime;

    @ColumnInfo(name="participant_count")
    public int mParticipantCount;

    @ColumnInfo(name="max_participant")
    public int mMaxParticipant;
}