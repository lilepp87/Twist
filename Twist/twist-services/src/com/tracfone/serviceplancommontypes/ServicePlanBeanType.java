
package com.tracfone.serviceplancommontypes;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.KeyValuePairType;


/**
 * <p>Java class for ServicePlanBeanType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServicePlanBeanType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="servicePlan" type="{http://www.tracfone.com/ServicePlanCommonTypes}ServicePlanType"/>
 *         &lt;element name="billingPlans" type="{http://www.tracfone.com/ServicePlanCommonTypes}BillingPlanType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="features" type="{http://www.tracfone.com/ServicePlanCommonTypes}FeatureType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="partClasses" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="carrierFeatures" type="{http://www.tracfone.com/CommonTypes}KeyValuePairType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServicePlanBeanType", propOrder = {
    "servicePlan",
    "billingPlans",
    "features",
    "partClasses",
    "carrierFeatures"
})
public class ServicePlanBeanType {

    @XmlElement(required = true)
    protected ServicePlanType servicePlan;
    protected List<BillingPlanType> billingPlans;
    protected List<FeatureType> features;
    protected List<String> partClasses;
    protected List<KeyValuePairType> carrierFeatures;

    /**
     * Gets the value of the servicePlan property.
     * 
     * @return
     *     possible object is
     *     {@link ServicePlanType }
     *     
     */
    public ServicePlanType getServicePlan() {
        return servicePlan;
    }

    /**
     * Sets the value of the servicePlan property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServicePlanType }
     *     
     */
    public void setServicePlan(ServicePlanType value) {
        this.servicePlan = value;
    }

    /**
     * Gets the value of the billingPlans property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the billingPlans property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBillingPlans().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BillingPlanType }
     * 
     * 
     */
    public List<BillingPlanType> getBillingPlans() {
        if (billingPlans == null) {
            billingPlans = new ArrayList<BillingPlanType>();
        }
        return this.billingPlans;
    }

    /**
     * Gets the value of the features property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the features property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFeatures().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FeatureType }
     * 
     * 
     */
    public List<FeatureType> getFeatures() {
        if (features == null) {
            features = new ArrayList<FeatureType>();
        }
        return this.features;
    }

    /**
     * Gets the value of the partClasses property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the partClasses property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPartClasses().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getPartClasses() {
        if (partClasses == null) {
            partClasses = new ArrayList<String>();
        }
        return this.partClasses;
    }

    /**
     * Gets the value of the carrierFeatures property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the carrierFeatures property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCarrierFeatures().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link KeyValuePairType }
     * 
     * 
     */
    public List<KeyValuePairType> getCarrierFeatures() {
        if (carrierFeatures == null) {
            carrierFeatures = new ArrayList<KeyValuePairType>();
        }
        return this.carrierFeatures;
    }

}
