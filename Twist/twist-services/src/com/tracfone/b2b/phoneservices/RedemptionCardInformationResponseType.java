
package com.tracfone.b2b.phoneservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.BaseResponseType;


/**
 * <p>Java class for RedemptionCardInformationResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RedemptionCardInformationResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/CommonTypes}BaseResponseType">
 *       &lt;sequence>
 *         &lt;element name="redemptionCard" type="{http://b2b.tracfone.com/PhoneServices}RedemptionCardType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RedemptionCardInformationResponseType", propOrder = {
    "redemptionCard"
})
public class RedemptionCardInformationResponseType
    extends BaseResponseType
{

    @XmlElement(required = true)
    protected RedemptionCardType redemptionCard;

    /**
     * Gets the value of the redemptionCard property.
     * 
     * @return
     *     possible object is
     *     {@link RedemptionCardType }
     *     
     */
    public RedemptionCardType getRedemptionCard() {
        return redemptionCard;
    }

    /**
     * Sets the value of the redemptionCard property.
     * 
     * @param value
     *     allowed object is
     *     {@link RedemptionCardType }
     *     
     */
    public void setRedemptionCard(RedemptionCardType value) {
        this.redemptionCard = value;
    }

}
