
package com.tracfone.twist.utils.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreditCard {

    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("month")
    @Expose
    private String month;
    @SerializedName("cvv")
    @Expose
    private String cvv;
    @SerializedName("accountNumber")
    @Expose
    private String accountNumber;
    @SerializedName("type")
    @Expose
    private String type;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CreditCard() {
    }

    /**
     * 
     * @param accountNumber
     * @param cvv
     * @param month
     * @param year
     * @param type
     */
    public CreditCard(String year, String month, String cvv, String accountNumber, String type) {
        super();
        this.year = year;
        this.month = month;
        this.cvv = cvv;
        this.accountNumber = accountNumber;
        this.type = type;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
