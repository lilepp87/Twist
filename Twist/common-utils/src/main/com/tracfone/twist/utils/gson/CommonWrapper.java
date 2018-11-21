
package com.tracfone.twist.utils.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommonWrapper {

    @SerializedName("common")
    @Expose
    private Common common;
    @SerializedName("request")
    @Expose
    private Request request;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CommonWrapper(String brand) {
    	common = new Common(brand);
    }

    /**
     * 
     * @param common
     * @param request
     */
    public CommonWrapper(String brand, Request request) {
        super();
        common = new Common(brand);
        this.request = request;
    }

    public Common getCommon() {
        return common;
    }

    public void setCommon(Common common) {
        this.common = common;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
    
}
