
package com.android.gigvid.model.repository.networkRepo.homeScreen.pojo.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BankDetailResp {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("contact_id")
    @Expose
    private String contactId;
    @SerializedName("ifsc")
    @Expose
    private String ifsc;
    @SerializedName("account_no")
    @Expose
    private String accountNo;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("balance")
    @Expose
    private Integer balance;
    @SerializedName("fund_account_id")
    @Expose
    private String fundAccountId;
    @SerializedName("user")
    @Expose
    private Integer user;

    /**
     * No args constructor for use in serialization
     * 
     */
    public BankDetailResp() {
    }

    /**
     * 
     * @param balance
     * @param contactId
     * @param fundAccountId
     * @param accountNo
     * @param name
     * @param id
     * @param ifsc
     * @param user
     */
    public BankDetailResp(Integer id, String contactId, String ifsc, String accountNo, String name, Integer balance, String fundAccountId, Integer user) {
        super();
        this.id = id;
        this.contactId = contactId;
        this.ifsc = ifsc;
        this.accountNo = accountNo;
        this.name = name;
        this.balance = balance;
        this.fundAccountId = fundAccountId;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public String getFundAccountId() {
        return fundAccountId;
    }

    public void setFundAccountId(String fundAccountId) {
        this.fundAccountId = fundAccountId;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

}
