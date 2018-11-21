
package com.tracfone.b2b.phoneservices;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for item_record complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="item_record">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="esn" type="{http://www.tracfone.com/PhoneCommonTypes}ESNType" minOccurs="0"/>
 *         &lt;element name="smp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="part_number" type="{http://www.tracfone.com/CommonTypes}PartNumberType" minOccurs="0"/>
 *         &lt;element name="is_tracfone" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "item_record", propOrder = {
    "esn",
    "smp",
    "partNumber",
    "isTracfone"
})
public class ItemRecord {

    @XmlElementRef(name = "esn", namespace = "http://b2b.tracfone.com/PhoneServices", type = JAXBElement.class, required = false)
    protected JAXBElement<String> esn;
    @XmlElementRef(name = "smp", namespace = "http://b2b.tracfone.com/PhoneServices", type = JAXBElement.class, required = false)
    protected JAXBElement<String> smp;
    @XmlElementRef(name = "part_number", namespace = "http://b2b.tracfone.com/PhoneServices", type = JAXBElement.class, required = false)
    protected JAXBElement<String> partNumber;
    @XmlElementRef(name = "is_tracfone", namespace = "http://b2b.tracfone.com/PhoneServices", type = JAXBElement.class, required = false)
    protected JAXBElement<String> isTracfone;

    /**
     * Gets the value of the esn property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getEsn() {
        return esn;
    }

    /**
     * Sets the value of the esn property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setEsn(JAXBElement<String> value) {
        this.esn = value;
    }

    /**
     * Gets the value of the smp property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getSmp() {
        return smp;
    }

    /**
     * Sets the value of the smp property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setSmp(JAXBElement<String> value) {
        this.smp = value;
    }

    /**
     * Gets the value of the partNumber property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getPartNumber() {
        return partNumber;
    }

    /**
     * Sets the value of the partNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setPartNumber(JAXBElement<String> value) {
        this.partNumber = value;
    }

    /**
     * Gets the value of the isTracfone property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getIsTracfone() {
        return isTracfone;
    }

    /**
     * Sets the value of the isTracfone property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setIsTracfone(JAXBElement<String> value) {
        this.isTracfone = value;
    }

}
