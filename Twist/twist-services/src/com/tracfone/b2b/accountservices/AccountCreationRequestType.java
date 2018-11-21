
package com.tracfone.b2b.accountservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import com.tracfone.accountcommontypes.AccountCredentialsType;
import com.tracfone.commontypes.AddressType;
import com.tracfone.commontypes.BaseRequestType;
import com.tracfone.commontypes.PersonNameType;


/**
 * <p>Java class for AccountCreationRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AccountCreationRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/CommonTypes}BaseRequestType">
 *       &lt;sequence>
 *         &lt;element name="customerName" type="{http://www.tracfone.com/CommonTypes}PersonNameType"/>
 *         &lt;element name="accountCredentials" type="{http://www.tracfone.com/AccountCommonTypes}AccountCredentialsType"/>
 *         &lt;element name="address" type="{http://www.tracfone.com/CommonTypes}AddressType"/>
 *         &lt;element name="phoneNumber" type="{http://www.tracfone.com/PhoneCommonTypes}PhoneNumberType" minOccurs="0"/>
 *         &lt;element name="dateOfBirth" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="fourPin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="securityQuestion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="securityQuestionAnswer">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="1"/>
 *               &lt;whiteSpace value="collapse"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="canTracfoneContactYou" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="canTracfonePartnersContactYou" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="esn" type="{http://www.tracfone.com/PhoneCommonTypes}ESNType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountCreationRequestType", propOrder = {
    "customerName",
    "accountCredentials",
    "address",
    "phoneNumber",
    "dateOfBirth",
    "fourPin",
    "securityQuestion",
    "securityQuestionAnswer",
    "canTracfoneContactYou",
    "canTracfonePartnersContactYou",
    "esn"
})
public class AccountCreationRequestType
    extends BaseRequestType
{

    @XmlElement(required = true)
    protected PersonNameType customerName;
    @XmlElement(required = true)
    protected AccountCredentialsType accountCredentials;
    @XmlElement(required = true)
    protected AddressType address;
    protected String phoneNumber;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dateOfBirth;
    protected String fourPin;
    @XmlElement(required = true)
    protected String securityQuestion;
    @XmlElement(required = true)
    protected String securityQuestionAnswer;
    protected boolean canTracfoneContactYou;
    protected boolean canTracfonePartnersContactYou;
    protected String esn;

    /**
     * Gets the value of the customerName property.
     * 
     * @return
     *     possible object is
     *     {@link PersonNameType }
     *     
     */
    public PersonNameType getCustomerName() {
        return customerName;
    }

    /**
     * Sets the value of the customerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonNameType }
     *     
     */
    public void setCustomerName(PersonNameType value) {
        this.customerName = value;
    }

    /**
     * Gets the value of the accountCredentials property.
     * 
     * @return
     *     possible object is
     *     {@link AccountCredentialsType }
     *     
     */
    public AccountCredentialsType getAccountCredentials() {
        return accountCredentials;
    }

    /**
     * Sets the value of the accountCredentials property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountCredentialsType }
     *     
     */
    public void setAccountCredentials(AccountCredentialsType value) {
        this.accountCredentials = value;
    }

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link AddressType }
     *     
     */
    public AddressType getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressType }
     *     
     */
    public void setAddress(AddressType value) {
        this.address = value;
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
     * Gets the value of the dateOfBirth property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets the value of the dateOfBirth property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateOfBirth(XMLGregorianCalendar value) {
        this.dateOfBirth = value;
    }

    /**
     * Gets the value of the fourPin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFourPin() {
        return fourPin;
    }

    /**
     * Sets the value of the fourPin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFourPin(String value) {
        this.fourPin = value;
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
     */
    public boolean isCanTracfoneContactYou() {
        return canTracfoneContactYou;
    }

    /**
     * Sets the value of the canTracfoneContactYou property.
     * 
     */
    public void setCanTracfoneContactYou(boolean value) {
        this.canTracfoneContactYou = value;
    }

    /**
     * Gets the value of the canTracfonePartnersContactYou property.
     * 
     */
    public boolean isCanTracfonePartnersContactYou() {
        return canTracfonePartnersContactYou;
    }

    /**
     * Sets the value of the canTracfonePartnersContactYou property.
     * 
     */
    public void setCanTracfonePartnersContactYou(boolean value) {
        this.canTracfonePartnersContactYou = value;
    }

    /**
     * Gets the value of the esn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsn() {
        return esn;
    }

    /**
     * Sets the value of the esn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsn(String value) {
        this.esn = value;
    }

}
