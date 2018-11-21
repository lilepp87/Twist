
package com.tracfone.serviceplancommontypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FeatureType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FeatureType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="featureName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="featureDefId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="objId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="featureChildValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="featureValue" type="{http://www.tracfone.com/ServicePlanCommonTypes}FeatureValueType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FeatureType", propOrder = {
    "featureName",
    "featureDefId",
    "objId",
    "featureChildValue",
    "featureValue"
})
public class FeatureType {

    @XmlElement(required = true)
    protected String featureName;
    @XmlElement(required = true)
    protected String featureDefId;
    @XmlElement(required = true)
    protected String objId;
    @XmlElement(required = true)
    protected String featureChildValue;
    @XmlElement(required = true)
    protected FeatureValueType featureValue;

    /**
     * Gets the value of the featureName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFeatureName() {
        return featureName;
    }

    /**
     * Sets the value of the featureName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFeatureName(String value) {
        this.featureName = value;
    }

    /**
     * Gets the value of the featureDefId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFeatureDefId() {
        return featureDefId;
    }

    /**
     * Sets the value of the featureDefId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFeatureDefId(String value) {
        this.featureDefId = value;
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

    /**
     * Gets the value of the featureValue property.
     * 
     * @return
     *     possible object is
     *     {@link FeatureValueType }
     *     
     */
    public FeatureValueType getFeatureValue() {
        return featureValue;
    }

    /**
     * Sets the value of the featureValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link FeatureValueType }
     *     
     */
    public void setFeatureValue(FeatureValueType value) {
        this.featureValue = value;
    }

}
