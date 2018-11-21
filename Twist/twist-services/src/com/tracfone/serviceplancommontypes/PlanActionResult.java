
package com.tracfone.serviceplancommontypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PlanActionResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PlanActionResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="planIdentifier" type="{http://www.tracfone.com/ServicePlanCommonTypes}PlanIdentificationType"/>
 *         &lt;element name="action" type="{http://www.tracfone.com/ServicePlanCommonTypes}PlanAction"/>
 *         &lt;element name="displayWarning" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="actionWarning" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PlanActionResult", propOrder = {
    "planIdentifier",
    "action",
    "displayWarning",
    "actionWarning"
})
public class PlanActionResult {

    @XmlElement(required = true)
    protected PlanIdentificationType planIdentifier;
    @XmlElement(required = true)
    protected PlanAction action;
    protected String displayWarning;
    protected String actionWarning;

    /**
     * Gets the value of the planIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link PlanIdentificationType }
     *     
     */
    public PlanIdentificationType getPlanIdentifier() {
        return planIdentifier;
    }

    /**
     * Sets the value of the planIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link PlanIdentificationType }
     *     
     */
    public void setPlanIdentifier(PlanIdentificationType value) {
        this.planIdentifier = value;
    }

    /**
     * Gets the value of the action property.
     * 
     * @return
     *     possible object is
     *     {@link PlanAction }
     *     
     */
    public PlanAction getAction() {
        return action;
    }

    /**
     * Sets the value of the action property.
     * 
     * @param value
     *     allowed object is
     *     {@link PlanAction }
     *     
     */
    public void setAction(PlanAction value) {
        this.action = value;
    }

    /**
     * Gets the value of the displayWarning property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisplayWarning() {
        return displayWarning;
    }

    /**
     * Sets the value of the displayWarning property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisplayWarning(String value) {
        this.displayWarning = value;
    }

    /**
     * Gets the value of the actionWarning property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActionWarning() {
        return actionWarning;
    }

    /**
     * Sets the value of the actionWarning property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActionWarning(String value) {
        this.actionWarning = value;
    }

}
