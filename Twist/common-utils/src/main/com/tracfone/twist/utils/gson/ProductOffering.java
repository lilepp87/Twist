
package com.tracfone.twist.utils.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductOffering {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("productSpecification")
    @Expose
    private ProductSpecification productSpecification;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ProductOffering() {
    }

    /**
     * 
     * @param id
     * @param name
     * @param productSpecification
     */
    public ProductOffering(String id, String name, ProductSpecification productSpecification) {
        super();
        this.id = id;
        this.name = name;
        this.productSpecification = productSpecification;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductSpecification getProductSpecification() {
        return productSpecification;
    }

    public void setProductSpecification(ProductSpecification productSpecification) {
        this.productSpecification = productSpecification;
    }

}
