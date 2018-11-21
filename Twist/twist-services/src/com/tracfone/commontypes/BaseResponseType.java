
package com.tracfone.commontypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import com.tracfone.b2b.accountservices.AccountCreationResponseType;
import com.tracfone.b2b.accountservices.AccountRecoveryAccountStatusResponseType;
import com.tracfone.b2b.accountservices.AccountSecurityQuestionsLookupResponseType;
import com.tracfone.b2b.accountservices.AccountUpdateResponseType;
import com.tracfone.b2b.accountservices.AccountValidationResponseType;
import com.tracfone.b2b.accountservices.AddEsnToAccountResponseType;
import com.tracfone.b2b.accountservices.AddressInformationResponseType;
import com.tracfone.b2b.accountservices.CreditCardListResponseType;
import com.tracfone.b2b.accountservices.ForgotPasswordResponseType;
import com.tracfone.b2b.accountservices.GetAccountDetailsResponseType;
import com.tracfone.b2b.accountservices.GetAccountSummaryResponseType;
import com.tracfone.b2b.accountservices.GetEsnListByCriteriaResponseType;
import com.tracfone.b2b.accountservices.RemoveEsnFromAccountResponseType;
import com.tracfone.b2b.accountservices.ResetPasswordResponseType;
import com.tracfone.b2b.accountservices.SubmitE911AddressCaseResponseType;
import com.tracfone.b2b.accountservices.TimeAsCustomerResponseType;
import com.tracfone.runtimefault.RuntimeFaultMessage;


/**
 * <p>Java class for BaseResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BaseResponseType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tracfone.com/CommonTypes}BaseRequestResponseType">
 *       &lt;sequence minOccurs="0">
 *         &lt;element name="result" type="{http://www.tracfone.com/CommonTypes}ResponseResultType"/>
 *         &lt;element name="serverTransactionId" type="{http://www.tracfone.com/CommonTypes}TransactionTokenType"/>
 *         &lt;element ref="{http://www.tracfone.com/RuntimeFault}RuntimeFaultMessage" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BaseResponseType", propOrder = {
    "result",
    "serverTransactionId",
    "runtimeFaultMessage"
})
@XmlSeeAlso({
    SubmitE911AddressCaseResponseType.class,
    GetEsnListByCriteriaResponseType.class,
    AddEsnToAccountResponseType.class,
    GetAccountDetailsResponseType.class,
    CreditCardListResponseType.class,
    TimeAsCustomerResponseType.class,
    RemoveEsnFromAccountResponseType.class,
    AddressInformationResponseType.class,
    AccountSecurityQuestionsLookupResponseType.class,
    ResetPasswordResponseType.class,
    ForgotPasswordResponseType.class,
    AccountValidationResponseType.class,
    GetAccountSummaryResponseType.class,
    AccountCreationResponseType.class,
    AccountUpdateResponseType.class,
    AccountRecoveryAccountStatusResponseType.class
})
public class BaseResponseType
    extends BaseRequestResponseType
{

    protected ResponseResultType result;
    protected String serverTransactionId;
    @XmlElement(name = "RuntimeFaultMessage", namespace = "http://www.tracfone.com/RuntimeFault")
    protected RuntimeFaultMessage runtimeFaultMessage;

    /**
     * Gets the value of the result property.
     * 
     * @return
     *     possible object is
     *     {@link ResponseResultType }
     *     
     */
    public ResponseResultType getResult() {
        return result;
    }

    /**
     * Sets the value of the result property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseResultType }
     *     
     */
    public void setResult(ResponseResultType value) {
        this.result = value;
    }

    /**
     * Gets the value of the serverTransactionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServerTransactionId() {
        return serverTransactionId;
    }

    /**
     * Sets the value of the serverTransactionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServerTransactionId(String value) {
        this.serverTransactionId = value;
    }

    /**
     * Gets the value of the runtimeFaultMessage property.
     * 
     * @return
     *     possible object is
     *     {@link RuntimeFaultMessage }
     *     
     */
    public RuntimeFaultMessage getRuntimeFaultMessage() {
        return runtimeFaultMessage;
    }

    /**
     * Sets the value of the runtimeFaultMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link RuntimeFaultMessage }
     *     
     */
    public void setRuntimeFaultMessage(RuntimeFaultMessage value) {
        this.runtimeFaultMessage = value;
    }

}
