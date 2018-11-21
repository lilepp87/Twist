
package com.tracfone.b2b.phoneservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.BaseResponseType;


/**
 * <p>Java class for CheckCarrierPendingResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CheckCarrierPendingResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/CommonTypes}BaseResponseType">
 *       &lt;sequence minOccurs="0">
 *         &lt;element name="errorString" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="errorCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="carrierPending" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="AP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="BI" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CheckCarrierPendingResponseType", propOrder = {
    "errorString",
    "errorCode",
    "carrierPending",
    "ap",
    "cr",
    "bi"
})
public class CheckCarrierPendingResponseType
    extends BaseResponseType
{

    protected String errorString;
    protected String errorCode;
    protected Boolean carrierPending;
    @XmlElement(name = "AP")
    protected String ap;
    @XmlElement(name = "CR")
    protected String cr;
    @XmlElement(name = "BI")
    protected String bi;

    /**
     * Gets the value of the errorString property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorString() {
        return errorString;
    }

    /**
     * Sets the value of the errorString property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorString(String value) {
        this.errorString = value;
    }

    /**
     * Gets the value of the errorCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Sets the value of the errorCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrorCode(String value) {
        this.errorCode = value;
    }

    /**
     * Gets the value of the carrierPending property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCarrierPending() {
        return carrierPending;
    }

    /**
     * Sets the value of the carrierPending property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCarrierPending(Boolean value) {
        this.carrierPending = value;
    }

    /**
     * Gets the value of the ap property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAP() {
        return ap;
    }

    /**
     * Sets the value of the ap property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAP(String value) {
        this.ap = value;
    }

    /**
     * Gets the value of the cr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCR() {
        return cr;
    }

    /**
     * Sets the value of the cr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCR(String value) {
        this.cr = value;
    }

    /**
     * Gets the value of the bi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBI() {
        return bi;
    }

    /**
     * Sets the value of the bi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBI(String value) {
        this.bi = value;
    }

}
