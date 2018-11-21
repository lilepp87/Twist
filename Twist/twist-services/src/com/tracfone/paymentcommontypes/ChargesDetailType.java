
package com.tracfone.paymentcommontypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ChargesDetailType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ChargesDetailType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="taxAmount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="rcrAmount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="combsAmount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="billingZipCodeForTaxCalc" type="{http://www.tracfone.com/CommonTypes}ZipCodeType" minOccurs="0"/>
 *         &lt;element name="totalAmount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="e911Amount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="discountAmount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="usfAmount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChargesDetailType", propOrder = {
    "amount",
    "taxAmount",
    "rcrAmount",
    "combsAmount",
    "billingZipCodeForTaxCalc",
    "totalAmount",
    "e911Amount",
    "discountAmount",
    "usfAmount"
})
public class ChargesDetailType {

    @XmlElement(required = true)
    protected String amount;
    @XmlElement(required = true)
    protected String taxAmount;
    @XmlElement(required = true)
    protected String rcrAmount;
    @XmlElement(required = true)
    protected String combsAmount;
    protected String billingZipCodeForTaxCalc;
    @XmlElement(required = true)
    protected String totalAmount;
    @XmlElement(required = true)
    protected String e911Amount;
    @XmlElement(required = true)
    protected String discountAmount;
    @XmlElement(required = true)
    protected String usfAmount;

    /**
     * Gets the value of the amount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmount(String value) {
        this.amount = value;
    }

    /**
     * Gets the value of the taxAmount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxAmount() {
        return taxAmount;
    }

    /**
     * Sets the value of the taxAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxAmount(String value) {
        this.taxAmount = value;
    }

    /**
     * Gets the value of the rcrAmount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRcrAmount() {
        return rcrAmount;
    }

    /**
     * Sets the value of the rcrAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRcrAmount(String value) {
        this.rcrAmount = value;
    }

    /**
     * Gets the value of the combsAmount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCombsAmount() {
        return combsAmount;
    }

    /**
     * Sets the value of the combsAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCombsAmount(String value) {
        this.combsAmount = value;
    }

    /**
     * Gets the value of the billingZipCodeForTaxCalc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBillingZipCodeForTaxCalc() {
        return billingZipCodeForTaxCalc;
    }

    /**
     * Sets the value of the billingZipCodeForTaxCalc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBillingZipCodeForTaxCalc(String value) {
        this.billingZipCodeForTaxCalc = value;
    }

    /**
     * Gets the value of the totalAmount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTotalAmount() {
        return totalAmount;
    }

    /**
     * Sets the value of the totalAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTotalAmount(String value) {
        this.totalAmount = value;
    }

    /**
     * Gets the value of the e911Amount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getE911Amount() {
        return e911Amount;
    }

    /**
     * Sets the value of the e911Amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setE911Amount(String value) {
        this.e911Amount = value;
    }

    /**
     * Gets the value of the discountAmount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiscountAmount() {
        return discountAmount;
    }

    /**
     * Sets the value of the discountAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiscountAmount(String value) {
        this.discountAmount = value;
    }

    /**
     * Gets the value of the usfAmount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsfAmount() {
        return usfAmount;
    }

    /**
     * Sets the value of the usfAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsfAmount(String value) {
        this.usfAmount = value;
    }

}
