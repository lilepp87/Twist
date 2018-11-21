
package com.tracfone.organizationcommontypes;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OrganizationAccountListType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrganizationAccountListType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="accountList">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="b2bAccount" type="{http://www.tracfone.com/OrganizationCommonTypes}B2BAccountType" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrganizationAccountListType", propOrder = {
    "accountList"
})
public class OrganizationAccountListType {

    @XmlElement(required = true)
    protected OrganizationAccountListType.AccountList accountList;

    /**
     * Gets the value of the accountList property.
     * 
     * @return
     *     possible object is
     *     {@link OrganizationAccountListType.AccountList }
     *     
     */
    public OrganizationAccountListType.AccountList getAccountList() {
        return accountList;
    }

    /**
     * Sets the value of the accountList property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrganizationAccountListType.AccountList }
     *     
     */
    public void setAccountList(OrganizationAccountListType.AccountList value) {
        this.accountList = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="b2bAccount" type="{http://www.tracfone.com/OrganizationCommonTypes}B2BAccountType" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "b2BAccount"
    })
    public static class AccountList {

        @XmlElement(name = "b2bAccount", required = true)
        protected List<B2BAccountType> b2BAccount;

        /**
         * Gets the value of the b2BAccount property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the b2BAccount property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getB2BAccount().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link B2BAccountType }
         * 
         * 
         */
        public List<B2BAccountType> getB2BAccount() {
            if (b2BAccount == null) {
                b2BAccount = new ArrayList<B2BAccountType>();
            }
            return this.b2BAccount;
        }

    }

}
