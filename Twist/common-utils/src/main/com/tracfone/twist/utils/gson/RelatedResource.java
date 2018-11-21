
package com.tracfone.twist.utils.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RelatedResource {

    @SerializedName("serialNumber")
    @Expose
    private String serialNumber;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("type")
    @Expose
    private String type;

    /**
     * No args constructor for use in serialization
     * 
     */
    public RelatedResource() {
    }

    /**
     * 
     * @param name
     * @param serialNumber
     * @param type
     */
    public RelatedResource(String serialNumber, String name, String type) {
        super();
        this.serialNumber = serialNumber;
        this.name = name;
        this.type = type;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
