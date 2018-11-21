
package com.tracfone.b2b.accountservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.BaseRequestType;
import com.tracfone.securitycommontypes.AuthenticationCredentialsTypeMB;


/**
 * <p>Java class for AccountValidationRequestTypeMB complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AccountValidationRequestTypeMB">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/CommonTypes}BaseRequestType">
 *       &lt;sequence>
 *         &lt;element name="MBCredentials" type="{http://www.tracfone.com/SecurityCommonTypes}AuthenticationCredentialsTypeMB"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountValidationRequestTypeMB", propOrder = {
    "mbCredentials"
})
public class AccountValidationRequestTypeMB
    extends BaseRequestType
{

    @XmlElement(name = "MBCredentials", required = true)
    protected AuthenticationCredentialsTypeMB mbCredentials;

    /**
     * Gets the value of the mbCredentials property.
     * 
     * @return
     *     possible object is
     *     {@link AuthenticationCredentialsTypeMB }
     *     
     */
    public AuthenticationCredentialsTypeMB getMBCredentials() {
        return mbCredentials;
    }

    /**
     * Sets the value of the mbCredentials property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthenticationCredentialsTypeMB }
     *     
     */
    public void setMBCredentials(AuthenticationCredentialsTypeMB value) {
        this.mbCredentials = value;
    }

}
