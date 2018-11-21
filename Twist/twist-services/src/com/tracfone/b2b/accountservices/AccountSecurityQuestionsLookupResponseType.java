
package com.tracfone.b2b.accountservices;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.accountcommontypes.AccountSecurityQuestionType;
import com.tracfone.commontypes.BaseResponseType;


/**
 * <p>Java class for AccountSecurityQuestionsLookupResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AccountSecurityQuestionsLookupResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/CommonTypes}BaseResponseType">
 *       &lt;sequence>
 *         &lt;element name="securityQuestion" type="{http://www.tracfone.com/AccountCommonTypes}AccountSecurityQuestionType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountSecurityQuestionsLookupResponseType", propOrder = {
    "securityQuestion"
})
public class AccountSecurityQuestionsLookupResponseType
    extends BaseResponseType
{

    @XmlElement(required = true)
    protected List<AccountSecurityQuestionType> securityQuestion;

    /**
     * Gets the value of the securityQuestion property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the securityQuestion property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSecurityQuestion().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AccountSecurityQuestionType }
     * 
     * 
     */
    public List<AccountSecurityQuestionType> getSecurityQuestion() {
        if (securityQuestion == null) {
            securityQuestion = new ArrayList<AccountSecurityQuestionType>();
        }
        return this.securityQuestion;
    }

}
