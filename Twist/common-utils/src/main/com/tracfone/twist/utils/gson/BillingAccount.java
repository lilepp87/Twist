
package com.tracfone.twist.utils.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BillingAccount {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("paymentPlan")
    @Expose
    private PaymentPlan paymentPlan;
    
    public static BillingAddress defaultAddress = new BillingAddress("1295 Charleston Road", null, "Mountain View", "CA", "94043", "USA");

    /**
     * No args constructor for use in serialization
     * 
     */
    public BillingAccount() {
    }

    /**
     * 
     * @param id
     * @param paymentPlan
     */
    public BillingAccount(boolean isRecurring, boolean savePayment, boolean isDefault, String firstName, String lastName, CreditCard creditcard) {
        super();
//        this.id = banid;
        this.paymentPlan = new PaymentPlan(isRecurring, new PaymentMean(isDefault, savePayment, firstName, lastName, creditcard, defaultAddress));
    }
    
    public BillingAccount(boolean isRecurring, boolean savePayment, boolean isDefault, String firstName, String lastName, BankAccount bank) {
        super();
//        this.id = banid;
        this.paymentPlan = new PaymentPlan(isRecurring, new PaymentMean(isDefault, savePayment, firstName, lastName, bank, defaultAddress));
    }
    
    public BillingAccount(boolean isRecurring, boolean savePayment, boolean isDefault, String firstName, String lastName, String paymentSource) {
        super();
//        this.id = banid;
        this.paymentPlan = new PaymentPlan(isRecurring, new PaymentMean(paymentSource));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PaymentPlan getPaymentPlan() {
        return paymentPlan;
    }

    public void setPaymentPlan(PaymentPlan paymentPlan) {
        this.paymentPlan = paymentPlan;
    }

}
