
package com.tracfone.b2b.phoneservices;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for pin_status_table complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pin_status_table">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="refund_items" type="{http://b2b.tracfone.com/PhoneServices}pin_status_record" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pin_status_table", propOrder = {
    "refundItems"
})
public class PinStatusTable {

    @XmlElement(name = "refund_items", nillable = true)
    protected List<PinStatusRecord> refundItems;

    /**
     * Gets the value of the refundItems property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the refundItems property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRefundItems().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PinStatusRecord }
     * 
     * 
     */
    public List<PinStatusRecord> getRefundItems() {
        if (refundItems == null) {
            refundItems = new ArrayList<PinStatusRecord>();
        }
        return this.refundItems;
    }

}
