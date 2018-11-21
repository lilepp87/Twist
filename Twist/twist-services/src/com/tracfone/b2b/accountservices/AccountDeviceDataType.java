
package com.tracfone.b2b.accountservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.phonecommontypes.DeviceIdType;
import com.tracfone.phonecommontypes.ESNStatusType;
import com.tracfone.serviceplancommontypes.ServicePlanType;


/**
 * <p>Java class for AccountDeviceDataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AccountDeviceDataType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="deviceIdentifier" type="{http://www.tracfone.com/PhoneCommonTypes}DeviceIdType"/>
 *         &lt;element name="deviceNickname" type="{http://www.tracfone.com/PhoneCommonTypes}DeviceNicknameType" minOccurs="0"/>
 *         &lt;element name="esnStatus" type="{http://www.tracfone.com/PhoneCommonTypes}ESNStatusType"/>
 *         &lt;element name="pendingOTA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="buyerIdentifier" type="{http://www.tracfone.com/OrganizationCommonTypes}BuyerIdentifierType" minOccurs="0"/>
 *         &lt;element name="orgName" type="{http://www.tracfone.com/OrganizationCommonTypes}OrganizationIdentifierType" minOccurs="0"/>
 *         &lt;element name="servicePlan" type="{http://www.tracfone.com/ServicePlanCommonTypes}ServicePlanType" minOccurs="0"/>
 *         &lt;element name="partNumber" type="{http://www.tracfone.com/CommonTypes}PartNumberType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountDeviceDataType", propOrder = {
    "deviceIdentifier",
    "deviceNickname",
    "esnStatus",
    "pendingOTA",
    "buyerIdentifier",
    "orgName",
    "servicePlan",
    "partNumber"
})
public class AccountDeviceDataType {

    @XmlElement(required = true)
    protected DeviceIdType deviceIdentifier;
    protected String deviceNickname;
    @XmlElement(required = true)
    protected ESNStatusType esnStatus;
    @XmlElement(required = true)
    protected String pendingOTA;
    protected String buyerIdentifier;
    protected String orgName;
    protected ServicePlanType servicePlan;
    @XmlElement(required = true)
    protected String partNumber;

    /**
     * Gets the value of the deviceIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link DeviceIdType }
     *     
     */
    public DeviceIdType getDeviceIdentifier() {
        return deviceIdentifier;
    }

    /**
     * Sets the value of the deviceIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeviceIdType }
     *     
     */
    public void setDeviceIdentifier(DeviceIdType value) {
        this.deviceIdentifier = value;
    }

    /**
     * Gets the value of the deviceNickname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeviceNickname() {
        return deviceNickname;
    }

    /**
     * Sets the value of the deviceNickname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeviceNickname(String value) {
        this.deviceNickname = value;
    }

    /**
     * Gets the value of the esnStatus property.
     * 
     * @return
     *     possible object is
     *     {@link ESNStatusType }
     *     
     */
    public ESNStatusType getEsnStatus() {
        return esnStatus;
    }

    /**
     * Sets the value of the esnStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link ESNStatusType }
     *     
     */
    public void setEsnStatus(ESNStatusType value) {
        this.esnStatus = value;
    }

    /**
     * Gets the value of the pendingOTA property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPendingOTA() {
        return pendingOTA;
    }

    /**
     * Sets the value of the pendingOTA property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPendingOTA(String value) {
        this.pendingOTA = value;
    }

    /**
     * Gets the value of the buyerIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBuyerIdentifier() {
        return buyerIdentifier;
    }

    /**
     * Sets the value of the buyerIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBuyerIdentifier(String value) {
        this.buyerIdentifier = value;
    }

    /**
     * Gets the value of the orgName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * Sets the value of the orgName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgName(String value) {
        this.orgName = value;
    }

    /**
     * Gets the value of the servicePlan property.
     * 
     * @return
     *     possible object is
     *     {@link ServicePlanType }
     *     
     */
    public ServicePlanType getServicePlan() {
        return servicePlan;
    }

    /**
     * Sets the value of the servicePlan property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServicePlanType }
     *     
     */
    public void setServicePlan(ServicePlanType value) {
        this.servicePlan = value;
    }

    /**
     * Gets the value of the partNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartNumber() {
        return partNumber;
    }

    /**
     * Sets the value of the partNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartNumber(String value) {
        this.partNumber = value;
    }

}
