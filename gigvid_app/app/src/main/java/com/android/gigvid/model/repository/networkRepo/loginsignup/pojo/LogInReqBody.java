package com.android.gigvid.model.repository.networkRepo.loginsignup.pojo;

public class LogInReqBody {
    private String username;

    public LogInReqBody(String username, String password) {
        this.username = username;
        this.password = password;
    }

    private String password;
}
