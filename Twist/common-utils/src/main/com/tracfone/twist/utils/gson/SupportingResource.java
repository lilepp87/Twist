
package com.tracfone.twist.utils.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SupportingResource {

    @SerializedName("resourceType")
    @Expose
    private String resourceType;
    @SerializedName("serialNumber")
    @Expose
    private String serialNumber;

    /**
     * No args constructor for use in serialization
     * 
     */
    public SupportingResource() {
    }

    /**
     * 
     * @param serialNumber
     * @param resourceType
     */
    public SupportingResource(String resourceType, String serialNumber) {
        super();
        this.resourceType = resourceType;
        this.serialNumber = serialNumber;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

}
