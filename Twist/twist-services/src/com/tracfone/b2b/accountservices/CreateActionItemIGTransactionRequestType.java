
package com.tracfone.b2b.accountservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreateActionItemIGTransactionRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreateActionItemIGTransactionRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="contactObjId" type="{http://www.tracfone.com/CommonTypes}ObjectIdType"/>
 *         &lt;element name="callTransObjId" type="{http://www.tracfone.com/CommonTypes}ObjectIdType"/>
 *         &lt;element name="orderType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="byPassOrderType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="caseCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="transMethod" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="transactionDetails" type="{http://b2b.tracfone.com/AccountServices}TransactionDeatilsType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateActionItemIGTransactionRequestType", propOrder = {
    "contactObjId",
    "callTransObjId",
    "orderType",
    "byPassOrderType",
    "caseCode",
    "transMethod",
    "transactionDetails"
})
public class CreateActionItemIGTransactionRequestType {

    @XmlElement(required = true)
    protected String contactObjId;
    @XmlElement(required = true)
    protected String callTransObjId;
    protected String orderType;
    protected String byPassOrderType;
    protected String caseCode;
    protected String transMethod;
    @XmlElement(required = true)
    protected TransactionDeatilsType transactionDetails;

    /**
     * Gets the value of the contactObjId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContactObjId() {
        return contactObjId;
    }

    /**
     * Sets the value of the contactObjId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContactObjId(String value) {
        this.contactObjId = value;
    }

    /**
     * Gets the value of the callTransObjId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCallTransObjId() {
        return callTransObjId;
    }

    /**
     * Sets the value of the callTransObjId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCallTransObjId(String value) {
        this.callTransObjId = value;
    }

    /**
     * Gets the value of the orderType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderType() {
        return orderType;
    }

    /**
     * Sets the value of the orderType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderType(String value) {
        this.orderType = value;
    }

    /**
     * Gets the value of the byPassOrderType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getByPassOrderType() {
        return byPassOrderType;
    }

    /**
     * Sets the value of the byPassOrderType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setByPassOrderType(String value) {
        this.byPassOrderType = value;
    }

    /**
     * Gets the value of the caseCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCaseCode() {
        return caseCode;
    }

    /**
     * Sets the value of the caseCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCaseCode(String value) {
        this.caseCode = value;
    }

    /**
     * Gets the value of the transMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransMethod() {
        return transMethod;
    }

    /**
     * Sets the value of the transMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransMethod(String value) {
        this.transMethod = value;
    }

    /**
     * Gets the value of the transactionDetails property.
     * 
     * @return
     *     possible object is
     *     {@link TransactionDeatilsType }
     *     
     */
    public TransactionDeatilsType getTransactionDetails() {
        return transactionDetails;
    }

    /**
     * Sets the value of the transactionDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransactionDeatilsType }
     *     
     */
    public void setTransactionDetails(TransactionDeatilsType value) {
        this.transactionDetails = value;
    }

}
