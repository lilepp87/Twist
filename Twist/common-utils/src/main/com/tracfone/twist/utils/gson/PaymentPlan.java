
package com.tracfone.twist.utils.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentPlan {

    @SerializedName("isRecurring")
    @Expose
    private Boolean isRecurring;
    @SerializedName("paymentMean")
    @Expose
    private PaymentMean paymentMean;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PaymentPlan() {
    }

    /**
     * 
     * @param isRecurring
     * @param paymentMean
     */
    public PaymentPlan(Boolean isRecurring, PaymentMean paymentMean) {
        super();
        this.isRecurring = isRecurring;
        this.paymentMean = paymentMean;
    }

    public Boolean getIsRecurring() {
        return isRecurring;
    }

    public void setIsRecurring(Boolean isRecurring) {
        this.isRecurring = isRecurring;
    }

    public PaymentMean getPaymentMean() {
        return paymentMean;
    }

    public void setPaymentMean(PaymentMean paymentMean) {
        this.paymentMean = paymentMean;
    }

}
