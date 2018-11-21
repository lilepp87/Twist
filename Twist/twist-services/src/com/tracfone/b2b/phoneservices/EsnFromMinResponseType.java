
package com.tracfone.b2b.phoneservices;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.BaseResponseType;
import com.tracfone.commontypes.KeyValuePairType;
import com.tracfone.commontypes.TracfoneBrandType;


/**
 * <p>Java class for EsnFromMinResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EsnFromMinResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/CommonTypes}BaseResponseType">
 *       &lt;sequence>
 *         &lt;element name="esn" type="{http://www.tracfone.com/PhoneCommonTypes}ESNType" minOccurs="0"/>
 *         &lt;element name="brandName" type="{http://www.tracfone.com/CommonTypes}TracfoneBrandType" minOccurs="0"/>
 *         &lt;element name="EsnAttributesList" type="{http://www.tracfone.com/CommonTypes}KeyValuePairType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EsnFromMinResponseType", propOrder = {
    "esn",
    "brandName",
    "esnAttributesList"
})
public class EsnFromMinResponseType
    extends BaseResponseType
{

    protected String esn;
    @XmlSchemaType(name = "string")
    protected TracfoneBrandType brandName;
    @XmlElement(name = "EsnAttributesList")
    protected List<KeyValuePairType> esnAttributesList;

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

    /**
     * Gets the value of the brandName property.
     * 
     * @return
     *     possible object is
     *     {@link TracfoneBrandType }
     *     
     */
    public TracfoneBrandType getBrandName() {
        return brandName;
    }

    /**
     * Sets the value of the brandName property.
     * 
     * @param value
     *     allowed object is
     *     {@link TracfoneBrandType }
     *     
     */
    public void setBrandName(TracfoneBrandType value) {
        this.brandName = value;
    }

    /**
     * Gets the value of the esnAttributesList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the esnAttributesList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEsnAttributesList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link KeyValuePairType }
     * 
     * 
     */
    public List<KeyValuePairType> getEsnAttributesList() {
        if (esnAttributesList == null) {
            esnAttributesList = new ArrayList<KeyValuePairType>();
        }
        return this.esnAttributesList;
    }

}
