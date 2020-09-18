package com.android.gigvid.model.repository.networkRepo.homeScreen.pojo;

import java.util.List;

public class GigListRespStatus {

    private List<GigListResp> gigListResp;
    private int status;
    private String errMsg;

    public GigListRespStatus(List<GigListResp> gigListResp, int status, String errMsg) {
        this.gigListResp = gigListResp;
        this.status = status;
        this.errMsg = errMsg;
    }

    public List<GigListResp> getGigListResp() {
        return gigListResp;
    }

    public void setGigListResp(List<GigListResp> gigListResp) {
        this.gigListResp = gigListResp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
