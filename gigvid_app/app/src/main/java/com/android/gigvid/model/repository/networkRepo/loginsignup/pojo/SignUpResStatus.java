package com.android.gigvid.model.repository.networkRepo.loginsignup.pojo;

public class SignUpResStatus {
    private String username;
    private String email;

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    private int status;
    private String errMsg;

    public SignUpResStatus(String username, String email, int status, String errMsg) {
        this.username = username;
        this.email = email;
        this.status = status;
        this.errMsg = errMsg;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
