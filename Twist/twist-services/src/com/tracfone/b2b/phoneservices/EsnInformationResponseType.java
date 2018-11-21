
package com.tracfone.b2b.phoneservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import com.tracfone.commontypes.BaseResponseType;
import com.tracfone.phonecommontypes.ESNStatusType;


/**
 * <p>Java class for EsnInformationResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EsnInformationResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/CommonTypes}BaseResponseType">
 *       &lt;sequence>
 *         &lt;element name="servicePlanId" type="{http://www.tracfone.com/ServicePlanCommonTypes}PlanIdType" minOccurs="0"/>
 *         &lt;element name="isServicePlanUnlimited" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="isEnrolledInAutorefill" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="endOfServiceDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="forecastDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="hasCreditCardRegistered" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="redemptionCardQueueSize" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="hasCreditCardInSchedule" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="esnStatus" type="{http://www.tracfone.com/PhoneCommonTypes}ESNStatusType" minOccurs="0"/>
 *         &lt;element name="email" type="{http://www.tracfone.com/CommonTypes}EmailType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EsnInformationResponseType", propOrder = {
    "servicePlanId",
    "isServicePlanUnlimited",
    "isEnrolledInAutorefill",
    "endOfServiceDate",
    "forecastDate",
    "hasCreditCardRegistered",
    "redemptionCardQueueSize",
    "hasCreditCardInSchedule",
    "esnStatus",
    "email"
})
public class EsnInformationResponseType
    extends BaseResponseType
{

    protected Integer servicePlanId;
    protected Boolean isServicePlanUnlimited;
    protected Boolean isEnrolledInAutorefill;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endOfServiceDate;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar forecastDate;
    protected Boolean hasCreditCardRegistered;
    protected Integer redemptionCardQueueSize;
    protected Boolean hasCreditCardInSchedule;
    protected ESNStatusType esnStatus;
    protected String email;

    /**
     * Gets the value of the servicePlanId property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getServicePlanId() {
        return servicePlanId;
    }

    /**
     * Sets the value of the servicePlanId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setServicePlanId(Integer value) {
        this.servicePlanId = value;
    }

    /**
     * Gets the value of the isServicePlanUnlimited property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsServicePlanUnlimited() {
        return isServicePlanUnlimited;
    }

    /**
     * Sets the value of the isServicePlanUnlimited property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsServicePlanUnlimited(Boolean value) {
        this.isServicePlanUnlimited = value;
    }

    /**
     * Gets the value of the isEnrolledInAutorefill property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsEnrolledInAutorefill() {
        return isEnrolledInAutorefill;
    }

    /**
     * Sets the value of the isEnrolledInAutorefill property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsEnrolledInAutorefill(Boolean value) {
        this.isEnrolledInAutorefill = value;
    }

    /**
     * Gets the value of the endOfServiceDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndOfServiceDate() {
        return endOfServiceDate;
    }

    /**
     * Sets the value of the endOfServiceDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndOfServiceDate(XMLGregorianCalendar value) {
        this.endOfServiceDate = value;
    }

    /**
     * Gets the value of the forecastDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getForecastDate() {
        return forecastDate;
    }

    /**
     * Sets the value of the forecastDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setForecastDate(XMLGregorianCalendar value) {
        this.forecastDate = value;
    }

    /**
     * Gets the value of the hasCreditCardRegistered property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHasCreditCardRegistered() {
        return hasCreditCardRegistered;
    }

    /**
     * Sets the value of the hasCreditCardRegistered property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHasCreditCardRegistered(Boolean value) {
        this.hasCreditCardRegistered = value;
    }

    /**
     * Gets the value of the redemptionCardQueueSize property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRedemptionCardQueueSize() {
        return redemptionCardQueueSize;
    }

    /**
     * Sets the value of the redemptionCardQueueSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRedemptionCardQueueSize(Integer value) {
        this.redemptionCardQueueSize = value;
    }

    /**
     * Gets the value of the hasCreditCardInSchedule property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHasCreditCardInSchedule() {
        return hasCreditCardInSchedule;
    }

    /**
     * Sets the value of the hasCreditCardInSchedule property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHasCreditCardInSchedule(Boolean value) {
        this.hasCreditCardInSchedule = value;
    }

    /**
     * Gets the value of the esnStatus property.
     * 
     * @return
     *     possible object is
     *     {@link ESNStatusType }
     *     
     */
    public ESNStatusType getEsnStatus() {
        return esnStatus;
    }

    /**
     * Sets the value of the esnStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link ESNStatusType }
     *     
     */
    public void setEsnStatus(ESNStatusType value) {
        this.esnStatus = value;
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

}
