
package com.tracfone.twist.utils.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceSpecification {

    @SerializedName("brand")
    @Expose
    private String brand;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ServiceSpecification() {
    }

    /**
     * 
     * @param brand
     */
    public ServiceSpecification(String brand) {
        super();
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

}
