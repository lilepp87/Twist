
package com.tracfone.twist.utils.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BankAccount {

    @SerializedName("accountNumber")
    @Expose
    private String accountNumber;
    @SerializedName("routingNumber")
    @Expose
    private String routingNumber;
    @SerializedName("type")
    @Expose
    private String type;

    /**
     * No args constructor for use in serialization
     * 
     */
    public BankAccount() {
    }

    /**
     * 
     * @param accountNumber
     * @param type
     * @param routingNumber
     */
    public BankAccount(String accountNumber, String routingNumber, String type) {
        super();
        this.accountNumber = accountNumber;
        this.routingNumber = routingNumber;
        this.type = type;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getRoutingNumber() {
        return routingNumber;
    }

    public void setRoutingNumber(String routingNumber) {
        this.routingNumber = routingNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
