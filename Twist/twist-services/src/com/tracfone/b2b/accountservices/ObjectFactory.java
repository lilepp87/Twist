
package com.tracfone.b2b.accountservices;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.tracfone.b2b.accountservices package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ValidateAccountResponse_QNAME = new QName("http://b2b.tracfone.com/AccountServices", "validateAccountResponse");
    private final static QName _IsValidAccountResponse_QNAME = new QName("http://b2b.tracfone.com/AccountServices", "isValidAccountResponse");
    private final static QName _ValidateAccountRequestMB_QNAME = new QName("http://b2b.tracfone.com/AccountServices", "validateAccountRequestMB");
    private final static QName _ValidateAccountRequest_QNAME = new QName("http://b2b.tracfone.com/AccountServices", "validateAccountRequest");
    private final static QName _IsValidAccountRequest_QNAME = new QName("http://b2b.tracfone.com/AccountServices", "isValidAccountRequest");
    private final static QName _GetAccountSecurityQuestionsRequest_QNAME = new QName("http://b2b.tracfone.com/AccountServices", "getAccountSecurityQuestionsRequest");
    private final static QName _ResetPasswordRequest_QNAME = new QName("http://b2b.tracfone.com/AccountServices", "resetPasswordRequest");
    private final static QName _GetEsnListByCriteriaRequest_QNAME = new QName("http://b2b.tracfone.com/AccountServices", "getEsnListByCriteriaRequest");
    private final static QName _UpdateAccountRequest_QNAME = new QName("http://b2b.tracfone.com/AccountServices", "updateAccountRequest");
    private final static QName _CreateAccountResponse_QNAME = new QName("http://b2b.tracfone.com/AccountServices", "createAccountResponse");
    private final static QName _UpdateAccountResponse_QNAME = new QName("http://b2b.tracfone.com/AccountServices", "updateAccountResponse");
    private final static QName _UpdateE911AddressInformationResponse_QNAME = new QName("http://b2b.tracfone.com/AccountServices", "updateE911AddressInformationResponse");
    private final static QName _SubmitE911AddressCaseRequest_QNAME = new QName("http://b2b.tracfone.com/AccountServices", "submitE911AddressCaseRequest");
    private final static QName _AddCreditCardRequest_QNAME = new QName("http://b2b.tracfone.com/AccountServices", "addCreditCardRequest");
    private final static QName _ForgotPasswordResponse_QNAME = new QName("http://b2b.tracfone.com/AccountServices", "forgotPasswordResponse");
    private final static QName _GetAccountSummaryResponse_QNAME = new QName("http://b2b.tracfone.com/AccountServices", "getAccountSummaryResponse");
    private final static QName _ValidateAccountResponseMB_QNAME = new QName("http://b2b.tracfone.com/AccountServices", "validateAccountResponseMB");
    private final static QName _GetCreditCardListRequest_QNAME = new QName("http://b2b.tracfone.com/AccountServices", "getCreditCardListRequest");
    private final static QName _GetAccountSecurityQuestionResponse_QNAME = new QName("http://b2b.tracfone.com/AccountServices", "getAccountSecurityQuestionResponse");
    private final static QName _ResetPasswordResponse_QNAME = new QName("http://b2b.tracfone.com/AccountServices", "resetPasswordResponse");
    private final static QName _CreateAccountRequest_QNAME = new QName("http://b2b.tracfone.com/AccountServices", "createAccountRequest");
    private final static QName _GetTimeAsCustomerResponse_QNAME = new QName("http://b2b.tracfone.com/AccountServices", "getTimeAsCustomerResponse");
    private final static QName _GetCreditCardListResponse_QNAME = new QName("http://b2b.tracfone.com/AccountServices", "getCreditCardListResponse");
    private final static QName _AddEsnToAccountRequest_QNAME = new QName("http://b2b.tracfone.com/AccountServices", "addEsnToAccountRequest");
    private final static QName _GetAccountSummaryRequest_QNAME = new QName("http://b2b.tracfone.com/AccountServices", "getAccountSummaryRequest");
    private final static QName _InsertE911AddressInformationRequest_QNAME = new QName("http://b2b.tracfone.com/AccountServices", "insertE911AddressInformationRequest");
    private final static QName _InsertE911AddressInformationResponse_QNAME = new QName("http://b2b.tracfone.com/AccountServices", "insertE911AddressInformationResponse");
    private final static QName _RemoveEsnFromAccountResponse_QNAME = new QName("http://b2b.tracfone.com/AccountServices", "removeEsnFromAccountResponse");
    private final static QName _GetTimeAsCustomerRequest_QNAME = new QName("http://b2b.tracfone.com/AccountServices", "getTimeAsCustomerRequest");
    private final static QName _GetAccountDetailsRequest_QNAME = new QName("http://b2b.tracfone.com/AccountServices", "getAccountDetailsRequest");
    private final static QName _UpdateE911AddressInformationRequest_QNAME = new QName("http://b2b.tracfone.com/AccountServices", "updateE911AddressInformationRequest");
    private final static QName _ForgotPasswordRequest_QNAME = new QName("http://b2b.tracfone.com/AccountServices", "forgotPasswordRequest");
    private final static QName _SubmitE911AddressCaseResponse_QNAME = new QName("http://b2b.tracfone.com/AccountServices", "submitE911AddressCaseResponse");
    private final static QName _AddCreditCardResponse_QNAME = new QName("http://b2b.tracfone.com/AccountServices", "addCreditCardResponse");
    private final static QName _GetEsnListByCriteriaResponse_QNAME = new QName("http://b2b.tracfone.com/AccountServices", "getEsnListByCriteriaResponse");
    private final static QName _AddEsnToAccountResponse_QNAME = new QName("http://b2b.tracfone.com/AccountServices", "addEsnToAccountResponse");
    private final static QName _GetAccountDetailsResponse_QNAME = new QName("http://b2b.tracfone.com/AccountServices", "getAccountDetailsResponse");
    private final static QName _RemoveEsnFromAccountRequest_QNAME = new QName("http://b2b.tracfone.com/AccountServices", "removeEsnFromAccountRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.tracfone.b2b.accountservices
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SubmitE911AddressCaseResponseType }
     * 
     */
    public SubmitE911AddressCaseResponseType createSubmitE911AddressCaseResponseType() {
        return new SubmitE911AddressCaseResponseType();
    }

    /**
     * Create an instance of {@link AddressInformationRequestType }
     * 
     */
    public AddressInformationRequestType createAddressInformationRequestType() {
        return new AddressInformationRequestType();
    }

    /**
     * Create an instance of {@link ForgotPasswordRequestType }
     * 
     */
    public ForgotPasswordRequestType createForgotPasswordRequestType() {
        return new ForgotPasswordRequestType();
    }

    /**
     * Create an instance of {@link TimeAsCustomerRequestType }
     * 
     */
    public TimeAsCustomerRequestType createTimeAsCustomerRequestType() {
        return new TimeAsCustomerRequestType();
    }

    /**
     * Create an instance of {@link GetAccountDetailsRequestType }
     * 
     */
    public GetAccountDetailsRequestType createGetAccountDetailsRequestType() {
        return new GetAccountDetailsRequestType();
    }

    /**
     * Create an instance of {@link RemoveEsnFromAccountRequestType }
     * 
     */
    public RemoveEsnFromAccountRequestType createRemoveEsnFromAccountRequestType() {
        return new RemoveEsnFromAccountRequestType();
    }

    /**
     * Create an instance of {@link GetEsnListByCriteriaResponseType }
     * 
     */
    public GetEsnListByCriteriaResponseType createGetEsnListByCriteriaResponseType() {
        return new GetEsnListByCriteriaResponseType();
    }

    /**
     * Create an instance of {@link AddEsnToAccountResponseType }
     * 
     */
    public AddEsnToAccountResponseType createAddEsnToAccountResponseType() {
        return new AddEsnToAccountResponseType();
    }

    /**
     * Create an instance of {@link GetAccountDetailsResponseType }
     * 
     */
    public GetAccountDetailsResponseType createGetAccountDetailsResponseType() {
        return new GetAccountDetailsResponseType();
    }

    /**
     * Create an instance of {@link CreditCardAdditionResponseType }
     * 
     */
    public CreditCardAdditionResponseType createCreditCardAdditionResponseType() {
        return new CreditCardAdditionResponseType();
    }

    /**
     * Create an instance of {@link GetAccountSummaryRequestType }
     * 
     */
    public GetAccountSummaryRequestType createGetAccountSummaryRequestType() {
        return new GetAccountSummaryRequestType();
    }

    /**
     * Create an instance of {@link CreditCardListResponseType }
     * 
     */
    public CreditCardListResponseType createCreditCardListResponseType() {
        return new CreditCardListResponseType();
    }

    /**
     * Create an instance of {@link AddEsnToAccountRequestType }
     * 
     */
    public AddEsnToAccountRequestType createAddEsnToAccountRequestType() {
        return new AddEsnToAccountRequestType();
    }

    /**
     * Create an instance of {@link TimeAsCustomerResponseType }
     * 
     */
    public TimeAsCustomerResponseType createTimeAsCustomerResponseType() {
        return new TimeAsCustomerResponseType();
    }

    /**
     * Create an instance of {@link RemoveEsnFromAccountResponseType }
     * 
     */
    public RemoveEsnFromAccountResponseType createRemoveEsnFromAccountResponseType() {
        return new RemoveEsnFromAccountResponseType();
    }

    /**
     * Create an instance of {@link AddressInformationResponseType }
     * 
     */
    public AddressInformationResponseType createAddressInformationResponseType() {
        return new AddressInformationResponseType();
    }

    /**
     * Create an instance of {@link AccountSecurityQuestionsLookupResponseType }
     * 
     */
    public AccountSecurityQuestionsLookupResponseType createAccountSecurityQuestionsLookupResponseType() {
        return new AccountSecurityQuestionsLookupResponseType();
    }

    /**
     * Create an instance of {@link CreditCardListRequestType }
     * 
     */
    public CreditCardListRequestType createCreditCardListRequestType() {
        return new CreditCardListRequestType();
    }

    /**
     * Create an instance of {@link AccountCreationRequestType }
     * 
     */
    public AccountCreationRequestType createAccountCreationRequestType() {
        return new AccountCreationRequestType();
    }

    /**
     * Create an instance of {@link ResetPasswordResponseType }
     * 
     */
    public ResetPasswordResponseType createResetPasswordResponseType() {
        return new ResetPasswordResponseType();
    }

    /**
     * Create an instance of {@link ForgotPasswordResponseType }
     * 
     */
    public ForgotPasswordResponseType createForgotPasswordResponseType() {
        return new ForgotPasswordResponseType();
    }

    /**
     * Create an instance of {@link CreditCardAdditionRequestType }
     * 
     */
    public CreditCardAdditionRequestType createCreditCardAdditionRequestType() {
        return new CreditCardAdditionRequestType();
    }

    /**
     * Create an instance of {@link SubmitE911AddressCaseRequestType }
     * 
     */
    public SubmitE911AddressCaseRequestType createSubmitE911AddressCaseRequestType() {
        return new SubmitE911AddressCaseRequestType();
    }

    /**
     * Create an instance of {@link AccountValidationResponseType }
     * 
     */
    public AccountValidationResponseType createAccountValidationResponseType() {
        return new AccountValidationResponseType();
    }

    /**
     * Create an instance of {@link GetAccountSummaryResponseType }
     * 
     */
    public GetAccountSummaryResponseType createGetAccountSummaryResponseType() {
        return new GetAccountSummaryResponseType();
    }

    /**
     * Create an instance of {@link AccountCreationResponseType }
     * 
     */
    public AccountCreationResponseType createAccountCreationResponseType() {
        return new AccountCreationResponseType();
    }

    /**
     * Create an instance of {@link AccountUpdateResponseType }
     * 
     */
    public AccountUpdateResponseType createAccountUpdateResponseType() {
        return new AccountUpdateResponseType();
    }

    /**
     * Create an instance of {@link GetEsnListByCriteriaRequestType }
     * 
     */
    public GetEsnListByCriteriaRequestType createGetEsnListByCriteriaRequestType() {
        return new GetEsnListByCriteriaRequestType();
    }

    /**
     * Create an instance of {@link AccountUpdateRequestType }
     * 
     */
    public AccountUpdateRequestType createAccountUpdateRequestType() {
        return new AccountUpdateRequestType();
    }

    /**
     * Create an instance of {@link AccountRecoveryAccountStatusRequestType }
     * 
     */
    public AccountRecoveryAccountStatusRequestType createAccountRecoveryAccountStatusRequestType() {
        return new AccountRecoveryAccountStatusRequestType();
    }

    /**
     * Create an instance of {@link ResetPasswordRequestType }
     * 
     */
    public ResetPasswordRequestType createResetPasswordRequestType() {
        return new ResetPasswordRequestType();
    }

    /**
     * Create an instance of {@link AccountSecurityQuestionsLookupRequestType }
     * 
     */
    public AccountSecurityQuestionsLookupRequestType createAccountSecurityQuestionsLookupRequestType() {
        return new AccountSecurityQuestionsLookupRequestType();
    }

    /**
     * Create an instance of {@link AccountRecoveryAccountStatusResponseType }
     * 
     */
    public AccountRecoveryAccountStatusResponseType createAccountRecoveryAccountStatusResponseType() {
        return new AccountRecoveryAccountStatusResponseType();
    }

    /**
     * Create an instance of {@link AccountValidationRequestType }
     * 
     */
    public AccountValidationRequestType createAccountValidationRequestType() {
        return new AccountValidationRequestType();
    }

    /**
     * Create an instance of {@link AccountValidationRequestTypeMB }
     * 
     */
    public AccountValidationRequestTypeMB createAccountValidationRequestTypeMB() {
        return new AccountValidationRequestTypeMB();
    }

    /**
     * Create an instance of {@link EsnQueryCriteria }
     * 
     */
    public EsnQueryCriteria createEsnQueryCriteria() {
        return new EsnQueryCriteria();
    }

    /**
     * Create an instance of {@link CreateCallTransRequestType }
     * 
     */
    public CreateCallTransRequestType createCreateCallTransRequestType() {
        return new CreateCallTransRequestType();
    }

    /**
     * Create an instance of {@link GroupInfoType }
     * 
     */
    public GroupInfoType createGroupInfoType() {
        return new GroupInfoType();
    }

    /**
     * Create an instance of {@link AccountDeviceDataType }
     * 
     */
    public AccountDeviceDataType createAccountDeviceDataType() {
        return new AccountDeviceDataType();
    }

    /**
     * Create an instance of {@link DeviceInfoType }
     * 
     */
    public DeviceInfoType createDeviceInfoType() {
        return new DeviceInfoType();
    }

    /**
     * Create an instance of {@link CreateActionItemIGTransactionRequestType }
     * 
     */
    public CreateActionItemIGTransactionRequestType createCreateActionItemIGTransactionRequestType() {
        return new CreateActionItemIGTransactionRequestType();
    }

    /**
     * Create an instance of {@link ESNSummary }
     * 
     */
    public ESNSummary createESNSummary() {
        return new ESNSummary();
    }

    /**
     * Create an instance of {@link CreateTicketRequestType }
     * 
     */
    public CreateTicketRequestType createCreateTicketRequestType() {
        return new CreateTicketRequestType();
    }

    /**
     * Create an instance of {@link TransactionDeatilsType }
     * 
     */
    public TransactionDeatilsType createTransactionDeatilsType() {
        return new TransactionDeatilsType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccountValidationResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/AccountServices", name = "validateAccountResponse")
    public JAXBElement<AccountValidationResponseType> createValidateAccountResponse(AccountValidationResponseType value) {
        return new JAXBElement<AccountValidationResponseType>(_ValidateAccountResponse_QNAME, AccountValidationResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccountRecoveryAccountStatusResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/AccountServices", name = "isValidAccountResponse")
    public JAXBElement<AccountRecoveryAccountStatusResponseType> createIsValidAccountResponse(AccountRecoveryAccountStatusResponseType value) {
        return new JAXBElement<AccountRecoveryAccountStatusResponseType>(_IsValidAccountResponse_QNAME, AccountRecoveryAccountStatusResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccountValidationRequestTypeMB }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/AccountServices", name = "validateAccountRequestMB")
    public JAXBElement<AccountValidationRequestTypeMB> createValidateAccountRequestMB(AccountValidationRequestTypeMB value) {
        return new JAXBElement<AccountValidationRequestTypeMB>(_ValidateAccountRequestMB_QNAME, AccountValidationRequestTypeMB.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccountValidationRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/AccountServices", name = "validateAccountRequest")
    public JAXBElement<AccountValidationRequestType> createValidateAccountRequest(AccountValidationRequestType value) {
        return new JAXBElement<AccountValidationRequestType>(_ValidateAccountRequest_QNAME, AccountValidationRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccountRecoveryAccountStatusRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/AccountServices", name = "isValidAccountRequest")
    public JAXBElement<AccountRecoveryAccountStatusRequestType> createIsValidAccountRequest(AccountRecoveryAccountStatusRequestType value) {
        return new JAXBElement<AccountRecoveryAccountStatusRequestType>(_IsValidAccountRequest_QNAME, AccountRecoveryAccountStatusRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccountSecurityQuestionsLookupRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/AccountServices", name = "getAccountSecurityQuestionsRequest")
    public JAXBElement<AccountSecurityQuestionsLookupRequestType> createGetAccountSecurityQuestionsRequest(AccountSecurityQuestionsLookupRequestType value) {
        return new JAXBElement<AccountSecurityQuestionsLookupRequestType>(_GetAccountSecurityQuestionsRequest_QNAME, AccountSecurityQuestionsLookupRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResetPasswordRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/AccountServices", name = "resetPasswordRequest")
    public JAXBElement<ResetPasswordRequestType> createResetPasswordRequest(ResetPasswordRequestType value) {
        return new JAXBElement<ResetPasswordRequestType>(_ResetPasswordRequest_QNAME, ResetPasswordRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEsnListByCriteriaRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/AccountServices", name = "getEsnListByCriteriaRequest")
    public JAXBElement<GetEsnListByCriteriaRequestType> createGetEsnListByCriteriaRequest(GetEsnListByCriteriaRequestType value) {
        return new JAXBElement<GetEsnListByCriteriaRequestType>(_GetEsnListByCriteriaRequest_QNAME, GetEsnListByCriteriaRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccountUpdateRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/AccountServices", name = "updateAccountRequest")
    public JAXBElement<AccountUpdateRequestType> createUpdateAccountRequest(AccountUpdateRequestType value) {
        return new JAXBElement<AccountUpdateRequestType>(_UpdateAccountRequest_QNAME, AccountUpdateRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccountCreationResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/AccountServices", name = "createAccountResponse")
    public JAXBElement<AccountCreationResponseType> createCreateAccountResponse(AccountCreationResponseType value) {
        return new JAXBElement<AccountCreationResponseType>(_CreateAccountResponse_QNAME, AccountCreationResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccountUpdateResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/AccountServices", name = "updateAccountResponse")
    public JAXBElement<AccountUpdateResponseType> createUpdateAccountResponse(AccountUpdateResponseType value) {
        return new JAXBElement<AccountUpdateResponseType>(_UpdateAccountResponse_QNAME, AccountUpdateResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddressInformationResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/AccountServices", name = "updateE911AddressInformationResponse")
    public JAXBElement<AddressInformationResponseType> createUpdateE911AddressInformationResponse(AddressInformationResponseType value) {
        return new JAXBElement<AddressInformationResponseType>(_UpdateE911AddressInformationResponse_QNAME, AddressInformationResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubmitE911AddressCaseRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/AccountServices", name = "submitE911AddressCaseRequest")
    public JAXBElement<SubmitE911AddressCaseRequestType> createSubmitE911AddressCaseRequest(SubmitE911AddressCaseRequestType value) {
        return new JAXBElement<SubmitE911AddressCaseRequestType>(_SubmitE911AddressCaseRequest_QNAME, SubmitE911AddressCaseRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreditCardAdditionRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/AccountServices", name = "addCreditCardRequest")
    public JAXBElement<CreditCardAdditionRequestType> createAddCreditCardRequest(CreditCardAdditionRequestType value) {
        return new JAXBElement<CreditCardAdditionRequestType>(_AddCreditCardRequest_QNAME, CreditCardAdditionRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ForgotPasswordResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/AccountServices", name = "forgotPasswordResponse")
    public JAXBElement<ForgotPasswordResponseType> createForgotPasswordResponse(ForgotPasswordResponseType value) {
        return new JAXBElement<ForgotPasswordResponseType>(_ForgotPasswordResponse_QNAME, ForgotPasswordResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAccountSummaryResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/AccountServices", name = "getAccountSummaryResponse")
    public JAXBElement<GetAccountSummaryResponseType> createGetAccountSummaryResponse(GetAccountSummaryResponseType value) {
        return new JAXBElement<GetAccountSummaryResponseType>(_GetAccountSummaryResponse_QNAME, GetAccountSummaryResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccountValidationResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/AccountServices", name = "validateAccountResponseMB")
    public JAXBElement<AccountValidationResponseType> createValidateAccountResponseMB(AccountValidationResponseType value) {
        return new JAXBElement<AccountValidationResponseType>(_ValidateAccountResponseMB_QNAME, AccountValidationResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreditCardListRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/AccountServices", name = "getCreditCardListRequest")
    public JAXBElement<CreditCardListRequestType> createGetCreditCardListRequest(CreditCardListRequestType value) {
        return new JAXBElement<CreditCardListRequestType>(_GetCreditCardListRequest_QNAME, CreditCardListRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccountSecurityQuestionsLookupResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/AccountServices", name = "getAccountSecurityQuestionResponse")
    public JAXBElement<AccountSecurityQuestionsLookupResponseType> createGetAccountSecurityQuestionResponse(AccountSecurityQuestionsLookupResponseType value) {
        return new JAXBElement<AccountSecurityQuestionsLookupResponseType>(_GetAccountSecurityQuestionResponse_QNAME, AccountSecurityQuestionsLookupResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResetPasswordResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/AccountServices", name = "resetPasswordResponse")
    public JAXBElement<ResetPasswordResponseType> createResetPasswordResponse(ResetPasswordResponseType value) {
        return new JAXBElement<ResetPasswordResponseType>(_ResetPasswordResponse_QNAME, ResetPasswordResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccountCreationRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/AccountServices", name = "createAccountRequest")
    public JAXBElement<AccountCreationRequestType> createCreateAccountRequest(AccountCreationRequestType value) {
        return new JAXBElement<AccountCreationRequestType>(_CreateAccountRequest_QNAME, AccountCreationRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TimeAsCustomerResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/AccountServices", name = "getTimeAsCustomerResponse")
    public JAXBElement<TimeAsCustomerResponseType> createGetTimeAsCustomerResponse(TimeAsCustomerResponseType value) {
        return new JAXBElement<TimeAsCustomerResponseType>(_GetTimeAsCustomerResponse_QNAME, TimeAsCustomerResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreditCardListResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/AccountServices", name = "getCreditCardListResponse")
    public JAXBElement<CreditCardListResponseType> createGetCreditCardListResponse(CreditCardListResponseType value) {
        return new JAXBElement<CreditCardListResponseType>(_GetCreditCardListResponse_QNAME, CreditCardListResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddEsnToAccountRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/AccountServices", name = "addEsnToAccountRequest")
    public JAXBElement<AddEsnToAccountRequestType> createAddEsnToAccountRequest(AddEsnToAccountRequestType value) {
        return new JAXBElement<AddEsnToAccountRequestType>(_AddEsnToAccountRequest_QNAME, AddEsnToAccountRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAccountSummaryRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/AccountServices", name = "getAccountSummaryRequest")
    public JAXBElement<GetAccountSummaryRequestType> createGetAccountSummaryRequest(GetAccountSummaryRequestType value) {
        return new JAXBElement<GetAccountSummaryRequestType>(_GetAccountSummaryRequest_QNAME, GetAccountSummaryRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddressInformationRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/AccountServices", name = "insertE911AddressInformationRequest")
    public JAXBElement<AddressInformationRequestType> createInsertE911AddressInformationRequest(AddressInformationRequestType value) {
        return new JAXBElement<AddressInformationRequestType>(_InsertE911AddressInformationRequest_QNAME, AddressInformationRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddressInformationResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/AccountServices", name = "insertE911AddressInformationResponse")
    public JAXBElement<AddressInformationResponseType> createInsertE911AddressInformationResponse(AddressInformationResponseType value) {
        return new JAXBElement<AddressInformationResponseType>(_InsertE911AddressInformationResponse_QNAME, AddressInformationResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveEsnFromAccountResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/AccountServices", name = "removeEsnFromAccountResponse")
    public JAXBElement<RemoveEsnFromAccountResponseType> createRemoveEsnFromAccountResponse(RemoveEsnFromAccountResponseType value) {
        return new JAXBElement<RemoveEsnFromAccountResponseType>(_RemoveEsnFromAccountResponse_QNAME, RemoveEsnFromAccountResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TimeAsCustomerRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/AccountServices", name = "getTimeAsCustomerRequest")
    public JAXBElement<TimeAsCustomerRequestType> createGetTimeAsCustomerRequest(TimeAsCustomerRequestType value) {
        return new JAXBElement<TimeAsCustomerRequestType>(_GetTimeAsCustomerRequest_QNAME, TimeAsCustomerRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAccountDetailsRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/AccountServices", name = "getAccountDetailsRequest")
    public JAXBElement<GetAccountDetailsRequestType> createGetAccountDetailsRequest(GetAccountDetailsRequestType value) {
        return new JAXBElement<GetAccountDetailsRequestType>(_GetAccountDetailsRequest_QNAME, GetAccountDetailsRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddressInformationRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/AccountServices", name = "updateE911AddressInformationRequest")
    public JAXBElement<AddressInformationRequestType> createUpdateE911AddressInformationRequest(AddressInformationRequestType value) {
        return new JAXBElement<AddressInformationRequestType>(_UpdateE911AddressInformationRequest_QNAME, AddressInformationRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ForgotPasswordRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/AccountServices", name = "forgotPasswordRequest")
    public JAXBElement<ForgotPasswordRequestType> createForgotPasswordRequest(ForgotPasswordRequestType value) {
        return new JAXBElement<ForgotPasswordRequestType>(_ForgotPasswordRequest_QNAME, ForgotPasswordRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SubmitE911AddressCaseResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/AccountServices", name = "submitE911AddressCaseResponse")
    public JAXBElement<SubmitE911AddressCaseResponseType> createSubmitE911AddressCaseResponse(SubmitE911AddressCaseResponseType value) {
        return new JAXBElement<SubmitE911AddressCaseResponseType>(_SubmitE911AddressCaseResponse_QNAME, SubmitE911AddressCaseResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreditCardAdditionResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/AccountServices", name = "addCreditCardResponse")
    public JAXBElement<CreditCardAdditionResponseType> createAddCreditCardResponse(CreditCardAdditionResponseType value) {
        return new JAXBElement<CreditCardAdditionResponseType>(_AddCreditCardResponse_QNAME, CreditCardAdditionResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEsnListByCriteriaResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/AccountServices", name = "getEsnListByCriteriaResponse")
    public JAXBElement<GetEsnListByCriteriaResponseType> createGetEsnListByCriteriaResponse(GetEsnListByCriteriaResponseType value) {
        return new JAXBElement<GetEsnListByCriteriaResponseType>(_GetEsnListByCriteriaResponse_QNAME, GetEsnListByCriteriaResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddEsnToAccountResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/AccountServices", name = "addEsnToAccountResponse")
    public JAXBElement<AddEsnToAccountResponseType> createAddEsnToAccountResponse(AddEsnToAccountResponseType value) {
        return new JAXBElement<AddEsnToAccountResponseType>(_AddEsnToAccountResponse_QNAME, AddEsnToAccountResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAccountDetailsResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/AccountServices", name = "getAccountDetailsResponse")
    public JAXBElement<GetAccountDetailsResponseType> createGetAccountDetailsResponse(GetAccountDetailsResponseType value) {
        return new JAXBElement<GetAccountDetailsResponseType>(_GetAccountDetailsResponse_QNAME, GetAccountDetailsResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveEsnFromAccountRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/AccountServices", name = "removeEsnFromAccountRequest")
    public JAXBElement<RemoveEsnFromAccountRequestType> createRemoveEsnFromAccountRequest(RemoveEsnFromAccountRequestType value) {
        return new JAXBElement<RemoveEsnFromAccountRequestType>(_RemoveEsnFromAccountRequest_QNAME, RemoveEsnFromAccountRequestType.class, null, value);
    }

}
