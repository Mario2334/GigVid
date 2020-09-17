package com.android.gigvid.model.repository.networkRepo.loginsignup.pojo;

public class LoginRespStatus {

    private String token;
    private int status;

    public LoginRespStatus(String token, int status) {
        this.token = token;
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
