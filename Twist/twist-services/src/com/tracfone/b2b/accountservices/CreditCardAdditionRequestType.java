
package com.tracfone.b2b.accountservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.BaseRequestType;
import com.tracfone.paymentcommontypes.CreditCardInfoType;
import com.tracfone.phonecommontypes.DeviceCredentialsType;


/**
 * <p>Java class for CreditCardAdditionRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreditCardAdditionRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/CommonTypes}BaseRequestType">
 *       &lt;sequence>
 *         &lt;element name="deviceCredentials" type="{http://www.tracfone.com/PhoneCommonTypes}DeviceCredentialsType"/>
 *         &lt;element name="creditCardInfo" type="{http://www.tracfone.com/PaymentCommonTypes}CreditCardInfoType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreditCardAdditionRequestType", propOrder = {
    "deviceCredentials",
    "creditCardInfo"
})
public class CreditCardAdditionRequestType
    extends BaseRequestType
{

    @XmlElement(required = true)
    protected DeviceCredentialsType deviceCredentials;
    @XmlElement(required = true)
    protected CreditCardInfoType creditCardInfo;

    /**
     * Gets the value of the deviceCredentials property.
     * 
     * @return
     *     possible object is
     *     {@link DeviceCredentialsType }
     *     
     */
    public DeviceCredentialsType getDeviceCredentials() {
        return deviceCredentials;
    }

    /**
     * Sets the value of the deviceCredentials property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeviceCredentialsType }
     *     
     */
    public void setDeviceCredentials(DeviceCredentialsType value) {
        this.deviceCredentials = value;
    }

    /**
     * Gets the value of the creditCardInfo property.
     * 
     * @return
     *     possible object is
     *     {@link CreditCardInfoType }
     *     
     */
    public CreditCardInfoType getCreditCardInfo() {
        return creditCardInfo;
    }

    /**
     * Sets the value of the creditCardInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreditCardInfoType }
     *     
     */
    public void setCreditCardInfo(CreditCardInfoType value) {
        this.creditCardInfo = value;
    }

}
