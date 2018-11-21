
package com.tracfone.commontypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.b2b.accountservices.AccountCreationRequestType;
import com.tracfone.b2b.accountservices.AccountRecoveryAccountStatusRequestType;
import com.tracfone.b2b.accountservices.AccountSecurityQuestionsLookupRequestType;
import com.tracfone.b2b.accountservices.AccountUpdateRequestType;
import com.tracfone.b2b.accountservices.AccountValidationRequestType;
import com.tracfone.b2b.accountservices.AccountValidationRequestTypeMB;
import com.tracfone.b2b.accountservices.AddEsnToAccountRequestType;
import com.tracfone.b2b.accountservices.AddressInformationRequestType;
import com.tracfone.b2b.accountservices.CreditCardAdditionRequestType;
import com.tracfone.b2b.accountservices.CreditCardAdditionResponseType;
import com.tracfone.b2b.accountservices.CreditCardListRequestType;
import com.tracfone.b2b.accountservices.ForgotPasswordRequestType;
import com.tracfone.b2b.accountservices.GetAccountDetailsRequestType;
import com.tracfone.b2b.accountservices.GetAccountSummaryRequestType;
import com.tracfone.b2b.accountservices.GetEsnListByCriteriaRequestType;
import com.tracfone.b2b.accountservices.RemoveEsnFromAccountRequestType;
import com.tracfone.b2b.accountservices.ResetPasswordRequestType;
import com.tracfone.b2b.accountservices.SubmitE911AddressCaseRequestType;
import com.tracfone.b2b.accountservices.TimeAsCustomerRequestType;


/**
 * <p>Java class for BaseRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BaseRequestType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/CommonTypes}BaseRequestResponseType">
 *       &lt;sequence>
 *         &lt;element name="brandName" type="{http://www.tracfone.com/CommonTypes}TracfoneBrandType"/>
 *         &lt;element name="sourceSystem" type="{http://www.tracfone.com/CommonTypes}SourceSystemType"/>
 *         &lt;element name="language" type="{http://www.tracfone.com/CommonTypes}LanguageType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BaseRequestType", propOrder = {
    "brandName",
    "sourceSystem",
    "language"
})
@XmlSeeAlso({
    AddressInformationRequestType.class,
    ForgotPasswordRequestType.class,
    TimeAsCustomerRequestType.class,
    GetAccountDetailsRequestType.class,
    RemoveEsnFromAccountRequestType.class,
    CreditCardAdditionResponseType.class,
    GetAccountSummaryRequestType.class,
    AddEsnToAccountRequestType.class,
    CreditCardListRequestType.class,
    AccountCreationRequestType.class,
    CreditCardAdditionRequestType.class,
    SubmitE911AddressCaseRequestType.class,
    GetEsnListByCriteriaRequestType.class,
    AccountUpdateRequestType.class,
    AccountRecoveryAccountStatusRequestType.class,
    ResetPasswordRequestType.class,
    AccountSecurityQuestionsLookupRequestType.class,
    AccountValidationRequestType.class,
    AccountValidationRequestTypeMB.class,
    BaseSecureRequestType.class
})
public class BaseRequestType
    extends BaseRequestResponseType
{

    @XmlElement(required = true)
    @XmlSchemaType(name = "string")
    protected TracfoneBrandType brandName;
    @XmlElement(required = true)
    protected String sourceSystem;
    @XmlSchemaType(name = "string")
    protected LanguageType language;

    /**
     * Gets the value of the brandName property.
     * 
     * @return
     *     possible object is
     *     {@link TracfoneBrandType }
     *     
     */
    public TracfoneBrandType getBrandName() {
        return brandName;
    }

    /**
     * Sets the value of the brandName property.
     * 
     * @param value
     *     allowed object is
     *     {@link TracfoneBrandType }
     *     
     */
    public void setBrandName(TracfoneBrandType value) {
        this.brandName = value;
    }

    /**
     * Gets the value of the sourceSystem property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceSystem() {
        return sourceSystem;
    }

    /**
     * Sets the value of the sourceSystem property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceSystem(String value) {
        this.sourceSystem = value;
    }

    /**
     * Gets the value of the language property.
     * 
     * @return
     *     possible object is
     *     {@link LanguageType }
     *     
     */
    public LanguageType getLanguage() {
        return language;
    }

    /**
     * Sets the value of the language property.
     * 
     * @param value
     *     allowed object is
     *     {@link LanguageType }
     *     
     */
    public void setLanguage(LanguageType value) {
        this.language = value;
    }

}
