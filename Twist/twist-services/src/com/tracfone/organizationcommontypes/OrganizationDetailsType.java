
package com.tracfone.organizationcommontypes;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.AddressType;
import com.tracfone.commontypes.KeyValuePairType;


/**
 * <p>Java class for OrganizationDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrganizationDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="organizationName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="organizationType" type="{http://www.tracfone.com/OrganizationCommonTypes}OrganizationTypeType"/>
 *         &lt;element name="taxId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="taxExemptCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DUNS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="businessCategory" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="billingAddress" type="{http://www.tracfone.com/CommonTypes}AddressType"/>
 *         &lt;element name="shippingAddress" type="{http://www.tracfone.com/CommonTypes}AddressType"/>
 *         &lt;element name="extra" type="{http://www.tracfone.com/CommonTypes}KeyValuePairType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrganizationDetailsType", propOrder = {
    "organizationName",
    "organizationType",
    "taxId",
    "taxExemptCode",
    "duns",
    "businessCategory",
    "billingAddress",
    "shippingAddress",
    "extra"
})
public class OrganizationDetailsType {

    @XmlElement(required = true)
    protected String organizationName;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected OrganizationTypeType organizationType;
    @XmlElement(required = true)
    protected String taxId;
    @XmlElement(required = true)
    protected String taxExemptCode;
    @XmlElement(name = "DUNS", required = true)
    protected String duns;
    @XmlElement(required = true)
    protected String businessCategory;
    @XmlElement(required = true)
    protected AddressType billingAddress;
    @XmlElement(required = true)
    protected AddressType shippingAddress;
    protected List<KeyValuePairType> extra;

    /**
     * Gets the value of the organizationName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrganizationName() {
        return organizationName;
    }

    /**
     * Sets the value of the organizationName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrganizationName(String value) {
        this.organizationName = value;
    }

    /**
     * Gets the value of the organizationType property.
     * 
     * @return
     *     possible object is
     *     {@link OrganizationTypeType }
     *     
     */
    public OrganizationTypeType getOrganizationType() {
        return organizationType;
    }

    /**
     * Sets the value of the organizationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrganizationTypeType }
     *     
     */
    public void setOrganizationType(OrganizationTypeType value) {
        this.organizationType = value;
    }

    /**
     * Gets the value of the taxId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxId() {
        return taxId;
    }

    /**
     * Sets the value of the taxId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxId(String value) {
        this.taxId = value;
    }

    /**
     * Gets the value of the taxExemptCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxExemptCode() {
        return taxExemptCode;
    }

    /**
     * Sets the value of the taxExemptCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxExemptCode(String value) {
        this.taxExemptCode = value;
    }

    /**
     * Gets the value of the duns property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDUNS() {
        return duns;
    }

    /**
     * Sets the value of the duns property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDUNS(String value) {
        this.duns = value;
    }

    /**
     * Gets the value of the businessCategory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessCategory() {
        return businessCategory;
    }

    /**
     * Sets the value of the businessCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessCategory(String value) {
        this.businessCategory = value;
    }

    /**
     * Gets the value of the billingAddress property.
     * 
     * @return
     *     possible object is
     *     {@link AddressType }
     *     
     */
    public AddressType getBillingAddress() {
        return billingAddress;
    }

    /**
     * Sets the value of the billingAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressType }
     *     
     */
    public void setBillingAddress(AddressType value) {
        this.billingAddress = value;
    }

    /**
     * Gets the value of the shippingAddress property.
     * 
     * @return
     *     possible object is
     *     {@link AddressType }
     *     
     */
    public AddressType getShippingAddress() {
        return shippingAddress;
    }

    /**
     * Sets the value of the shippingAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressType }
     *     
     */
    public void setShippingAddress(AddressType value) {
        this.shippingAddress = value;
    }

    /**
     * Gets the value of the extra property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the extra property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExtra().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link KeyValuePairType }
     * 
     * 
     */
    public List<KeyValuePairType> getExtra() {
        if (extra == null) {
            extra = new ArrayList<KeyValuePairType>();
        }
        return this.extra;
    }

}
