
package com.tracfone.b2b.accountservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EsnQueryCriteria complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EsnQueryCriteria">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="esn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="min" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nickname" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="esnStatus" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="otaStatus" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="buyerId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="buyerType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="orgName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="planName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="planDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="esnPartNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EsnQueryCriteria", propOrder = {
    "esn",
    "min",
    "nickname",
    "esnStatus",
    "otaStatus",
    "buyerId",
    "buyerType",
    "orgName",
    "planName",
    "planDescription",
    "esnPartNumber"
})
public class EsnQueryCriteria {

    @XmlElement(required = true)
    protected String esn;
    @XmlElement(required = true)
    protected String min;
    @XmlElement(required = true)
    protected String nickname;
    @XmlElement(required = true)
    protected String esnStatus;
    @XmlElement(required = true)
    protected String otaStatus;
    @XmlElement(required = true)
    protected String buyerId;
    @XmlElement(required = true)
    protected String buyerType;
    @XmlElement(required = true)
    protected String orgName;
    @XmlElement(required = true)
    protected String planName;
    @XmlElement(required = true)
    protected String planDescription;
    @XmlElement(required = true)
    protected String esnPartNumber;

    /**
     * Gets the value of the esn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsn() {
        return esn;
    }

    /**
     * Sets the value of the esn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsn(String value) {
        this.esn = value;
    }

    /**
     * Gets the value of the min property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMin() {
        return min;
    }

    /**
     * Sets the value of the min property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMin(String value) {
        this.min = value;
    }

    /**
     * Gets the value of the nickname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Sets the value of the nickname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNickname(String value) {
        this.nickname = value;
    }

    /**
     * Gets the value of the esnStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsnStatus() {
        return esnStatus;
    }

    /**
     * Sets the value of the esnStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsnStatus(String value) {
        this.esnStatus = value;
    }

    /**
     * Gets the value of the otaStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtaStatus() {
        return otaStatus;
    }

    /**
     * Sets the value of the otaStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtaStatus(String value) {
        this.otaStatus = value;
    }

    /**
     * Gets the value of the buyerId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBuyerId() {
        return buyerId;
    }

    /**
     * Sets the value of the buyerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBuyerId(String value) {
        this.buyerId = value;
    }

    /**
     * Gets the value of the buyerType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBuyerType() {
        return buyerType;
    }

    /**
     * Sets the value of the buyerType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBuyerType(String value) {
        this.buyerType = value;
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
     * Gets the value of the planName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlanName() {
        return planName;
    }

    /**
     * Sets the value of the planName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlanName(String value) {
        this.planName = value;
    }

    /**
     * Gets the value of the planDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlanDescription() {
        return planDescription;
    }

    /**
     * Sets the value of the planDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlanDescription(String value) {
        this.planDescription = value;
    }

    /**
     * Gets the value of the esnPartNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsnPartNumber() {
        return esnPartNumber;
    }

    /**
     * Sets the value of the esnPartNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsnPartNumber(String value) {
        this.esnPartNumber = value;
    }

}
