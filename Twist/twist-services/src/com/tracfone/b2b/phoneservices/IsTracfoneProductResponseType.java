
package com.tracfone.b2b.phoneservices;

import java.math.BigDecimal;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IsTracfoneProductResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IsTracfoneProductResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="resp_items" type="{http://b2b.tracfone.com/PhoneServices}item_table" minOccurs="0"/>
 *         &lt;element name="err_num" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="err_msg" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IsTracfoneProductResponseType", propOrder = {
    "respItems",
    "errNum",
    "errMsg"
})
public class IsTracfoneProductResponseType {

    @XmlElementRef(name = "resp_items", namespace = "http://b2b.tracfone.com/PhoneServices", type = JAXBElement.class, required = false)
    protected JAXBElement<ItemTable> respItems;
    @XmlElementRef(name = "err_num", namespace = "http://b2b.tracfone.com/PhoneServices", type = JAXBElement.class, required = false)
    protected JAXBElement<BigDecimal> errNum;
    @XmlElementRef(name = "err_msg", namespace = "http://b2b.tracfone.com/PhoneServices", type = JAXBElement.class, required = false)
    protected JAXBElement<String> errMsg;

    /**
     * Gets the value of the respItems property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link ItemTable }{@code >}
     *     
     */
    public JAXBElement<ItemTable> getRespItems() {
        return respItems;
    }

    /**
     * Sets the value of the respItems property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link ItemTable }{@code >}
     *     
     */
    public void setRespItems(JAXBElement<ItemTable> value) {
        this.respItems = value;
    }

    /**
     * Gets the value of the errNum property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public JAXBElement<BigDecimal> getErrNum() {
        return errNum;
    }

    /**
     * Sets the value of the errNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}
     *     
     */
    public void setErrNum(JAXBElement<BigDecimal> value) {
        this.errNum = value;
    }

    /**
     * Gets the value of the errMsg property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public JAXBElement<String> getErrMsg() {
        return errMsg;
    }

    /**
     * Sets the value of the errMsg property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link String }{@code >}
     *     
     */
    public void setErrMsg(JAXBElement<String> value) {
        this.errMsg = value;
    }

}
