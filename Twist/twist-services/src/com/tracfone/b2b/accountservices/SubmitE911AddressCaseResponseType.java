
package com.tracfone.b2b.accountservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.BaseResponseType;


/**
 * <p>Java class for SubmitE911AddressCaseResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SubmitE911AddressCaseResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/CommonTypes}BaseResponseType">
 *       &lt;sequence>
 *         &lt;element name="actionItemStatusCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="actionItemObjId" type="{http://www.tracfone.com/CommonTypes}ObjectIdType"/>
 *         &lt;element name="actionItemId" type="{http://www.tracfone.com/CommonTypes}ObjectIdType"/>
 *         &lt;element name="destinationQueue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="igTransStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SubmitE911AddressCaseResponseType", propOrder = {
    "actionItemStatusCode",
    "actionItemObjId",
    "actionItemId",
    "destinationQueue",
    "igTransStatus"
})
public class SubmitE911AddressCaseResponseType
    extends BaseResponseType
{

    protected String actionItemStatusCode;
    @XmlElement(required = true)
    protected String actionItemObjId;
    @XmlElement(required = true)
    protected String actionItemId;
    protected String destinationQueue;
    protected String igTransStatus;

    /**
     * Gets the value of the actionItemStatusCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActionItemStatusCode() {
        return actionItemStatusCode;
    }

    /**
     * Sets the value of the actionItemStatusCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActionItemStatusCode(String value) {
        this.actionItemStatusCode = value;
    }

    /**
     * Gets the value of the actionItemObjId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActionItemObjId() {
        return actionItemObjId;
    }

    /**
     * Sets the value of the actionItemObjId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActionItemObjId(String value) {
        this.actionItemObjId = value;
    }

    /**
     * Gets the value of the actionItemId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActionItemId() {
        return actionItemId;
    }

    /**
     * Sets the value of the actionItemId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActionItemId(String value) {
        this.actionItemId = value;
    }

    /**
     * Gets the value of the destinationQueue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestinationQueue() {
        return destinationQueue;
    }

    /**
     * Sets the value of the destinationQueue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestinationQueue(String value) {
        this.destinationQueue = value;
    }

    /**
     * Gets the value of the igTransStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIgTransStatus() {
        return igTransStatus;
    }

    /**
     * Sets the value of the igTransStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIgTransStatus(String value) {
        this.igTransStatus = value;
    }

}
