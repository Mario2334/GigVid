package com.android.gigvid.model.repository.state;

import androidx.annotation.IntDef;

/**
 * Created by Kavya P S on 18/09/20.
 */
public class Status {
    @IntDef( {State.LOADING,State.COMPLETED,State.ERROR})
    public  @interface  State{
        int LOADING = 0;
        int COMPLETED = 21;
        int ERROR = 42;
    }



    // Mark the argument as restricted to these enumerated types
    public void getItemType(@State int itemType) {
        int res = itemType;
    }
}