
package com.tracfone.serviceplancommontypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PlanPurchaseDetailInfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PlanPurchaseDetailInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="planDescription">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="255"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="purchaseAmount" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="taxAmount" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="e911Fee" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="usfCharge" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="rcrFee" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="totalAmount" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="isAutoRefill" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PlanPurchaseDetailInfoType", propOrder = {
    "planDescription",
    "purchaseAmount",
    "taxAmount",
    "e911Fee",
    "usfCharge",
    "rcrFee",
    "totalAmount",
    "isAutoRefill"
})
public class PlanPurchaseDetailInfoType {

    @XmlElement(required = true)
    protected String planDescription;
    protected float purchaseAmount;
    protected float taxAmount;
    protected float e911Fee;
    protected float usfCharge;
    protected float rcrFee;
    protected float totalAmount;
    protected boolean isAutoRefill;

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
     * Gets the value of the purchaseAmount property.
     * 
     */
    public float getPurchaseAmount() {
        return purchaseAmount;
    }

    /**
     * Sets the value of the purchaseAmount property.
     * 
     */
    public void setPurchaseAmount(float value) {
        this.purchaseAmount = value;
    }

    /**
     * Gets the value of the taxAmount property.
     * 
     */
    public float getTaxAmount() {
        return taxAmount;
    }

    /**
     * Sets the value of the taxAmount property.
     * 
     */
    public void setTaxAmount(float value) {
        this.taxAmount = value;
    }

    /**
     * Gets the value of the e911Fee property.
     * 
     */
    public float getE911Fee() {
        return e911Fee;
    }

    /**
     * Sets the value of the e911Fee property.
     * 
     */
    public void setE911Fee(float value) {
        this.e911Fee = value;
    }

    /**
     * Gets the value of the usfCharge property.
     * 
     */
    public float getUsfCharge() {
        return usfCharge;
    }

    /**
     * Sets the value of the usfCharge property.
     * 
     */
    public void setUsfCharge(float value) {
        this.usfCharge = value;
    }

    /**
     * Gets the value of the rcrFee property.
     * 
     */
    public float getRcrFee() {
        return rcrFee;
    }

    /**
     * Sets the value of the rcrFee property.
     * 
     */
    public void setRcrFee(float value) {
        this.rcrFee = value;
    }

    /**
     * Gets the value of the totalAmount property.
     * 
     */
    public float getTotalAmount() {
        return totalAmount;
    }

    /**
     * Sets the value of the totalAmount property.
     * 
     */
    public void setTotalAmount(float value) {
        this.totalAmount = value;
    }

    /**
     * Gets the value of the isAutoRefill property.
     * 
     */
    public boolean isIsAutoRefill() {
        return isAutoRefill;
    }

    /**
     * Sets the value of the isAutoRefill property.
     * 
     */
    public void setIsAutoRefill(boolean value) {
        this.isAutoRefill = value;
    }

}
