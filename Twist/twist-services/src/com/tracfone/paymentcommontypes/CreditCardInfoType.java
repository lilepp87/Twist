
package com.tracfone.paymentcommontypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.securitycommontypes.DpCryptoType;


/**
 * <p>Java class for CreditCardInfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreditCardInfoType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/PaymentCommonTypes}CreditCardInfoBaseType">
 *       &lt;sequence>
 *         &lt;element name="cardId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cardNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="expDate">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;pattern value="[\d]{2}\\[\d]{4}"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="securityCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cvv" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="secureData" type="{http://www.tracfone.com/SecurityCommonTypes}DpCryptoType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="loggable" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" fixed="false" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreditCardInfoType", propOrder = {
    "cardId",
    "cardNumber",
    "expDate",
    "securityCode",
    "cvv",
    "secureData"
})
public class CreditCardInfoType
    extends CreditCardInfoBaseType
{

    @XmlElement(required = true)
    protected String cardId;
    @XmlElement(required = true)
    protected String cardNumber;
    @XmlElement(required = true)
    protected String expDate;
    @XmlElement(required = true)
    protected String securityCode;
    @XmlElement(required = true)
    protected String cvv;
    protected DpCryptoType secureData;
    @XmlAttribute(name = "loggable", required = true)
    protected boolean loggable;

    /**
     * Gets the value of the cardId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardId() {
        return cardId;
    }

    /**
     * Sets the value of the cardId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardId(String value) {
        this.cardId = value;
    }

    /**
     * Gets the value of the cardNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * Sets the value of the cardNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardNumber(String value) {
        this.cardNumber = value;
    }

    /**
     * Gets the value of the expDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpDate() {
        return expDate;
    }

    /**
     * Sets the value of the expDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpDate(String value) {
        this.expDate = value;
    }

    /**
     * Gets the value of the securityCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecurityCode() {
        return securityCode;
    }

    /**
     * Sets the value of the securityCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecurityCode(String value) {
        this.securityCode = value;
    }

    /**
     * Gets the value of the cvv property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCvv() {
        return cvv;
    }

    /**
     * Sets the value of the cvv property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCvv(String value) {
        this.cvv = value;
    }

    /**
     * Gets the value of the secureData property.
     * 
     * @return
     *     possible object is
     *     {@link DpCryptoType }
     *     
     */
    public DpCryptoType getSecureData() {
        return secureData;
    }

    /**
     * Sets the value of the secureData property.
     * 
     * @param value
     *     allowed object is
     *     {@link DpCryptoType }
     *     
     */
    public void setSecureData(DpCryptoType value) {
        this.secureData = value;
    }

    /**
     * Gets the value of the loggable property.
     * 
     */
    public boolean isLoggable() {
        return loggable;
    }

    /**
     * Sets the value of the loggable property.
     * 
     */
    public void setLoggable(boolean value) {
        this.loggable = value;
    }

}
