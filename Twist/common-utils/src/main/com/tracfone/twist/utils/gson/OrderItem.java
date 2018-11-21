
package com.tracfone.twist.utils.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderItem {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("action")
    @Expose
    private String action;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("productOffering")
    @Expose
    private ProductOffering productOffering;
    @SerializedName("product")
    @Expose
    private Product product;
    @SerializedName("location")
    @Expose
    private Location location;

    /**
     * No args constructor for use in serialization
     * 
     */
    public OrderItem() {
    }

    /**
     * 
     * @param product
     * @param id
     * @param location
     * @param action
     * @param quantity
     * @param productOffering
     */
    public OrderItem(int id, String action, String quantity, ProductOffering productOffering, Product product, String zipCode) {
        super();
        this.id = Integer.toString(id);
        this.action = action;
        this.quantity = quantity;
        this.productOffering = productOffering;
        this.product = product;
        this.location = new Location(new PostalAddress(zipCode));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public ProductOffering getProductOffering() {
        return productOffering;
    }

    public void setProductOffering(ProductOffering productOffering) {
        this.productOffering = productOffering;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

}
