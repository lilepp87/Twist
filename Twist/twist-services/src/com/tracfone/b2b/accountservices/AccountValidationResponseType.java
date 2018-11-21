
package com.tracfone.b2b.accountservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.BaseResponseType;


/**
 * <p>Java class for AccountValidationResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AccountValidationResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/CommonTypes}BaseResponseType">
 *       &lt;sequence minOccurs="0">
 *         &lt;element name="isValid" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="accountWebUserId" type="{http://www.tracfone.com/CommonTypes}ObjectIdType" minOccurs="0"/>
 *         &lt;element name="accountContactObjId" type="{http://www.tracfone.com/CommonTypes}ObjectIdType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountValidationResponseType", propOrder = {
    "isValid",
    "accountWebUserId",
    "accountContactObjId"
})
public class AccountValidationResponseType
    extends BaseResponseType
{

    protected Boolean isValid;
    protected String accountWebUserId;
    protected String accountContactObjId;

    /**
     * Gets the value of the isValid property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsValid() {
        return isValid;
    }

    /**
     * Sets the value of the isValid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsValid(Boolean value) {
        this.isValid = value;
    }

    /**
     * Gets the value of the accountWebUserId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountWebUserId() {
        return accountWebUserId;
    }

    /**
     * Sets the value of the accountWebUserId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountWebUserId(String value) {
        this.accountWebUserId = value;
    }

    /**
     * Gets the value of the accountContactObjId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountContactObjId() {
        return accountContactObjId;
    }

    /**
     * Sets the value of the accountContactObjId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountContactObjId(String value) {
        this.accountContactObjId = value;
    }

}
