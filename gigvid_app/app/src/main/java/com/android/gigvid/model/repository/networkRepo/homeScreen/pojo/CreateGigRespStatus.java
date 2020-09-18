package com.android.gigvid.model.repository.networkRepo.homeScreen.pojo;

public class CreateGigRespStatus {
    private CreateGigResp createGigResp;
    private int status;

    public CreateGigResp getCreateGigResp() {
        return createGigResp;
    }

    public void setCreateGigResp(CreateGigResp createGigResp) {
        this.createGigResp = createGigResp;
    }

    public int getStatus() {
        return status;
    }

    public CreateGigRespStatus(CreateGigResp createGigResp, int status) {
        this.createGigResp = createGigResp;
        this.status = status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
