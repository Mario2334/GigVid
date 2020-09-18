package com.android.gigvid.model.repository.reponseData;

import java.util.List;

/**
 * Created by Kavya P S on 19/09/20.
 */
public class ListResponse<T> {

    @StateDefinition.State
    private int mStatus;

    private List<T> mData;

    private ErrorData mError;

    @StateDefinition.State
    public int getStatus() {
        return mStatus;
    }

    public void setStatus(@StateDefinition.State int mStatus) {
        this.mStatus = mStatus;
    }

    public List<T> getData() {
        return mData;
    }

    public void setData(List<T> mData) {
        this.mData = mData;
    }

    public ErrorData getError() {
        return mError;
    }

    public void setError(ErrorData error) {
        this.mError = error;
    }
}
