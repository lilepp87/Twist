
package com.tracfone.twist.utils.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostalAddress {

    @SerializedName("zipcode")
    @Expose
    private String zipcode;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PostalAddress() {
    }

    /**
     * 
     * @param zipcode
     */
    public PostalAddress(String zipcode) {
        super();
        this.zipcode = zipcode;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

}
