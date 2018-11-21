
package com.tracfone.b2b.phoneservices;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for item_table complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="item_table">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="in_item" type="{http://b2b.tracfone.com/PhoneServices}item_record" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "item_table", propOrder = {
    "inItem"
})
public class ItemTable {

    @XmlElement(name = "in_item", nillable = true)
    protected List<ItemRecord> inItem;

    /**
     * Gets the value of the inItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the inItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ItemRecord }
     * 
     * 
     */
    public List<ItemRecord> getInItem() {
        if (inItem == null) {
            inItem = new ArrayList<ItemRecord>();
        }
        return this.inItem;
    }

}
