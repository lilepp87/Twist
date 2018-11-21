
package com.tracfone.accountcommontypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AccountIdentifierType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AccountIdentifierType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="accountIdentifierName">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="EMAIL"/>
 *               &lt;enumeration value="ESN"/>
 *               &lt;enumeration value="MIN"/>
 *               &lt;enumeration value="SIM"/>
 *               &lt;enumeration value="USERID"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="accountIdentifierValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountIdentifierType", propOrder = {
    "accountIdentifierName",
    "accountIdentifierValue"
})
public class AccountIdentifierType {

    @XmlElement(required = true)
    protected String accountIdentifierName;
    @XmlElement(required = true)
    protected String accountIdentifierValue;

    /**
     * Gets the value of the accountIdentifierName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountIdentifierName() {
        return accountIdentifierName;
    }

    /**
     * Sets the value of the accountIdentifierName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountIdentifierName(String value) {
        this.accountIdentifierName = value;
    }

    /**
     * Gets the value of the accountIdentifierValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountIdentifierValue() {
        return accountIdentifierValue;
    }

    /**
     * Sets the value of the accountIdentifierValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountIdentifierValue(String value) {
        this.accountIdentifierValue = value;
    }

}
