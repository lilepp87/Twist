
package com.tracfone.paymentcommontypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PurchaseTaxType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PurchaseTaxType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="e911Tax" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="rcrfTax" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="usfTax" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="salesTax" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="totalTaxAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="discountAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="totalCharges" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PurchaseTaxType", propOrder = {
    "e911Tax",
    "rcrfTax",
    "usfTax",
    "salesTax",
    "totalTaxAmount",
    "amount",
    "discountAmount",
    "totalCharges"
})
public class PurchaseTaxType {

    protected double e911Tax;
    protected double rcrfTax;
    protected double usfTax;
    protected double salesTax;
    protected double totalTaxAmount;
    protected double amount;
    protected double discountAmount;
    protected double totalCharges;

    /**
     * Gets the value of the e911Tax property.
     * 
     */
    public double getE911Tax() {
        return e911Tax;
    }

    /**
     * Sets the value of the e911Tax property.
     * 
     */
    public void setE911Tax(double value) {
        this.e911Tax = value;
    }

    /**
     * Gets the value of the rcrfTax property.
     * 
     */
    public double getRcrfTax() {
        return rcrfTax;
    }

    /**
     * Sets the value of the rcrfTax property.
     * 
     */
    public void setRcrfTax(double value) {
        this.rcrfTax = value;
    }

    /**
     * Gets the value of the usfTax property.
     * 
     */
    public double getUsfTax() {
        return usfTax;
    }

    /**
     * Sets the value of the usfTax property.
     * 
     */
    public void setUsfTax(double value) {
        this.usfTax = value;
    }

    /**
     * Gets the value of the salesTax property.
     * 
     */
    public double getSalesTax() {
        return salesTax;
    }

    /**
     * Sets the value of the salesTax property.
     * 
     */
    public void setSalesTax(double value) {
        this.salesTax = value;
    }

    /**
     * Gets the value of the totalTaxAmount property.
     * 
     */
    public double getTotalTaxAmount() {
        return totalTaxAmount;
    }

    /**
     * Sets the value of the totalTaxAmount property.
     * 
     */
    public void setTotalTaxAmount(double value) {
        this.totalTaxAmount = value;
    }

    /**
     * Gets the value of the amount property.
     * 
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     */
    public void setAmount(double value) {
        this.amount = value;
    }

    /**
     * Gets the value of the discountAmount property.
     * 
     */
    public double getDiscountAmount() {
        return discountAmount;
    }

    /**
     * Sets the value of the discountAmount property.
     * 
     */
    public void setDiscountAmount(double value) {
        this.discountAmount = value;
    }

    /**
     * Gets the value of the totalCharges property.
     * 
     */
    public double getTotalCharges() {
        return totalCharges;
    }

    /**
     * Sets the value of the totalCharges property.
     * 
     */
    public void setTotalCharges(double value) {
        this.totalCharges = value;
    }

}
