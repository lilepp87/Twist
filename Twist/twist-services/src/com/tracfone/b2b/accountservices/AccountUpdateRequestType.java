
package com.tracfone.b2b.accountservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import com.tracfone.commontypes.BaseRequestType;
import com.tracfone.commontypes.FullAddressType;
import com.tracfone.commontypes.FullPersonNameType;
import com.tracfone.securitycommontypes.AuthenticationCredentialsType;


/**
 * <p>Java class for AccountUpdateRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AccountUpdateRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/CommonTypes}BaseRequestType">
 *       &lt;sequence>
 *         &lt;element name="authenticationCredentials" type="{http://www.tracfone.com/SecurityCommonTypes}AuthenticationCredentialsType"/>
 *         &lt;element name="customerName" type="{http://www.tracfone.com/CommonTypes}FullPersonNameType" minOccurs="0"/>
 *         &lt;element name="nickName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="primaryAddress" type="{http://www.tracfone.com/CommonTypes}FullAddressType" minOccurs="0"/>
 *         &lt;element name="mailingAddress" type="{http://www.tracfone.com/CommonTypes}FullAddressType" minOccurs="0"/>
 *         &lt;element name="email" type="{http://www.tracfone.com/CommonTypes}EmailType" minOccurs="0"/>
 *         &lt;element name="password" type="{http://www.tracfone.com/AccountCommonTypes}PasswordType" minOccurs="0"/>
 *         &lt;element name="dob" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="securityPin" type="{http://www.tracfone.com/PhoneCommonTypes}SecurityPinType" minOccurs="0"/>
 *         &lt;element name="phoneNumber" type="{http://www.tracfone.com/PhoneCommonTypes}PhoneNumberType" minOccurs="0"/>
 *         &lt;element name="securityQuestion" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="securityQuestionAnswer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="canTracfoneContactYou" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="canTracfonePartnersContactYou" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountUpdateRequestType", propOrder = {
    "authenticationCredentials",
    "customerName",
    "nickName",
    "primaryAddress",
    "mailingAddress",
    "email",
    "password",
    "dob",
    "securityPin",
    "phoneNumber",
    "securityQuestion",
    "securityQuestionAnswer",
    "canTracfoneContactYou",
    "canTracfonePartnersContactYou"
})
public class AccountUpdateRequestType
    extends BaseRequestType
{

    @XmlElement(required = true)
    protected AuthenticationCredentialsType authenticationCredentials;
    protected FullPersonNameType customerName;
    protected String nickName;
    protected FullAddressType primaryAddress;
    protected FullAddressType mailingAddress;
    protected String email;
    protected String password;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dob;
    protected String securityPin;
    protected String phoneNumber;
    protected String securityQuestion;
    protected String securityQuestionAnswer;
    protected Boolean canTracfoneContactYou;
    protected Boolean canTracfonePartnersContactYou;

    /**
     * Gets the value of the authenticationCredentials property.
     * 
     * @return
     *     possible object is
     *     {@link AuthenticationCredentialsType }
     *     
     */
    public AuthenticationCredentialsType getAuthenticationCredentials() {
        return authenticationCredentials;
    }

    /**
     * Sets the value of the authenticationCredentials property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthenticationCredentialsType }
     *     
     */
    public void setAuthenticationCredentials(AuthenticationCredentialsType value) {
        this.authenticationCredentials = value;
    }

    /**
     * Gets the value of the customerName property.
     * 
     * @return
     *     possible object is
     *     {@link FullPersonNameType }
     *     
     */
    public FullPersonNameType getCustomerName() {
        return customerName;
    }

    /**
     * Sets the value of the customerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link FullPersonNameType }
     *     
     */
    public void setCustomerName(FullPersonNameType value) {
        this.customerName = value;
    }

    /**
     * Gets the value of the nickName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * Sets the value of the nickName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNickName(String value) {
        this.nickName = value;
    }

    /**
     * Gets the value of the primaryAddress property.
     * 
     * @return
     *     possible object is
     *     {@link FullAddressType }
     *     
     */
    public FullAddressType getPrimaryAddress() {
        return primaryAddress;
    }

    /**
     * Sets the value of the primaryAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link FullAddressType }
     *     
     */
    public void setPrimaryAddress(FullAddressType value) {
        this.primaryAddress = value;
    }

    /**
     * Gets the value of the mailingAddress property.
     * 
     * @return
     *     possible object is
     *     {@link FullAddressType }
     *     
     */
    public FullAddressType getMailingAddress() {
        return mailingAddress;
    }

    /**
     * Sets the value of the mailingAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link FullAddressType }
     *     
     */
    public void setMailingAddress(FullAddressType value) {
        this.mailingAddress = value;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the password property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Gets the value of the dob property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDob() {
        return dob;
    }

    /**
     * Sets the value of the dob property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDob(XMLGregorianCalendar value) {
        this.dob = value;
    }

    /**
     * Gets the value of the securityPin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecurityPin() {
        return securityPin;
    }

    /**
     * Sets the value of the securityPin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecurityPin(String value) {
        this.securityPin = value;
    }

    /**
     * Gets the value of the phoneNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the value of the phoneNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhoneNumber(String value) {
        this.phoneNumber = value;
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

    /**
     * Gets the value of the securityQuestionAnswer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecurityQuestionAnswer() {
        return securityQuestionAnswer;
    }

    /**
     * Sets the value of the securityQuestionAnswer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecurityQuestionAnswer(String value) {
        this.securityQuestionAnswer = value;
    }

    /**
     * Gets the value of the canTracfoneContactYou property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCanTracfoneContactYou() {
        return canTracfoneContactYou;
    }

    /**
     * Sets the value of the canTracfoneContactYou property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCanTracfoneContactYou(Boolean value) {
        this.canTracfoneContactYou = value;
    }

    /**
     * Gets the value of the canTracfonePartnersContactYou property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCanTracfonePartnersContactYou() {
        return canTracfonePartnersContactYou;
    }

    /**
     * Sets the value of the canTracfonePartnersContactYou property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCanTracfonePartnersContactYou(Boolean value) {
        this.canTracfonePartnersContactYou = value;
    }

}
