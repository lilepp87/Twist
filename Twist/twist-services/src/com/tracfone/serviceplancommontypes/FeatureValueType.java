
package com.tracfone.serviceplancommontypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FeatureValueType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FeatureValueType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="displayName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="objId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tabletype" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="valueName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="childObjId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="leaf" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="displayOrder" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="parentObjId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="optional" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="featureChildValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FeatureValueType", propOrder = {
    "displayName",
    "objId",
    "description",
    "tabletype",
    "valueName",
    "childObjId",
    "leaf",
    "displayOrder",
    "parentObjId",
    "optional",
    "featureChildValue"
})
public class FeatureValueType {

    @XmlElement(required = true)
    protected String displayName;
    @XmlElement(required = true)
    protected String objId;
    @XmlElement(required = true)
    protected String description;
    @XmlElement(required = true)
    protected String tabletype;
    @XmlElement(required = true)
    protected String valueName;
    @XmlElement(required = true)
    protected String childObjId;
    protected int leaf;
    protected int displayOrder;
    @XmlElement(required = true)
    protected String parentObjId;
    protected boolean optional;
    @XmlElement(required = true)
    protected String featureChildValue;

    /**
     * Gets the value of the displayName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets the value of the displayName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisplayName(String value) {
        this.displayName = value;
    }

    /**
     * Gets the value of the objId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObjId() {
        return objId;
    }

    /**
     * Sets the value of the objId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObjId(String value) {
        this.objId = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the tabletype property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTabletype() {
        return tabletype;
    }

    /**
     * Sets the value of the tabletype property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTabletype(String value) {
        this.tabletype = value;
    }

    /**
     * Gets the value of the valueName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValueName() {
        return valueName;
    }

    /**
     * Sets the value of the valueName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValueName(String value) {
        this.valueName = value;
    }

    /**
     * Gets the value of the childObjId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChildObjId() {
        return childObjId;
    }

    /**
     * Sets the value of the childObjId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChildObjId(String value) {
        this.childObjId = value;
    }

    /**
     * Gets the value of the leaf property.
     * 
     */
    public int getLeaf() {
        return leaf;
    }

    /**
     * Sets the value of the leaf property.
     * 
     */
    public void setLeaf(int value) {
        this.leaf = value;
    }

    /**
     * Gets the value of the displayOrder property.
     * 
     */
    public int getDisplayOrder() {
        return displayOrder;
    }

    /**
     * Sets the value of the displayOrder property.
     * 
     */
    public void setDisplayOrder(int value) {
        this.displayOrder = value;
    }

    /**
     * Gets the value of the parentObjId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getParentObjId() {
        return parentObjId;
    }

    /**
     * Sets the value of the parentObjId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setParentObjId(String value) {
        this.parentObjId = value;
    }

    /**
     * Gets the value of the optional property.
     * 
     */
    public boolean isOptional() {
        return optional;
    }

    /**
     * Sets the value of the optional property.
     * 
     */
    public void setOptional(boolean value) {
        this.optional = value;
    }

    /**
     * Gets the value of the featureChildValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFeatureChildValue() {
        return featureChildValue;
    }

    /**
     * Sets the value of the featureChildValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFeatureChildValue(String value) {
        this.featureChildValue = value;
    }

}
