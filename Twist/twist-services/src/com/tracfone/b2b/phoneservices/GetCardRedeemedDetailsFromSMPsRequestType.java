
package com.tracfone.b2b.phoneservices;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for GetCardRedeemedDetailsFromSMPsRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetCardRedeemedDetailsFromSMPsRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="order_id" type="{http://www.tracfone.com/CommonTypes}ObjectIdType" minOccurs="0"/>
 *         &lt;element name="rma_id" type="{http://www.tracfone.com/CommonTypes}ObjectIdType" minOccurs="0"/>
 *         &lt;element name="order_type" type="{http://www.tracfone.com/CommonTypes}OrderIdType" minOccurs="0"/>
 *         &lt;element name="in_pin_status" type="{http://b2b.tracfone.com/PhoneServices}pin_status_table" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetCardRedeemedDetailsFromSMPsRequestType", propOrder = {
    "orderId",
    "rmaId",
    "orderType",
    "inPinStatus"
})
public class GetCardRedeemedDetailsFromSMPsRequestType {

    @XmlElementRef(name = "order_id", namespace = "http://b2b.tracfone.com/PhoneServices", type = JAXBElement.class, required = false)
    protected JAXBElement<String> orderId;
    @XmlElementRef(name = "rma_id", namespace = "http://b2b.tracfone.com/PhoneServices", type = JAXBElement.class, required = false)
    protected JAXBElement<String> rmaId;
    @XmlElementRef(name = "order_type", namespace = "http://b2b.tracfone.com/PhoneServices", type = JAXBElement.class, required = false)
    protected JAXBElement<String> orderType;
    @XmlElementRef(name = "in_pin_status", namespace = "http://b2b.tracfone.com/PhoneServices", type = JAXBElement.class, required = false)
    protected JAXBElement<PinStatusTable> inPinStatus;

    /**
     * Gets the value of the orderId property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOrderId() {
        return orderId;
    }

    /**
     * Sets the value of the orderId property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOrderId(JAXBElement<String> value) {
        this.orderId = value;
    }

    /**
     * Gets the value of the rmaId property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getRmaId() {
        return rmaId;
    }

    /**
     * Sets the value of the rmaId property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setRmaId(JAXBElement<String> value) {
        this.rmaId = value;
    }

    /**
     * Gets the value of the orderType property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getOrderType() {
        return orderType;
    }

    /**
     * Sets the value of the orderType property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setOrderType(JAXBElement<String> value) {
        this.orderType = value;
    }

    /**
     * Gets the value of the inPinStatus property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link PinStatusTable }{@code >}
     *     
     */
    public JAXBElement<PinStatusTable> getInPinStatus() {
        return inPinStatus;
    }

    /**
     * Sets the value of the inPinStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link PinStatusTable }{@code >}
     *     
     */
    public void setInPinStatus(JAXBElement<PinStatusTable> value) {
        this.inPinStatus = value;
    }

}
