
package com.tracfone.b2b.accountservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.BaseRequestType;


/**
 * <p>Java class for SubmitE911AddressCaseRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SubmitE911AddressCaseRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/CommonTypes}BaseRequestType">
 *       &lt;sequence>
 *         &lt;element name="createTicket" type="{http://b2b.tracfone.com/AccountServices}CreateTicketRequestType"/>
 *         &lt;element name="createCallTrans" type="{http://b2b.tracfone.com/AccountServices}CreateCallTransRequestType"/>
 *         &lt;element name="createActionItemIGTransaction" type="{http://b2b.tracfone.com/AccountServices}CreateActionItemIGTransactionRequestType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SubmitE911AddressCaseRequestType", propOrder = {
    "createTicket",
    "createCallTrans",
    "createActionItemIGTransaction"
})
public class SubmitE911AddressCaseRequestType
    extends BaseRequestType
{

    @XmlElement(required = true)
    protected CreateTicketRequestType createTicket;
    @XmlElement(required = true)
    protected CreateCallTransRequestType createCallTrans;
    @XmlElement(required = true)
    protected CreateActionItemIGTransactionRequestType createActionItemIGTransaction;

    /**
     * Gets the value of the createTicket property.
     * 
     * @return
     *     possible object is
     *     {@link CreateTicketRequestType }
     *     
     */
    public CreateTicketRequestType getCreateTicket() {
        return createTicket;
    }

    /**
     * Sets the value of the createTicket property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreateTicketRequestType }
     *     
     */
    public void setCreateTicket(CreateTicketRequestType value) {
        this.createTicket = value;
    }

    /**
     * Gets the value of the createCallTrans property.
     * 
     * @return
     *     possible object is
     *     {@link CreateCallTransRequestType }
     *     
     */
    public CreateCallTransRequestType getCreateCallTrans() {
        return createCallTrans;
    }

    /**
     * Sets the value of the createCallTrans property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreateCallTransRequestType }
     *     
     */
    public void setCreateCallTrans(CreateCallTransRequestType value) {
        this.createCallTrans = value;
    }

    /**
     * Gets the value of the createActionItemIGTransaction property.
     * 
     * @return
     *     possible object is
     *     {@link CreateActionItemIGTransactionRequestType }
     *     
     */
    public CreateActionItemIGTransactionRequestType getCreateActionItemIGTransaction() {
        return createActionItemIGTransaction;
    }

    /**
     * Sets the value of the createActionItemIGTransaction property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreateActionItemIGTransactionRequestType }
     *     
     */
    public void setCreateActionItemIGTransaction(CreateActionItemIGTransactionRequestType value) {
        this.createActionItemIGTransaction = value;
    }

}
