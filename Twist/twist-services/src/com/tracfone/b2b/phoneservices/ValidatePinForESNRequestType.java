
package com.tracfone.b2b.phoneservices;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.BaseRequestType;


/**
 * <p>Java class for ValidatePinForESNRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ValidatePinForESNRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/CommonTypes}BaseRequestType">
 *       &lt;sequence>
 *         &lt;element name="esn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="number_of_redcards" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pincards" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ValidatePinForESNRequestType", propOrder = {
    "esn",
    "numberOfRedcards",
    "pincards"
})
public class ValidatePinForESNRequestType
    extends BaseRequestType
{

    @XmlElement(required = true)
    protected String esn;
    @XmlElement(name = "number_of_redcards", required = true)
    protected String numberOfRedcards;
    @XmlElement(required = true)
    protected List<String> pincards;

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
     * Gets the value of the numberOfRedcards property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumberOfRedcards() {
        return numberOfRedcards;
    }

    /**
     * Sets the value of the numberOfRedcards property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumberOfRedcards(String value) {
        this.numberOfRedcards = value;
    }

    /**
     * Gets the value of the pincards property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pincards property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPincards().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getPincards() {
        if (pincards == null) {
            pincards = new ArrayList<String>();
        }
        return this.pincards;
    }

}
