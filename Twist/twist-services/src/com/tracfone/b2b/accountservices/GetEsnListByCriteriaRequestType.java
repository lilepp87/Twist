
package com.tracfone.b2b.accountservices;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.BaseRequestType;
import com.tracfone.commontypes.KeyValuePairType;
import com.tracfone.securitycommontypes.AuthenticationCredentialsType;


/**
 * <p>Java class for GetEsnListByCriteriaRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetEsnListByCriteriaRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/CommonTypes}BaseRequestType">
 *       &lt;sequence>
 *         &lt;element name="authenticationCredentials" type="{http://www.tracfone.com/SecurityCommonTypes}AuthenticationCredentialsType"/>
 *         &lt;element name="filterBy" type="{http://www.tracfone.com/CommonTypes}KeyValuePairType" maxOccurs="unbounded"/>
 *         &lt;element name="orderBy" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="orderDirection">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="ASC"/>
 *               &lt;enumeration value="DESC"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="startIndex" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="maxRecords" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetEsnListByCriteriaRequestType", propOrder = {
    "authenticationCredentials",
    "filterBy",
    "orderBy",
    "orderDirection",
    "startIndex",
    "maxRecords"
})
public class GetEsnListByCriteriaRequestType
    extends BaseRequestType
{

    @XmlElement(required = true)
    protected AuthenticationCredentialsType authenticationCredentials;
    @XmlElement(required = true)
    protected List<KeyValuePairType> filterBy;
    @XmlElement(required = true)
    protected String orderBy;
    @XmlElement(required = true)
    protected String orderDirection;
    protected int startIndex;
    protected int maxRecords;

    /**
     * Gets the value of the authenticationCredentials property.
     * 
     * @return
     *     possible object is
     *     {@link AuthenticationCredentialsType }
     *     
     */
    public AuthenticationCredentialsType getAuthenticationCredentials() {
        return authenticationCredentials;
    }

    /**
     * Sets the value of the authenticationCredentials property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthenticationCredentialsType }
     *     
     */
    public void setAuthenticationCredentials(AuthenticationCredentialsType value) {
        this.authenticationCredentials = value;
    }

    /**
     * Gets the value of the filterBy property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the filterBy property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFilterBy().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link KeyValuePairType }
     * 
     * 
     */
    public List<KeyValuePairType> getFilterBy() {
        if (filterBy == null) {
            filterBy = new ArrayList<KeyValuePairType>();
        }
        return this.filterBy;
    }

    /**
     * Gets the value of the orderBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderBy() {
        return orderBy;
    }

    /**
     * Sets the value of the orderBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderBy(String value) {
        this.orderBy = value;
    }

    /**
     * Gets the value of the orderDirection property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderDirection() {
        return orderDirection;
    }

    /**
     * Sets the value of the orderDirection property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderDirection(String value) {
        this.orderDirection = value;
    }

    /**
     * Gets the value of the startIndex property.
     * 
     */
    public int getStartIndex() {
        return startIndex;
    }

    /**
     * Sets the value of the startIndex property.
     * 
     */
    public void setStartIndex(int value) {
        this.startIndex = value;
    }

    /**
     * Gets the value of the maxRecords property.
     * 
     */
    public int getMaxRecords() {
        return maxRecords;
    }

    /**
     * Sets the value of the maxRecords property.
     * 
     */
    public void setMaxRecords(int value) {
        this.maxRecords = value;
    }

}
