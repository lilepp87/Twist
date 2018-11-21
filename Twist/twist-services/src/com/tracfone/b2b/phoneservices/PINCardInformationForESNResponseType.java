
package com.tracfone.b2b.phoneservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.BaseResponseType;
import com.tracfone.serviceplancommontypes.PlanInfoType;


/**
 * <p>Java class for PINCardInformationForESNResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PINCardInformationForESNResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/CommonTypes}BaseResponseType">
 *       &lt;sequence>
 *         &lt;element name="servicePlanInfo" type="{http://www.tracfone.com/ServicePlanCommonTypes}PlanInfoType" minOccurs="0"/>
 *         &lt;element name="hasCardInQueue" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PINCardInformationForESNResponseType", propOrder = {
    "servicePlanInfo",
    "hasCardInQueue"
})
public class PINCardInformationForESNResponseType
    extends BaseResponseType
{

    protected PlanInfoType servicePlanInfo;
    protected boolean hasCardInQueue;

    /**
     * Gets the value of the servicePlanInfo property.
     * 
     * @return
     *     possible object is
     *     {@link PlanInfoType }
     *     
     */
    public PlanInfoType getServicePlanInfo() {
        return servicePlanInfo;
    }

    /**
     * Sets the value of the servicePlanInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link PlanInfoType }
     *     
     */
    public void setServicePlanInfo(PlanInfoType value) {
        this.servicePlanInfo = value;
    }

    /**
     * Gets the value of the hasCardInQueue property.
     * 
     */
    public boolean isHasCardInQueue() {
        return hasCardInQueue;
    }

    /**
     * Sets the value of the hasCardInQueue property.
     * 
     */
    public void setHasCardInQueue(boolean value) {
        this.hasCardInQueue = value;
    }

}
