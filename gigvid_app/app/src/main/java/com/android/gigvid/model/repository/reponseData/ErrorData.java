package com.android.gigvid.model.repository.reponseData;

/**
 * Created by Kavya P S on 19/09/20.
 */
public class ErrorData {
    @StateDefinition.ErrorState
    private int mErrorStatus;

    private String mErrorMsg;

    public ErrorData(@StateDefinition.ErrorState int mErrorStatus, String mErrorMsg) {
        this.mErrorStatus = mErrorStatus;
        this.mErrorMsg = mErrorMsg;
    }

    @StateDefinition.ErrorState
    public int getErrorStatus() {
        return mErrorStatus;
    }

    public void setErrorStatus(@StateDefinition.ErrorState int mErrorStatus) {
        this.mErrorStatus = mErrorStatus;
    }

    public String getErrorMsg() {
        return mErrorMsg;
    }

    public void setErrorMsg(String mErrorMsg) {
        this.mErrorMsg = mErrorMsg;
    }

}
