
package com.tracfone.b2b.accountservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.BaseRequestType;
import com.tracfone.commontypes.MobileRequestType;


/**
 * <p>Java class for AccountSecurityQuestionsLookupRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AccountSecurityQuestionsLookupRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/CommonTypes}BaseRequestType">
 *       &lt;sequence>
 *         &lt;element name="consumerInfo" type="{http://www.tracfone.com/CommonTypes}MobileRequestType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountSecurityQuestionsLookupRequestType", propOrder = {
    "consumerInfo"
})
public class AccountSecurityQuestionsLookupRequestType
    extends BaseRequestType
{

    protected MobileRequestType consumerInfo;

    /**
     * Gets the value of the consumerInfo property.
     * 
     * @return
     *     possible object is
     *     {@link MobileRequestType }
     *     
     */
    public MobileRequestType getConsumerInfo() {
        return consumerInfo;
    }

    /**
     * Sets the value of the consumerInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link MobileRequestType }
     *     
     */
    public void setConsumerInfo(MobileRequestType value) {
        this.consumerInfo = value;
    }

}
