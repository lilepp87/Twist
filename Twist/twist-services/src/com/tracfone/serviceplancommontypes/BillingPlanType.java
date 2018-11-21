
package com.tracfone.serviceplancommontypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BillingPlanType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BillingPlanType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="xProgramClass" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="programPara2xSp" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="xProgramName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="xRecurring" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="xSp2ProgramParam" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BillingPlanType", propOrder = {
    "xProgramClass",
    "programPara2XSp",
    "xProgramName",
    "xRecurring",
    "xSp2ProgramParam"
})
public class BillingPlanType {

    @XmlElement(required = true)
    protected String xProgramClass;
    @XmlElement(name = "programPara2xSp", required = true)
    protected String programPara2XSp;
    @XmlElement(required = true)
    protected String xProgramName;
    protected boolean xRecurring;
    @XmlElement(required = true)
    protected String xSp2ProgramParam;

    /**
     * Gets the value of the xProgramClass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXProgramClass() {
        return xProgramClass;
    }

    /**
     * Sets the value of the xProgramClass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXProgramClass(String value) {
        this.xProgramClass = value;
    }

    /**
     * Gets the value of the programPara2XSp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProgramPara2XSp() {
        return programPara2XSp;
    }

    /**
     * Sets the value of the programPara2XSp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProgramPara2XSp(String value) {
        this.programPara2XSp = value;
    }

    /**
     * Gets the value of the xProgramName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXProgramName() {
        return xProgramName;
    }

    /**
     * Sets the value of the xProgramName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXProgramName(String value) {
        this.xProgramName = value;
    }

    /**
     * Gets the value of the xRecurring property.
     * 
     */
    public boolean isXRecurring() {
        return xRecurring;
    }

    /**
     * Sets the value of the xRecurring property.
     * 
     */
    public void setXRecurring(boolean value) {
        this.xRecurring = value;
    }

    /**
     * Gets the value of the xSp2ProgramParam property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXSp2ProgramParam() {
        return xSp2ProgramParam;
    }

    /**
     * Sets the value of the xSp2ProgramParam property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXSp2ProgramParam(String value) {
        this.xSp2ProgramParam = value;
    }

}
