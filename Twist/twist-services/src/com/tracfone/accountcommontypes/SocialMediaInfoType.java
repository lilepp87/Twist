
package com.tracfone.accountcommontypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.GenderType;
import com.tracfone.commontypes.PersonNameType;


/**
 * <p>Java class for SocialMediaInfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SocialMediaInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="businessToken" type="{http://www.tracfone.com/CommonTypes}ObjectIdType"/>
 *         &lt;element name="smUIDs" type="{http://www.tracfone.com/AccountCommonTypes}SMUIDType"/>
 *         &lt;element name="smeID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="name" type="{http://www.tracfone.com/CommonTypes}PersonNameType" minOccurs="0"/>
 *         &lt;element name="link" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="userName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="gender" type="{http://www.tracfone.com/CommonTypes}GenderType" minOccurs="0"/>
 *         &lt;element name="userLocale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ageRange" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="email" type="{http://www.tracfone.com/CommonTypes}EmailType" minOccurs="0"/>
 *         &lt;element name="friendList" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SocialMediaInfoType", propOrder = {
    "businessToken",
    "smUIDs",
    "smeID",
    "name",
    "link",
    "userName",
    "gender",
    "userLocale",
    "ageRange",
    "email",
    "friendList"
})
public class SocialMediaInfoType {

    @XmlElement(required = true)
    protected String businessToken;
    @XmlElement(required = true)
    protected SMUIDType smUIDs;
    protected int smeID;
    protected PersonNameType name;
    protected String link;
    protected String userName;
    @XmlSchemaType(name = "string")
    protected GenderType gender;
    protected String userLocale;
    protected String ageRange;
    protected String email;
    protected String friendList;

    /**
     * Gets the value of the businessToken property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessToken() {
        return businessToken;
    }

    /**
     * Sets the value of the businessToken property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessToken(String value) {
        this.businessToken = value;
    }

    /**
     * Gets the value of the smUIDs property.
     * 
     * @return
     *     possible object is
     *     {@link SMUIDType }
     *     
     */
    public SMUIDType getSmUIDs() {
        return smUIDs;
    }

    /**
     * Sets the value of the smUIDs property.
     * 
     * @param value
     *     allowed object is
     *     {@link SMUIDType }
     *     
     */
    public void setSmUIDs(SMUIDType value) {
        this.smUIDs = value;
    }

    /**
     * Gets the value of the smeID property.
     * 
     */
    public int getSmeID() {
        return smeID;
    }

    /**
     * Sets the value of the smeID property.
     * 
     */
    public void setSmeID(int value) {
        this.smeID = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link PersonNameType }
     *     
     */
    public PersonNameType getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonNameType }
     *     
     */
    public void setName(PersonNameType value) {
        this.name = value;
    }

    /**
     * Gets the value of the link property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLink() {
        return link;
    }

    /**
     * Sets the value of the link property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLink(String value) {
        this.link = value;
    }

    /**
     * Gets the value of the userName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the value of the userName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserName(String value) {
        this.userName = value;
    }

    /**
     * Gets the value of the gender property.
     * 
     * @return
     *     possible object is
     *     {@link GenderType }
     *     
     */
    public GenderType getGender() {
        return gender;
    }

    /**
     * Sets the value of the gender property.
     * 
     * @param value
     *     allowed object is
     *     {@link GenderType }
     *     
     */
    public void setGender(GenderType value) {
        this.gender = value;
    }

    /**
     * Gets the value of the userLocale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserLocale() {
        return userLocale;
    }

    /**
     * Sets the value of the userLocale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserLocale(String value) {
        this.userLocale = value;
    }

    /**
     * Gets the value of the ageRange property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgeRange() {
        return ageRange;
    }

    /**
     * Sets the value of the ageRange property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgeRange(String value) {
        this.ageRange = value;
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
     * Gets the value of the friendList property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFriendList() {
        return friendList;
    }

    /**
     * Sets the value of the friendList property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFriendList(String value) {
        this.friendList = value;
    }

}
