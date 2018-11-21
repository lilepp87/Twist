
package com.tracfone.b2b.inventoryservices;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.BaseRequestType;


/**
 * <p>Java class for CreateResourceRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreateResourceRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/CommonTypes}BaseRequestType">
 *       &lt;sequence>
 *         &lt;element name="consumerInfo" type="{http://b2b.tracfone.com/InventoryServices}ExtensionType" maxOccurs="unbounded"/>
 *         &lt;element name="resourceOrderItem" type="{http://b2b.tracfone.com/InventoryServices}resourceOrderItemType" maxOccurs="unbounded"/>
 *         &lt;element name="extensionInfo" type="{http://b2b.tracfone.com/InventoryServices}ExtensionType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreateResourceRequestType", propOrder = {
    "consumerInfo",
    "resourceOrderItem",
    "extensionInfo"
})
public class CreateResourceRequestType
    extends BaseRequestType
{

    @XmlElement(required = true)
    protected List<ExtensionType> consumerInfo;
    @XmlElement(required = true)
    protected List<ResourceOrderItemType> resourceOrderItem;
    protected List<ExtensionType> extensionInfo;

    /**
     * Gets the value of the consumerInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the consumerInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConsumerInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExtensionType }
     * 
     * 
     */
    public List<ExtensionType> getConsumerInfo() {
        if (consumerInfo == null) {
            consumerInfo = new ArrayList<ExtensionType>();
        }
        return this.consumerInfo;
    }

    /**
     * Gets the value of the resourceOrderItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the resourceOrderItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResourceOrderItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ResourceOrderItemType }
     * 
     * 
     */
    public List<ResourceOrderItemType> getResourceOrderItem() {
        if (resourceOrderItem == null) {
            resourceOrderItem = new ArrayList<ResourceOrderItemType>();
        }
        return this.resourceOrderItem;
    }

    /**
     * Gets the value of the extensionInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the extensionInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExtensionInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExtensionType }
     * 
     * 
     */
    public List<ExtensionType> getExtensionInfo() {
        if (extensionInfo == null) {
            extensionInfo = new ArrayList<ExtensionType>();
        }
        return this.extensionInfo;
    }

}
