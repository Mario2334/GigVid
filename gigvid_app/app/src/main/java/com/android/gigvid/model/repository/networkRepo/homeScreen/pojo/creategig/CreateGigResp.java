package com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.creategig;

public class CreateGigResp {
    private String message;

    public CreateGigResp(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
