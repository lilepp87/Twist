
package com.tracfone.b2b.accountservices;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.commontypes.BaseResponseType;
import com.tracfone.paymentcommontypes.CreditCardDisplayType;


/**
 * <p>Java class for CreditCardListResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CreditCardListResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/CommonTypes}BaseResponseType">
 *       &lt;sequence>
 *         &lt;element name="creditCardInfo" type="{http://www.tracfone.com/PaymentCommonTypes}CreditCardDisplayType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreditCardListResponseType", propOrder = {
    "creditCardInfo"
})
public class CreditCardListResponseType
    extends BaseResponseType
{

    protected List<CreditCardDisplayType> creditCardInfo;

    /**
     * Gets the value of the creditCardInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the creditCardInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCreditCardInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CreditCardDisplayType }
     * 
     * 
     */
    public List<CreditCardDisplayType> getCreditCardInfo() {
        if (creditCardInfo == null) {
            creditCardInfo = new ArrayList<CreditCardDisplayType>();
        }
        return this.creditCardInfo;
    }

}
