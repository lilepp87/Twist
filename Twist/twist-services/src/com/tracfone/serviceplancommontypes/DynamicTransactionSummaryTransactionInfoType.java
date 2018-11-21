
package com.tracfone.serviceplancommontypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DynamicTransactionSummaryTransactionInfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DynamicTransactionSummaryTransactionInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="transactionTotal" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="transactionTotalTax" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="transactionId" type="{http://www.tracfone.com/CommonTypes}ObjectIdType"/>
 *         &lt;element name="transactionDate" type="{http://www.tracfone.com/CommonTypes}StringDatetype"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DynamicTransactionSummaryTransactionInfoType", propOrder = {
    "transactionTotal",
    "transactionTotalTax",
    "transactionId",
    "transactionDate"
})
public class DynamicTransactionSummaryTransactionInfoType {

    protected double transactionTotal;
    protected double transactionTotalTax;
    @XmlElement(required = true)
    protected String transactionId;
    @XmlElement(required = true)
    protected String transactionDate;

    /**
     * Gets the value of the transactionTotal property.
     * 
     */
    public double getTransactionTotal() {
        return transactionTotal;
    }

    /**
     * Sets the value of the transactionTotal property.
     * 
     */
    public void setTransactionTotal(double value) {
        this.transactionTotal = value;
    }

    /**
     * Gets the value of the transactionTotalTax property.
     * 
     */
    public double getTransactionTotalTax() {
        return transactionTotalTax;
    }

    /**
     * Sets the value of the transactionTotalTax property.
     * 
     */
    public void setTransactionTotalTax(double value) {
        this.transactionTotalTax = value;
    }

    /**
     * Gets the value of the transactionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransactionId() {
        return transactionId;
    }

    /**
     * Sets the value of the transactionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransactionId(String value) {
        this.transactionId = value;
    }

    /**
     * Gets the value of the transactionDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransactionDate() {
        return transactionDate;
    }

    /**
     * Sets the value of the transactionDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransactionDate(String value) {
        this.transactionDate = value;
    }

}
