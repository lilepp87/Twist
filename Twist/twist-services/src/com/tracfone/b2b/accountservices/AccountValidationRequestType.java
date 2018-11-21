
package com.tracfone.b2b.accountservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.BaseRequestType;
import com.tracfone.securitycommontypes.AuthenticationCredentialsType;


/**
 * <p>Java class for AccountValidationRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AccountValidationRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/CommonTypes}BaseRequestType">
 *       &lt;sequence>
 *         &lt;element name="authenticationCredentials" type="{http://www.tracfone.com/SecurityCommonTypes}AuthenticationCredentialsType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountValidationRequestType", propOrder = {
    "authenticationCredentials"
})
public class AccountValidationRequestType
    extends BaseRequestType
{

    @XmlElement(required = true)
    protected AuthenticationCredentialsType authenticationCredentials;

    /**
     * Gets the value of the authenticationCredentials property.
     * 
     * @return
     *     possible object is
     *     {@link AuthenticationCredentialsType }
     *     
     */
    public AuthenticationCredentialsType getAuthenticationCredentials() {
        return authenticationCredentials;
    }

    /**
     * Sets the value of the authenticationCredentials property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthenticationCredentialsType }
     *     
     */
    public void setAuthenticationCredentials(AuthenticationCredentialsType value) {
        this.authenticationCredentials = value;
    }

}
