package com.android.gigvid.model.repository.reponseData;

/**
 * Created by Kavya P S on 19/09/20.
 */
public class DataResponse<T> {

    @StateDefinition.State
    private int mStatus;

    private T mData;

    private ErrorData mError;

    public DataResponse(@StateDefinition.State int status, T data, ErrorData errorData) {
        mStatus = status;
        mData = data;
        mError = errorData;
    }

    public int getStatus() {
        return mStatus;
    }

    public void setStatus(int mStatus) {
        this.mStatus = mStatus;
    }

    public T getData() {
        return mData;
    }

    public void setData(T mData) {
        this.mData = mData;
    }

    public ErrorData getError() {
        return mError;
    }

    public void setError(ErrorData error) {
        this.mError = error;
    }
}
