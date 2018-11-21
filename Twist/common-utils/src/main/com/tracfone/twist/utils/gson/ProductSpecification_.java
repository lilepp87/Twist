
package com.tracfone.twist.utils.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductSpecification_ {

    @SerializedName("brand")
    @Expose
    private String brand;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ProductSpecification_() {
    }

    /**
     * 
     * @param brand
     */
    public ProductSpecification_(String brand) {
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
