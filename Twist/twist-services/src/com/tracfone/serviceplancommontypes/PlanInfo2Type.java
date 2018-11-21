
package com.tracfone.serviceplancommontypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for PlanInfo2Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PlanInfo2Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="planId" type="{http://www.tracfone.com/ServicePlanCommonTypes}PlanIdType"/>
 *         &lt;element name="planName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="planDesc" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="planType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="nextChargeDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="planPartNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PlanInfo2Type", propOrder = {
    "planId",
    "planName",
    "planDesc",
    "planType",
    "nextChargeDate",
    "planPartNumber"
})
public class PlanInfo2Type {

    protected int planId;
    @XmlElement(required = true)
    protected String planName;
    @XmlElement(required = true)
    protected String planDesc;
    @XmlElement(required = true)
    protected String planType;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar nextChargeDate;
    protected String planPartNumber;

    /**
     * Gets the value of the planId property.
     * 
     */
    public int getPlanId() {
        return planId;
    }

    /**
     * Sets the value of the planId property.
     * 
     */
    public void setPlanId(int value) {
        this.planId = value;
    }

    /**
     * Gets the value of the planName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlanName() {
        return planName;
    }

    /**
     * Sets the value of the planName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlanName(String value) {
        this.planName = value;
    }

    /**
     * Gets the value of the planDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlanDesc() {
        return planDesc;
    }

    /**
     * Sets the value of the planDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlanDesc(String value) {
        this.planDesc = value;
    }

    /**
     * Gets the value of the planType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlanType() {
        return planType;
    }

    /**
     * Sets the value of the planType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlanType(String value) {
        this.planType = value;
    }

    /**
     * Gets the value of the nextChargeDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getNextChargeDate() {
        return nextChargeDate;
    }

    /**
     * Sets the value of the nextChargeDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setNextChargeDate(XMLGregorianCalendar value) {
        this.nextChargeDate = value;
    }

    /**
     * Gets the value of the planPartNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlanPartNumber() {
        return planPartNumber;
    }

    /**
     * Sets the value of the planPartNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlanPartNumber(String value) {
        this.planPartNumber = value;
    }

}
