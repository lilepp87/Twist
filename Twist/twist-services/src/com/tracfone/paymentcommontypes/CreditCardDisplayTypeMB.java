
package com.tracfone.paymentcommontypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CreditCardDisplayTypeMB complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreditCardDisplayTypeMB">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/PaymentCommonTypes}CreditCardDisplayType">
 *       &lt;sequence>
 *         &lt;element name="isDefault" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="mobileTANDC" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreditCardDisplayTypeMB", propOrder = {
    "isDefault",
    "mobileTANDC"
})
public class CreditCardDisplayTypeMB
    extends CreditCardDisplayType
{

    @XmlElement(required = true)
    protected String isDefault;
    @XmlElement(required = true)
    protected String mobileTANDC;

    /**
     * Gets the value of the isDefault property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsDefault() {
        return isDefault;
    }

    /**
     * Sets the value of the isDefault property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsDefault(String value) {
        this.isDefault = value;
    }

    /**
     * Gets the value of the mobileTANDC property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMobileTANDC() {
        return mobileTANDC;
    }

    /**
     * Sets the value of the mobileTANDC property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMobileTANDC(String value) {
        this.mobileTANDC = value;
    }

}
