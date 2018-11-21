
package com.tracfone.b2b.phoneservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import com.tracfone.commontypes.BaseResponseType;
import com.tracfone.phonecommontypes.ServiceStatusType;


/**
 * <p>Java class for DeviceServiceInformationResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DeviceServiceInformationResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/CommonTypes}BaseResponseType">
 *       &lt;sequence>
 *         &lt;element name="serviceEndDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="servicePlanId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="enrolledInAutoRefill" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="servicePlanUnlimited" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="zipcode" type="{http://www.tracfone.com/CommonTypes}ZipCodeType" minOccurs="0"/>
 *         &lt;element name="serviceStatus" type="{http://www.tracfone.com/PhoneCommonTypes}ServiceStatusType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeviceServiceInformationResponseType", propOrder = {
    "serviceEndDate",
    "servicePlanId",
    "enrolledInAutoRefill",
    "servicePlanUnlimited",
    "zipcode",
    "serviceStatus"
})
public class DeviceServiceInformationResponseType
    extends BaseResponseType
{

    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar serviceEndDate;
    protected String servicePlanId;
    protected boolean enrolledInAutoRefill;
    protected boolean servicePlanUnlimited;
    protected String zipcode;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected ServiceStatusType serviceStatus;

    /**
     * Gets the value of the serviceEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getServiceEndDate() {
        return serviceEndDate;
    }

    /**
     * Sets the value of the serviceEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setServiceEndDate(XMLGregorianCalendar value) {
        this.serviceEndDate = value;
    }

    /**
     * Gets the value of the servicePlanId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServicePlanId() {
        return servicePlanId;
    }

    /**
     * Sets the value of the servicePlanId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServicePlanId(String value) {
        this.servicePlanId = value;
    }

    /**
     * Gets the value of the enrolledInAutoRefill property.
     * 
     */
    public boolean isEnrolledInAutoRefill() {
        return enrolledInAutoRefill;
    }

    /**
     * Sets the value of the enrolledInAutoRefill property.
     * 
     */
    public void setEnrolledInAutoRefill(boolean value) {
        this.enrolledInAutoRefill = value;
    }

    /**
     * Gets the value of the servicePlanUnlimited property.
     * 
     */
    public boolean isServicePlanUnlimited() {
        return servicePlanUnlimited;
    }

    /**
     * Sets the value of the servicePlanUnlimited property.
     * 
     */
    public void setServicePlanUnlimited(boolean value) {
        this.servicePlanUnlimited = value;
    }

    /**
     * Gets the value of the zipcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZipcode() {
        return zipcode;
    }

    /**
     * Sets the value of the zipcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZipcode(String value) {
        this.zipcode = value;
    }

    /**
     * Gets the value of the serviceStatus property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceStatusType }
     *     
     */
    public ServiceStatusType getServiceStatus() {
        return serviceStatus;
    }

    /**
     * Sets the value of the serviceStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceStatusType }
     *     
     */
    public void setServiceStatus(ServiceStatusType value) {
        this.serviceStatus = value;
    }

}
