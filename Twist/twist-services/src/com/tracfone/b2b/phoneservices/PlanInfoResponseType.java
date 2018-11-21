
package com.tracfone.b2b.phoneservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.BaseResponseType;
import com.tracfone.serviceplancommontypes.PlanInfoType;


/**
 * <p>Java class for PlanInfoResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PlanInfoResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/CommonTypes}BaseResponseType">
 *       &lt;sequence>
 *         &lt;element name="availablePlans" type="{http://www.tracfone.com/ServicePlanCommonTypes}PlanInfoType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PlanInfoResponseType", propOrder = {
    "availablePlans"
})
public class PlanInfoResponseType
    extends BaseResponseType
{

    @XmlElement(required = true)
    protected PlanInfoType availablePlans;

    /**
     * Gets the value of the availablePlans property.
     * 
     * @return
     *     possible object is
     *     {@link PlanInfoType }
     *     
     */
    public PlanInfoType getAvailablePlans() {
        return availablePlans;
    }

    /**
     * Sets the value of the availablePlans property.
     * 
     * @param value
     *     allowed object is
     *     {@link PlanInfoType }
     *     
     */
    public void setAvailablePlans(PlanInfoType value) {
        this.availablePlans = value;
    }

}
