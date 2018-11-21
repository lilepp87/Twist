
package com.tracfone.organizationcommontypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrganizationStructureType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrganizationStructureType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="orgId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="type" type="{http://www.tracfone.com/OrganizationCommonTypes}OrganizationTypeType"/>
 *         &lt;element name="accounts" type="{http://www.tracfone.com/OrganizationCommonTypes}OrganizationAccountListType" minOccurs="0"/>
 *         &lt;element name="division" type="{http://www.tracfone.com/OrganizationCommonTypes}DivisionType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrganizationStructureType", propOrder = {
    "orgId",
    "type",
    "accounts",
    "division"
})
@XmlSeeAlso({
    DivisionType.class
})
public class OrganizationStructureType {

    @XmlElement(required = true)
    protected String orgId;
    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected OrganizationTypeType type;
    protected OrganizationAccountListType accounts;
    protected DivisionType division;

    /**
     * Gets the value of the orgId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgId() {
        return orgId;
    }

    /**
     * Sets the value of the orgId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgId(String value) {
        this.orgId = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link OrganizationTypeType }
     *     
     */
    public OrganizationTypeType getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrganizationTypeType }
     *     
     */
    public void setType(OrganizationTypeType value) {
        this.type = value;
    }

    /**
     * Gets the value of the accounts property.
     * 
     * @return
     *     possible object is
     *     {@link OrganizationAccountListType }
     *     
     */
    public OrganizationAccountListType getAccounts() {
        return accounts;
    }

    /**
     * Sets the value of the accounts property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrganizationAccountListType }
     *     
     */
    public void setAccounts(OrganizationAccountListType value) {
        this.accounts = value;
    }

    /**
     * Gets the value of the division property.
     * 
     * @return
     *     possible object is
     *     {@link DivisionType }
     *     
     */
    public DivisionType getDivision() {
        return division;
    }

    /**
     * Sets the value of the division property.
     * 
     * @param value
     *     allowed object is
     *     {@link DivisionType }
     *     
     */
    public void setDivision(DivisionType value) {
        this.division = value;
    }

}
