
package com.tracfone.b2b.accountservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.BaseResponseType;


/**
 * <p>Java class for AccountUpdateResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AccountUpdateResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/CommonTypes}BaseResponseType">
 *       &lt;sequence>
 *         &lt;element name="webUserObjId" type="{http://www.tracfone.com/CommonTypes}ObjectIdType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountUpdateResponseType", propOrder = {
    "webUserObjId"
})
public class AccountUpdateResponseType
    extends BaseResponseType
{

    @XmlElement(required = true)
    protected String webUserObjId;

    /**
     * Gets the value of the webUserObjId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWebUserObjId() {
        return webUserObjId;
    }

    /**
     * Sets the value of the webUserObjId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWebUserObjId(String value) {
        this.webUserObjId = value;
    }

}
