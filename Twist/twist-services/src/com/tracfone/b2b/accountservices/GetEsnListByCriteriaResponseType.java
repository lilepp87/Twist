
package com.tracfone.b2b.accountservices;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.BaseResponseType;


/**
 * <p>Java class for GetEsnListByCriteriaResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetEsnListByCriteriaResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/CommonTypes}BaseResponseType">
 *       &lt;sequence>
 *         &lt;element name="accountDevice" type="{http://b2b.tracfone.com/AccountServices}EsnQueryCriteria" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetEsnListByCriteriaResponseType", propOrder = {
    "accountDevice"
})
public class GetEsnListByCriteriaResponseType
    extends BaseResponseType
{

    protected List<EsnQueryCriteria> accountDevice;

    /**
     * Gets the value of the accountDevice property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the accountDevice property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAccountDevice().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EsnQueryCriteria }
     * 
     * 
     */
    public List<EsnQueryCriteria> getAccountDevice() {
        if (accountDevice == null) {
            accountDevice = new ArrayList<EsnQueryCriteria>();
        }
        return this.accountDevice;
    }

}
