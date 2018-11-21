
package com.tracfone.twist.utils.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentMean {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("isDefault")
    @Expose
    private Boolean isDefault;
    @SerializedName("savePaymentInfo")
    @Expose
    private Boolean savePaymentInfo;
    @SerializedName("nickname")
    @Expose
    private String nickname;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("accountHolderName")
    @Expose
    private String accountHolderName;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("creditCard")
    @Expose
    private CreditCard creditCard;
	@SerializedName("bankAccount")
    @Expose
    private BankAccount bankAccount;
    @SerializedName("billingAddress")
    @Expose
    private BillingAddress billingAddress;


    /**
     * Use existing payment source
     * 
     */
    public PaymentMean(String paymentSource) {
    	this.id = paymentSource;
    }
    /**
     * Create a new credit card
     */
    public PaymentMean(Boolean isDefault, Boolean savePaymentInfo, String firstName, String lastName, CreditCard creditCard, BillingAddress billingAddress) {
        super();
        this.isDefault = isDefault;
        this.savePaymentInfo = savePaymentInfo;
        this.nickname = "NickNameCredit";
        this.type = "CREDITCARD";
        this.accountHolderName = firstName + " " + lastName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.creditCard = creditCard;
        this.billingAddress = billingAddress;
    }
    /**
     * Create a new Bank account
     */
    public PaymentMean(Boolean isDefault, Boolean savePaymentInfo, String firstName, String lastName, BankAccount bank, BillingAddress billingAddress) {
        super();
        this.isDefault = isDefault;
        this.savePaymentInfo = savePaymentInfo;
        this.nickname = "NickNameACH";
        this.type = "ACH";
        this.accountHolderName = firstName + " " + lastName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bankAccount = bank;
        this.billingAddress = billingAddress;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public Boolean getSavePaymentInfo() {
        return savePaymentInfo;
    }

    public void setSavePaymentInfo(Boolean savePaymentInfo) {
        this.savePaymentInfo = savePaymentInfo;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public BillingAddress getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(BillingAddress billingAddress) {
        this.billingAddress = billingAddress;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }
}
