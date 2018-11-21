
package com.tracfone.twist.utils.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tracfone.twist.utils.TwistUtils;

public class Common {

    @SerializedName("brandName")
    @Expose
    private String brandName;
    @SerializedName("clientAppName")
    @Expose
    private String clientAppName;
    @SerializedName("clientAppType")
    @Expose
    private String clientAppType;
    @SerializedName("clientAppVersion")
    @Expose
    private String clientAppVersion;
    @SerializedName("deviceModel")
    @Expose
    private String deviceModel;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("sourceSystem")
    @Expose
    private String sourceSystem;
    @SerializedName("OSVersion")
    @Expose
    private String oSVersion;
    @SerializedName("partyID")
    @Expose
    private String partyID;    
    @SerializedName("partyTransactionID")
    @Expose
    private String partyTransactionID;


    /**
     * No args constructor for use in serialization
     * 
     */
    public Common(String brand) {
    	brandName = brand;
    	language = "ENG";
    	sourceSystem = "WEB";
    	partyTransactionID = Long.toString(TwistUtils.createRandomLong(1000, 99999));
//    	 Misc Wrong Info
//		 clientAppName = "com.tracfone.wfm.myaccount";
//		 clientAppType = "FULL";
//		 clientAppVersion = "999";
//		 deviceModel = "samsung_SM-G925F";
//		 oSVersion = "android_23";
//		 partyID = "WALMART";
//		 partyTransactionID = "1234";
    }

    /**
     * 
     * @param clientAppType
     * @param sourceSystem
     * @param deviceModel
     * @param clientAppVersion
     * @param language
     * @param brandName
     * @param oSVersion
     * @param clientAppName
     */
    public Common(String brandName, String clientAppName, String clientAppType, String clientAppVersion, String deviceModel, String language, String sourceSystem, String oSVersion) {
        super();
        this.brandName = brandName;
        this.clientAppName = clientAppName;
        this.clientAppType = clientAppType;
        this.clientAppVersion = clientAppVersion;
        this.deviceModel = deviceModel;
        this.language = language;
        this.sourceSystem = sourceSystem;
        this.oSVersion = oSVersion;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getClientAppName() {
        return clientAppName;
    }

    public void setClientAppName(String clientAppName) {
        this.clientAppName = clientAppName;
    }

    public String getClientAppType() {
        return clientAppType;
    }

    public void setClientAppType(String clientAppType) {
        this.clientAppType = clientAppType;
    }

    public String getClientAppVersion() {
        return clientAppVersion;
    }

    public void setClientAppVersion(String clientAppVersion) {
        this.clientAppVersion = clientAppVersion;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSourceSystem() {
        return sourceSystem;
    }

    public void setSourceSystem(String sourceSystem) {
        this.sourceSystem = sourceSystem;
    }

    public String getOSVersion() {
        return oSVersion;
    }

    public void setOSVersion(String oSVersion) {
        this.oSVersion = oSVersion;
    }

}
