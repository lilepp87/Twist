
package com.tracfone.b2b.accountservices;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.BaseResponseType;


/**
 * <p>Java class for GetAccountSummaryResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetAccountSummaryResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/CommonTypes}BaseResponseType">
 *       &lt;sequence>
 *         &lt;element name="MonthlyPlanCharges" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ESNSummary" type="{http://b2b.tracfone.com/AccountServices}ESNSummary"/>
 *         &lt;element name="groupInfo" type="{http://b2b.tracfone.com/AccountServices}groupInfoType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetAccountSummaryResponseType", propOrder = {
    "monthlyPlanCharges",
    "esnSummary",
    "groupInfo"
})
public class GetAccountSummaryResponseType
    extends BaseResponseType
{

    @XmlElement(name = "MonthlyPlanCharges", required = true)
    protected String monthlyPlanCharges;
    @XmlElement(name = "ESNSummary", required = true)
    protected ESNSummary esnSummary;
    protected GroupInfoType groupInfo;

    /**
     * Gets the value of the monthlyPlanCharges property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMonthlyPlanCharges() {
        return monthlyPlanCharges;
    }

    /**
     * Sets the value of the monthlyPlanCharges property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMonthlyPlanCharges(String value) {
        this.monthlyPlanCharges = value;
    }

    /**
     * Gets the value of the esnSummary property.
     * 
     * @return
     *     possible object is
     *     {@link ESNSummary }
     *     
     */
    public ESNSummary getESNSummary() {
        return esnSummary;
    }

    /**
     * Sets the value of the esnSummary property.
     * 
     * @param value
     *     allowed object is
     *     {@link ESNSummary }
     *     
     */
    public void setESNSummary(ESNSummary value) {
        this.esnSummary = value;
    }

    /**
     * Gets the value of the groupInfo property.
     * 
     * @return
     *     possible object is
     *     {@link GroupInfoType }
     *     
     */
    public GroupInfoType getGroupInfo() {
        return groupInfo;
    }

    /**
     * Sets the value of the groupInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link GroupInfoType }
     *     
     */
    public void setGroupInfo(GroupInfoType value) {
        this.groupInfo = value;
    }

}
