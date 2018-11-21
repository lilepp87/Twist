
package com.tracfone.b2b.phoneservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.BaseResponseType;
import com.tracfone.serviceplancommontypes.PlanPurchaseDetailInfoType;


/**
 * <p>Java class for PurchaseDetailsResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PurchaseDetailsResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/CommonTypes}BaseResponseType">
 *       &lt;sequence>
 *         &lt;element name="purchaseDetails" type="{http://www.tracfone.com/ServicePlanCommonTypes}PlanPurchaseDetailInfoType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PurchaseDetailsResponseType", propOrder = {
    "purchaseDetails"
})
public class PurchaseDetailsResponseType
    extends BaseResponseType
{

    @XmlElement(required = true)
    protected PlanPurchaseDetailInfoType purchaseDetails;

    /**
     * Gets the value of the purchaseDetails property.
     * 
     * @return
     *     possible object is
     *     {@link PlanPurchaseDetailInfoType }
     *     
     */
    public PlanPurchaseDetailInfoType getPurchaseDetails() {
        return purchaseDetails;
    }

    /**
     * Sets the value of the purchaseDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link PlanPurchaseDetailInfoType }
     *     
     */
    public void setPurchaseDetails(PlanPurchaseDetailInfoType value) {
        this.purchaseDetails = value;
    }

}
