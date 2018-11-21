
package com.tracfone.b2b.accountservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.BaseRequestType;
import com.tracfone.securitycommontypes.AuthenticationCredentialsType;


/**
 * <p>Java class for RemoveEsnFromAccountRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RemoveEsnFromAccountRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/CommonTypes}BaseRequestType">
 *       &lt;sequence>
 *         &lt;element name="credentials" type="{http://www.tracfone.com/SecurityCommonTypes}AuthenticationCredentialsType"/>
 *         &lt;element name="esn" type="{http://www.tracfone.com/PhoneCommonTypes}ESNType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RemoveEsnFromAccountRequestType", propOrder = {
    "credentials",
    "esn"
})
public class RemoveEsnFromAccountRequestType
    extends BaseRequestType
{

    @XmlElement(required = true)
    protected AuthenticationCredentialsType credentials;
    @XmlElement(required = true)
    protected String esn;

    /**
     * Gets the value of the credentials property.
     * 
     * @return
     *     possible object is
     *     {@link AuthenticationCredentialsType }
     *     
     */
    public AuthenticationCredentialsType getCredentials() {
        return credentials;
    }

    /**
     * Sets the value of the credentials property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthenticationCredentialsType }
     *     
     */
    public void setCredentials(AuthenticationCredentialsType value) {
        this.credentials = value;
    }

    /**
     * Gets the value of the esn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsn() {
        return esn;
    }

    /**
     * Sets the value of the esn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsn(String value) {
        this.esn = value;
    }

}
