
package com.tracfone.b2b.accountservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.BaseResponseType;


/**
 * <p>Java class for AccountCreationResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AccountCreationResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/CommonTypes}BaseResponseType">
 *       &lt;sequence>
 *         &lt;element name="webUserObjid" type="{http://www.tracfone.com/CommonTypes}ObjectIdType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountCreationResponseType", propOrder = {
    "webUserObjid"
})
public class AccountCreationResponseType
    extends BaseResponseType
{

    @XmlElement(required = true)
    protected String webUserObjid;

    /**
     * Gets the value of the webUserObjid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWebUserObjid() {
        return webUserObjid;
    }

    /**
     * Sets the value of the webUserObjid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWebUserObjid(String value) {
        this.webUserObjid = value;
    }

}
