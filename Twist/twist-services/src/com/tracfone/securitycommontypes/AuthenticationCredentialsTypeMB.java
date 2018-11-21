
package com.tracfone.securitycommontypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AuthenticationCredentialsTypeMB complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AuthenticationCredentialsTypeMB">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="mbUserId" type="{http://www.tracfone.com/CommonTypes}ObjectIdType"/>
 *         &lt;element name="mbSecurityPin" type="{http://www.tracfone.com/PhoneCommonTypes}SecurityPinType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AuthenticationCredentialsTypeMB", propOrder = {
    "mbUserId",
    "mbSecurityPin"
})
public class AuthenticationCredentialsTypeMB {

    @XmlElement(required = true)
    protected String mbUserId;
    @XmlElement(required = true)
    protected String mbSecurityPin;

    /**
     * Gets the value of the mbUserId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMbUserId() {
        return mbUserId;
    }

    /**
     * Sets the value of the mbUserId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMbUserId(String value) {
        this.mbUserId = value;
    }

    /**
     * Gets the value of the mbSecurityPin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMbSecurityPin() {
        return mbSecurityPin;
    }

    /**
     * Sets the value of the mbSecurityPin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMbSecurityPin(String value) {
        this.mbSecurityPin = value;
    }

}
