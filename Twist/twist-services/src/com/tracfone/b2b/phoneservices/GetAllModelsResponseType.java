
package com.tracfone.b2b.phoneservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.BaseResponseType;
import com.tracfone.commontypes.HandsetModelListType;


/**
 * <p>Java class for GetAllModelsResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetAllModelsResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/CommonTypes}BaseResponseType">
 *       &lt;sequence>
 *         &lt;element name="handsetModelList" type="{http://www.tracfone.com/CommonTypes}HandsetModelListType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetAllModelsResponseType", propOrder = {
    "handsetModelList"
})
public class GetAllModelsResponseType
    extends BaseResponseType
{

    @XmlElement(required = true)
    protected HandsetModelListType handsetModelList;

    /**
     * Gets the value of the handsetModelList property.
     * 
     * @return
     *     possible object is
     *     {@link HandsetModelListType }
     *     
     */
    public HandsetModelListType getHandsetModelList() {
        return handsetModelList;
    }

    /**
     * Sets the value of the handsetModelList property.
     * 
     * @param value
     *     allowed object is
     *     {@link HandsetModelListType }
     *     
     */
    public void setHandsetModelList(HandsetModelListType value) {
        this.handsetModelList = value;
    }

}
