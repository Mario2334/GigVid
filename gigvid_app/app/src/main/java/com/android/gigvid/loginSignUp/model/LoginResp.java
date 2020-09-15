package com.android.gigvid.loginSignUp.model;

public class LoginResp {

    private String token;
    private int status;

    public LoginResp(String token, int status) {
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
