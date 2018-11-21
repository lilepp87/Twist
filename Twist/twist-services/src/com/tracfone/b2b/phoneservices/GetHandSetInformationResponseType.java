
package com.tracfone.b2b.phoneservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.BaseResponseType;


/**
 * <p>Java class for GetHandSetInformationResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetHandSetInformationResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/CommonTypes}BaseResponseType">
 *       &lt;sequence>
 *         &lt;element name="billingDirection" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="resolutionURL" type="{http://www.tracfone.com/CommonTypes}URLType" minOccurs="0"/>
 *         &lt;element name="deliveryMethod" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="manufacturerID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="phoneModel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="partClass" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ppeEnabled" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="toolkitVersion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="deviceOS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="backboneCarrier" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="conversionFactor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="accountID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dealerID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dueDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="brandName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="chargeUnitType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="associatedAccount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetHandSetInformationResponseType", propOrder = {
    "billingDirection",
    "resolutionURL",
    "deliveryMethod",
    "manufacturerID",
    "phoneModel",
    "partClass",
    "ppeEnabled",
    "toolkitVersion",
    "deviceOS",
    "backboneCarrier",
    "conversionFactor",
    "accountID",
    "dealerID",
    "dueDate",
    "brandName",
    "chargeUnitType",
    "associatedAccount"
})
public class GetHandSetInformationResponseType
    extends BaseResponseType
{

    protected String billingDirection;
    protected String resolutionURL;
    protected String deliveryMethod;
    protected String manufacturerID;
    protected String phoneModel;
    protected String partClass;
    protected String ppeEnabled;
    protected String toolkitVersion;
    protected String deviceOS;
    protected String backboneCarrier;
    protected String conversionFactor;
    protected String accountID;
    protected String dealerID;
    protected String dueDate;
    @XmlElement(required = true)
    protected String brandName;
    @XmlElement(required = true)
    protected String chargeUnitType;
    @XmlElement(required = true)
    protected String associatedAccount;

    /**
     * Gets the value of the billingDirection property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillingDirection() {
        return billingDirection;
    }

    /**
     * Sets the value of the billingDirection property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillingDirection(String value) {
        this.billingDirection = value;
    }

    /**
     * Gets the value of the resolutionURL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResolutionURL() {
        return resolutionURL;
    }

    /**
     * Sets the value of the resolutionURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResolutionURL(String value) {
        this.resolutionURL = value;
    }

    /**
     * Gets the value of the deliveryMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    /**
     * Sets the value of the deliveryMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliveryMethod(String value) {
        this.deliveryMethod = value;
    }

    /**
     * Gets the value of the manufacturerID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getManufacturerID() {
        return manufacturerID;
    }

    /**
     * Sets the value of the manufacturerID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setManufacturerID(String value) {
        this.manufacturerID = value;
    }

    /**
     * Gets the value of the phoneModel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhoneModel() {
        return phoneModel;
    }

    /**
     * Sets the value of the phoneModel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhoneModel(String value) {
        this.phoneModel = value;
    }

    /**
     * Gets the value of the partClass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartClass() {
        return partClass;
    }

    /**
     * Sets the value of the partClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartClass(String value) {
        this.partClass = value;
    }

    /**
     * Gets the value of the ppeEnabled property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPpeEnabled() {
        return ppeEnabled;
    }

    /**
     * Sets the value of the ppeEnabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPpeEnabled(String value) {
        this.ppeEnabled = value;
    }

    /**
     * Gets the value of the toolkitVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToolkitVersion() {
        return toolkitVersion;
    }

    /**
     * Sets the value of the toolkitVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToolkitVersion(String value) {
        this.toolkitVersion = value;
    }

    /**
     * Gets the value of the deviceOS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeviceOS() {
        return deviceOS;
    }

    /**
     * Sets the value of the deviceOS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeviceOS(String value) {
        this.deviceOS = value;
    }

    /**
     * Gets the value of the backboneCarrier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBackboneCarrier() {
        return backboneCarrier;
    }

    /**
     * Sets the value of the backboneCarrier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBackboneCarrier(String value) {
        this.backboneCarrier = value;
    }

    /**
     * Gets the value of the conversionFactor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConversionFactor() {
        return conversionFactor;
    }

    /**
     * Sets the value of the conversionFactor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConversionFactor(String value) {
        this.conversionFactor = value;
    }

    /**
     * Gets the value of the accountID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountID() {
        return accountID;
    }

    /**
     * Sets the value of the accountID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountID(String value) {
        this.accountID = value;
    }

    /**
     * Gets the value of the dealerID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDealerID() {
        return dealerID;
    }

    /**
     * Sets the value of the dealerID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDealerID(String value) {
        this.dealerID = value;
    }

    /**
     * Gets the value of the dueDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDueDate() {
        return dueDate;
    }

    /**
     * Sets the value of the dueDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDueDate(String value) {
        this.dueDate = value;
    }

    /**
     * Gets the value of the brandName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBrandName() {
        return brandName;
    }

    /**
     * Sets the value of the brandName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBrandName(String value) {
        this.brandName = value;
    }

    /**
     * Gets the value of the chargeUnitType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChargeUnitType() {
        return chargeUnitType;
    }

    /**
     * Sets the value of the chargeUnitType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChargeUnitType(String value) {
        this.chargeUnitType = value;
    }

    /**
     * Gets the value of the associatedAccount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssociatedAccount() {
        return associatedAccount;
    }

    /**
     * Sets the value of the associatedAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssociatedAccount(String value) {
        this.associatedAccount = value;
    }

}
