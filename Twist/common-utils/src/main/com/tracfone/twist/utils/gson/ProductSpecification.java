
package com.tracfone.twist.utils.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductSpecification {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("brand")
    @Expose
    private String brand;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ProductSpecification() {
    }

    /**
     * 
     * @param id
     * @param brand
     */
    public ProductSpecification(String id, String brand) {
        super();
        this.id = id;
        this.brand = brand;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

}
