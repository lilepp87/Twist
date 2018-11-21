
package com.tracfone.b2b.accountservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.accountcommontypes.AccountCredentialsType;
import com.tracfone.commontypes.BaseRequestType;


/**
 * <p>Java class for ResetPasswordRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ResetPasswordRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/CommonTypes}BaseRequestType">
 *       &lt;sequence>
 *         &lt;element name="credentials" type="{http://www.tracfone.com/AccountCommonTypes}AccountCredentialsType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResetPasswordRequestType", propOrder = {
    "credentials"
})
public class ResetPasswordRequestType
    extends BaseRequestType
{

    @XmlElement(required = true)
    protected AccountCredentialsType credentials;

    /**
     * Gets the value of the credentials property.
     * 
     * @return
     *     possible object is
     *     {@link AccountCredentialsType }
     *     
     */
    public AccountCredentialsType getCredentials() {
        return credentials;
    }

    /**
     * Sets the value of the credentials property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountCredentialsType }
     *     
     */
    public void setCredentials(AccountCredentialsType value) {
        this.credentials = value;
    }

}
