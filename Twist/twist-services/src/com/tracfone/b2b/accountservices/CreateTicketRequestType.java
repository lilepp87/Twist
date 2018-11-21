
package com.tracfone.b2b.accountservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.AddressType;
import com.tracfone.commontypes.PersonNameType;


/**
 * <p>Java class for CreateTicketRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreateTicketRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="issue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="shippingAddress" type="{http://www.tracfone.com/CommonTypes}AddressType"/>
 *         &lt;element name="currentEsn" type="{http://www.tracfone.com/PhoneCommonTypes}ESNType"/>
 *         &lt;element name="currentMin" type="{http://www.tracfone.com/PhoneCommonTypes}MINType"/>
 *         &lt;element name="source" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ss" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="currentCarrierId" type="{http://www.tracfone.com/CommonTypes}ObjectIdType"/>
 *         &lt;element name="currentCarrier" type="{http://www.tracfone.com/CommonTypes}ObjectIdType"/>
 *         &lt;element name="currentCarrierOtherName" type="{http://www.tracfone.com/CommonTypes}ObjectIdType"/>
 *         &lt;element name="unitsReplaceCaseId" type="{http://www.tracfone.com/CommonTypes}ObjectIdType" minOccurs="0"/>
 *         &lt;element name="simId" type="{http://www.tracfone.com/CommonTypes}ObjectIdType" minOccurs="0"/>
 *         &lt;element name="notes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ticketType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="attributeAssignedCarrierId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="attributeAccount" type="{http://www.tracfone.com/CommonTypes}ObjectIdType" minOccurs="0"/>
 *         &lt;element name="customerObjId" type="{http://www.tracfone.com/CommonTypes}ObjectIdType"/>
 *         &lt;element name="customerName" type="{http://www.tracfone.com/CommonTypes}PersonNameType"/>
 *         &lt;element name="customerAddress" type="{http://www.tracfone.com/CommonTypes}AddressType"/>
 *         &lt;element name="ticketPriority" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pointOfContact" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ratePlan" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="assignedCarrier" type="{http://www.tracfone.com/CommonTypes}ObjectIdType"/>
 *         &lt;element name="agent" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="homePhone" type="{http://www.tracfone.com/PhoneCommonTypes}PhoneNumberType" minOccurs="0"/>
 *         &lt;element name="populateCustomer" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attributeRecurrent" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="targetEsn" type="{http://www.tracfone.com/PhoneCommonTypes}ESNType"/>
 *         &lt;element name="ticketTitle" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="attributeActivationZipcode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="attributePin" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AttributeServicePlan" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AttributeEnrollAutoRefill" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AttributeEnrollPaymentSource" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AttributeEnnrollRecurrentPlan" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AttributeEnrollNextChargeDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AttributeNewMSID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CustomerEmail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AttributeZipCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PartNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Priority" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CustomerFirstName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AttributeCustEmail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AttributeCurrentAddrHouseNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AttributeCurrAddrDirection" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AttributeCurrAddrStreetName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AttributeCurrAddrStreetType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AttributeCurrAddrUnit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateTicketRequestType", propOrder = {
    "issue",
    "shippingAddress",
    "currentEsn",
    "currentMin",
    "source",
    "ss",
    "currentCarrierId",
    "currentCarrier",
    "currentCarrierOtherName",
    "unitsReplaceCaseId",
    "simId",
    "notes",
    "ticketType",
    "attributeAssignedCarrierId",
    "attributeAccount",
    "customerObjId",
    "customerName",
    "customerAddress",
    "ticketPriority",
    "pointOfContact",
    "ratePlan",
    "assignedCarrier",
    "agent",
    "homePhone",
    "populateCustomer",
    "attributeRecurrent",
    "targetEsn",
    "ticketTitle",
    "attributeActivationZipcode",
    "attributePin",
    "attributeServicePlan",
    "attributeEnrollAutoRefill",
    "attributeEnrollPaymentSource",
    "attributeEnnrollRecurrentPlan",
    "attributeEnrollNextChargeDate",
    "attributeNewMSID",
    "customerEmail",
    "attributeZipCode",
    "partNumber",
    "priority",
    "customerFirstName",
    "attributeCustEmail",
    "attributeCurrentAddrHouseNumber",
    "attributeCurrAddrDirection",
    "attributeCurrAddrStreetName",
    "attributeCurrAddrStreetType",
    "attributeCurrAddrUnit"
})
public class CreateTicketRequestType {

    @XmlElement(required = true)
    protected String issue;
    @XmlElement(required = true)
    protected AddressType shippingAddress;
    @XmlElement(required = true)
    protected String currentEsn;
    @XmlElement(required = true)
    protected String currentMin;
    @XmlElement(required = true)
    protected String source;
    protected String ss;
    @XmlElement(required = true)
    protected String currentCarrierId;
    @XmlElement(required = true)
    protected String currentCarrier;
    @XmlElement(required = true)
    protected String currentCarrierOtherName;
    protected String unitsReplaceCaseId;
    protected String simId;
    protected String notes;
    @XmlElement(required = true)
    protected String ticketType;
    @XmlElement(required = true)
    protected String attributeAssignedCarrierId;
    protected String attributeAccount;
    @XmlElement(required = true)
    protected String customerObjId;
    @XmlElement(required = true)
    protected PersonNameType customerName;
    @XmlElement(required = true)
    protected AddressType customerAddress;
    @XmlElement(required = true)
    protected String ticketPriority;
    @XmlElement(required = true)
    protected String pointOfContact;
    protected String ratePlan;
    @XmlElement(required = true)
    protected String assignedCarrier;
    @XmlElement(required = true)
    protected String agent;
    protected String homePhone;
    protected String populateCustomer;
    protected String attributeRecurrent;
    @XmlElement(required = true)
    protected String targetEsn;
    @XmlElement(required = true)
    protected String ticketTitle;
    protected String attributeActivationZipcode;
    protected String attributePin;
    @XmlElement(name = "AttributeServicePlan")
    protected String attributeServicePlan;
    @XmlElement(name = "AttributeEnrollAutoRefill")
    protected String attributeEnrollAutoRefill;
    @XmlElement(name = "AttributeEnrollPaymentSource")
    protected String attributeEnrollPaymentSource;
    @XmlElement(name = "AttributeEnnrollRecurrentPlan")
    protected String attributeEnnrollRecurrentPlan;
    @XmlElement(name = "AttributeEnrollNextChargeDate")
    protected String attributeEnrollNextChargeDate;
    @XmlElement(name = "AttributeNewMSID")
    protected String attributeNewMSID;
    @XmlElement(name = "CustomerEmail")
    protected String customerEmail;
    @XmlElement(name = "AttributeZipCode")
    protected String attributeZipCode;
    @XmlElement(name = "PartNumber")
    protected String partNumber;
    @XmlElement(name = "Priority")
    protected String priority;
    @XmlElement(name = "CustomerFirstName")
    protected String customerFirstName;
    @XmlElement(name = "AttributeCustEmail")
    protected String attributeCustEmail;
    @XmlElement(name = "AttributeCurrentAddrHouseNumber")
    protected String attributeCurrentAddrHouseNumber;
    @XmlElement(name = "AttributeCurrAddrDirection")
    protected String attributeCurrAddrDirection;
    @XmlElement(name = "AttributeCurrAddrStreetName")
    protected String attributeCurrAddrStreetName;
    @XmlElement(name = "AttributeCurrAddrStreetType")
    protected String attributeCurrAddrStreetType;
    @XmlElement(name = "AttributeCurrAddrUnit")
    protected String attributeCurrAddrUnit;

    /**
     * Gets the value of the issue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIssue() {
        return issue;
    }

    /**
     * Sets the value of the issue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIssue(String value) {
        this.issue = value;
    }

    /**
     * Gets the value of the shippingAddress property.
     * 
     * @return
     *     possible object is
     *     {@link AddressType }
     *     
     */
    public AddressType getShippingAddress() {
        return shippingAddress;
    }

    /**
     * Sets the value of the shippingAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressType }
     *     
     */
    public void setShippingAddress(AddressType value) {
        this.shippingAddress = value;
    }

    /**
     * Gets the value of the currentEsn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrentEsn() {
        return currentEsn;
    }

    /**
     * Sets the value of the currentEsn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrentEsn(String value) {
        this.currentEsn = value;
    }

    /**
     * Gets the value of the currentMin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrentMin() {
        return currentMin;
    }

    /**
     * Sets the value of the currentMin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrentMin(String value) {
        this.currentMin = value;
    }

    /**
     * Gets the value of the source property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets the value of the source property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSource(String value) {
        this.source = value;
    }

    /**
     * Gets the value of the ss property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSs() {
        return ss;
    }

    /**
     * Sets the value of the ss property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSs(String value) {
        this.ss = value;
    }

    /**
     * Gets the value of the currentCarrierId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrentCarrierId() {
        return currentCarrierId;
    }

    /**
     * Sets the value of the currentCarrierId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrentCarrierId(String value) {
        this.currentCarrierId = value;
    }

    /**
     * Gets the value of the currentCarrier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrentCarrier() {
        return currentCarrier;
    }

    /**
     * Sets the value of the currentCarrier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrentCarrier(String value) {
        this.currentCarrier = value;
    }

    /**
     * Gets the value of the currentCarrierOtherName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrentCarrierOtherName() {
        return currentCarrierOtherName;
    }

    /**
     * Sets the value of the currentCarrierOtherName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrentCarrierOtherName(String value) {
        this.currentCarrierOtherName = value;
    }

    /**
     * Gets the value of the unitsReplaceCaseId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnitsReplaceCaseId() {
        return unitsReplaceCaseId;
    }

    /**
     * Sets the value of the unitsReplaceCaseId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnitsReplaceCaseId(String value) {
        this.unitsReplaceCaseId = value;
    }

    /**
     * Gets the value of the simId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSimId() {
        return simId;
    }

    /**
     * Sets the value of the simId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSimId(String value) {
        this.simId = value;
    }

    /**
     * Gets the value of the notes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets the value of the notes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotes(String value) {
        this.notes = value;
    }

    /**
     * Gets the value of the ticketType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTicketType() {
        return ticketType;
    }

    /**
     * Sets the value of the ticketType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTicketType(String value) {
        this.ticketType = value;
    }

    /**
     * Gets the value of the attributeAssignedCarrierId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttributeAssignedCarrierId() {
        return attributeAssignedCarrierId;
    }

    /**
     * Sets the value of the attributeAssignedCarrierId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttributeAssignedCarrierId(String value) {
        this.attributeAssignedCarrierId = value;
    }

    /**
     * Gets the value of the attributeAccount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttributeAccount() {
        return attributeAccount;
    }

    /**
     * Sets the value of the attributeAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttributeAccount(String value) {
        this.attributeAccount = value;
    }

    /**
     * Gets the value of the customerObjId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerObjId() {
        return customerObjId;
    }

    /**
     * Sets the value of the customerObjId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerObjId(String value) {
        this.customerObjId = value;
    }

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
     * Gets the value of the customerAddress property.
     * 
     * @return
     *     possible object is
     *     {@link AddressType }
     *     
     */
    public AddressType getCustomerAddress() {
        return customerAddress;
    }

    /**
     * Sets the value of the customerAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressType }
     *     
     */
    public void setCustomerAddress(AddressType value) {
        this.customerAddress = value;
    }

    /**
     * Gets the value of the ticketPriority property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTicketPriority() {
        return ticketPriority;
    }

    /**
     * Sets the value of the ticketPriority property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTicketPriority(String value) {
        this.ticketPriority = value;
    }

    /**
     * Gets the value of the pointOfContact property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPointOfContact() {
        return pointOfContact;
    }

    /**
     * Sets the value of the pointOfContact property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPointOfContact(String value) {
        this.pointOfContact = value;
    }

    /**
     * Gets the value of the ratePlan property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRatePlan() {
        return ratePlan;
    }

    /**
     * Sets the value of the ratePlan property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRatePlan(String value) {
        this.ratePlan = value;
    }

    /**
     * Gets the value of the assignedCarrier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssignedCarrier() {
        return assignedCarrier;
    }

    /**
     * Sets the value of the assignedCarrier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssignedCarrier(String value) {
        this.assignedCarrier = value;
    }

    /**
     * Gets the value of the agent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgent() {
        return agent;
    }

    /**
     * Sets the value of the agent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgent(String value) {
        this.agent = value;
    }

    /**
     * Gets the value of the homePhone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHomePhone() {
        return homePhone;
    }

    /**
     * Sets the value of the homePhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHomePhone(String value) {
        this.homePhone = value;
    }

    /**
     * Gets the value of the populateCustomer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPopulateCustomer() {
        return populateCustomer;
    }

    /**
     * Sets the value of the populateCustomer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPopulateCustomer(String value) {
        this.populateCustomer = value;
    }

    /**
     * Gets the value of the attributeRecurrent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttributeRecurrent() {
        return attributeRecurrent;
    }

    /**
     * Sets the value of the attributeRecurrent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttributeRecurrent(String value) {
        this.attributeRecurrent = value;
    }

    /**
     * Gets the value of the targetEsn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetEsn() {
        return targetEsn;
    }

    /**
     * Sets the value of the targetEsn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetEsn(String value) {
        this.targetEsn = value;
    }

    /**
     * Gets the value of the ticketTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTicketTitle() {
        return ticketTitle;
    }

    /**
     * Sets the value of the ticketTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTicketTitle(String value) {
        this.ticketTitle = value;
    }

    /**
     * Gets the value of the attributeActivationZipcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttributeActivationZipcode() {
        return attributeActivationZipcode;
    }

    /**
     * Sets the value of the attributeActivationZipcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttributeActivationZipcode(String value) {
        this.attributeActivationZipcode = value;
    }

    /**
     * Gets the value of the attributePin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttributePin() {
        return attributePin;
    }

    /**
     * Sets the value of the attributePin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttributePin(String value) {
        this.attributePin = value;
    }

    /**
     * Gets the value of the attributeServicePlan property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttributeServicePlan() {
        return attributeServicePlan;
    }

    /**
     * Sets the value of the attributeServicePlan property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttributeServicePlan(String value) {
        this.attributeServicePlan = value;
    }

    /**
     * Gets the value of the attributeEnrollAutoRefill property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttributeEnrollAutoRefill() {
        return attributeEnrollAutoRefill;
    }

    /**
     * Sets the value of the attributeEnrollAutoRefill property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttributeEnrollAutoRefill(String value) {
        this.attributeEnrollAutoRefill = value;
    }

    /**
     * Gets the value of the attributeEnrollPaymentSource property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttributeEnrollPaymentSource() {
        return attributeEnrollPaymentSource;
    }

    /**
     * Sets the value of the attributeEnrollPaymentSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttributeEnrollPaymentSource(String value) {
        this.attributeEnrollPaymentSource = value;
    }

    /**
     * Gets the value of the attributeEnnrollRecurrentPlan property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttributeEnnrollRecurrentPlan() {
        return attributeEnnrollRecurrentPlan;
    }

    /**
     * Sets the value of the attributeEnnrollRecurrentPlan property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttributeEnnrollRecurrentPlan(String value) {
        this.attributeEnnrollRecurrentPlan = value;
    }

    /**
     * Gets the value of the attributeEnrollNextChargeDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttributeEnrollNextChargeDate() {
        return attributeEnrollNextChargeDate;
    }

    /**
     * Sets the value of the attributeEnrollNextChargeDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttributeEnrollNextChargeDate(String value) {
        this.attributeEnrollNextChargeDate = value;
    }

    /**
     * Gets the value of the attributeNewMSID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttributeNewMSID() {
        return attributeNewMSID;
    }

    /**
     * Sets the value of the attributeNewMSID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttributeNewMSID(String value) {
        this.attributeNewMSID = value;
    }

    /**
     * Gets the value of the customerEmail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerEmail() {
        return customerEmail;
    }

    /**
     * Sets the value of the customerEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerEmail(String value) {
        this.customerEmail = value;
    }

    /**
     * Gets the value of the attributeZipCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttributeZipCode() {
        return attributeZipCode;
    }

    /**
     * Sets the value of the attributeZipCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttributeZipCode(String value) {
        this.attributeZipCode = value;
    }

    /**
     * Gets the value of the partNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartNumber() {
        return partNumber;
    }

    /**
     * Sets the value of the partNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartNumber(String value) {
        this.partNumber = value;
    }

    /**
     * Gets the value of the priority property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPriority() {
        return priority;
    }

    /**
     * Sets the value of the priority property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPriority(String value) {
        this.priority = value;
    }

    /**
     * Gets the value of the customerFirstName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerFirstName() {
        return customerFirstName;
    }

    /**
     * Sets the value of the customerFirstName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerFirstName(String value) {
        this.customerFirstName = value;
    }

    /**
     * Gets the value of the attributeCustEmail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttributeCustEmail() {
        return attributeCustEmail;
    }

    /**
     * Sets the value of the attributeCustEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttributeCustEmail(String value) {
        this.attributeCustEmail = value;
    }

    /**
     * Gets the value of the attributeCurrentAddrHouseNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttributeCurrentAddrHouseNumber() {
        return attributeCurrentAddrHouseNumber;
    }

    /**
     * Sets the value of the attributeCurrentAddrHouseNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttributeCurrentAddrHouseNumber(String value) {
        this.attributeCurrentAddrHouseNumber = value;
    }

    /**
     * Gets the value of the attributeCurrAddrDirection property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttributeCurrAddrDirection() {
        return attributeCurrAddrDirection;
    }

    /**
     * Sets the value of the attributeCurrAddrDirection property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttributeCurrAddrDirection(String value) {
        this.attributeCurrAddrDirection = value;
    }

    /**
     * Gets the value of the attributeCurrAddrStreetName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttributeCurrAddrStreetName() {
        return attributeCurrAddrStreetName;
    }

    /**
     * Sets the value of the attributeCurrAddrStreetName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttributeCurrAddrStreetName(String value) {
        this.attributeCurrAddrStreetName = value;
    }

    /**
     * Gets the value of the attributeCurrAddrStreetType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttributeCurrAddrStreetType() {
        return attributeCurrAddrStreetType;
    }

    /**
     * Sets the value of the attributeCurrAddrStreetType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttributeCurrAddrStreetType(String value) {
        this.attributeCurrAddrStreetType = value;
    }

    /**
     * Gets the value of the attributeCurrAddrUnit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttributeCurrAddrUnit() {
        return attributeCurrAddrUnit;
    }

    /**
     * Sets the value of the attributeCurrAddrUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttributeCurrAddrUnit(String value) {
        this.attributeCurrAddrUnit = value;
    }

}
