
package com.tracfone.b2b.accountservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.accountcommontypes.AccountStatusType;
import com.tracfone.commontypes.AVAILABLETYPE;
import com.tracfone.commontypes.BaseResponseType;


/**
 * <p>Java class for AccountRecoveryAccountStatusResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AccountRecoveryAccountStatusResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/CommonTypes}BaseResponseType">
 *       &lt;sequence>
 *         &lt;element name="accountStatus" type="{http://www.tracfone.com/AccountCommonTypes}AccountStatusType"/>
 *         &lt;element name="securityPin" type="{http://www.tracfone.com/CommonTypes}AVAILABLETYPE" minOccurs="0"/>
 *         &lt;element name="securityQuestionAvail" type="{http://www.tracfone.com/CommonTypes}AVAILABLETYPE" minOccurs="0"/>
 *         &lt;element name="securityQuestion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountRecoveryAccountStatusResponseType", propOrder = {
    "accountStatus",
    "securityPin",
    "securityQuestionAvail",
    "securityQuestion"
})
public class AccountRecoveryAccountStatusResponseType
    extends BaseResponseType
{

    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected AccountStatusType accountStatus;
    @XmlSchemaType(name = "string")
    protected AVAILABLETYPE securityPin;
    @XmlSchemaType(name = "string")
    protected AVAILABLETYPE securityQuestionAvail;
    protected String securityQuestion;

    /**
     * Gets the value of the accountStatus property.
     * 
     * @return
     *     possible object is
     *     {@link AccountStatusType }
     *     
     */
    public AccountStatusType getAccountStatus() {
        return accountStatus;
    }

    /**
     * Sets the value of the accountStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountStatusType }
     *     
     */
    public void setAccountStatus(AccountStatusType value) {
        this.accountStatus = value;
    }

    /**
     * Gets the value of the securityPin property.
     * 
     * @return
     *     possible object is
     *     {@link AVAILABLETYPE }
     *     
     */
    public AVAILABLETYPE getSecurityPin() {
        return securityPin;
    }

    /**
     * Sets the value of the securityPin property.
     * 
     * @param value
     *     allowed object is
     *     {@link AVAILABLETYPE }
     *     
     */
    public void setSecurityPin(AVAILABLETYPE value) {
        this.securityPin = value;
    }

    /**
     * Gets the value of the securityQuestionAvail property.
     * 
     * @return
     *     possible object is
     *     {@link AVAILABLETYPE }
     *     
     */
    public AVAILABLETYPE getSecurityQuestionAvail() {
        return securityQuestionAvail;
    }

    /**
     * Sets the value of the securityQuestionAvail property.
     * 
     * @param value
     *     allowed object is
     *     {@link AVAILABLETYPE }
     *     
     */
    public void setSecurityQuestionAvail(AVAILABLETYPE value) {
        this.securityQuestionAvail = value;
    }

    /**
     * Gets the value of the securityQuestion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecurityQuestion() {
        return securityQuestion;
    }

    /**
     * Sets the value of the securityQuestion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecurityQuestion(String value) {
        this.securityQuestion = value;
    }

}
