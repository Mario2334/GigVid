package com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.profile;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Kavya P S on 21/09/20.
 */
public class BankDetailsReqBody {
    @SerializedName("name")
    public String mName;

    @SerializedName("ifsc")
    public String mIfsc;

    @SerializedName("account_no")
    public String mAccountNo;

    public BankDetailsReqBody(String mName, String mIfsc, String mAccountNo) {
        this.mName = mName;
        this.mIfsc = mIfsc;
        this.mAccountNo = mAccountNo;
    }
}

/*
{
        "name":"Gaurav Kumar",
        "ifsc":"HDFC0000053",
        "account_no":"765432123456789"
        }*/
