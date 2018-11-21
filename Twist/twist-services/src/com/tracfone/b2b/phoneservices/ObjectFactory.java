
package com.tracfone.b2b.phoneservices;

import java.math.BigDecimal;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.tracfone.b2b.phoneservices package. 
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

    private final static QName _GetDeviceServiceInformationResponse_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "getDeviceServiceInformationResponse");
    private final static QName _IsTracfoneProductResponse_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "isTracfoneProductResponse");
    private final static QName _GetPINCardInformationForEsnRequest_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "getPINCardInformationForEsnRequest");
    private final static QName _GetEsnAttributesResponse_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "getEsnAttributesResponse");
    private final static QName _GetEsnFromSimRequest_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "getEsnFromSimRequest");
    private final static QName _SetEsnAttributesRequest_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "setEsnAttributesRequest");
    private final static QName _GetEsnFromMinRequest_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "getEsnFromMinRequest");
    private final static QName _GetPurchaseDetailsResponse_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "getPurchaseDetailsResponse");
    private final static QName _GetAllManufacturersResponse_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "getAllManufacturersResponse");
    private final static QName _VoidPinResponse_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "voidPinResponse");
    private final static QName _ValidatePinForESNResponse_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "validatePinForESNResponse");
    private final static QName _PlanInfoResponse_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "planInfoResponse");
    private final static QName _CheckCarrierPendingResponse_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "checkCarrierPendingResponse");
    private final static QName _GetDeviceInformationRequest_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "getDeviceInformationRequest");
    private final static QName _ValidatePinForESNRequest_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "validatePinForESNRequest");
    private final static QName _GetEsnInformationRequest_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "getEsnInformationRequest");
    private final static QName _GetDeviceInformationResponse_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "getDeviceInformationResponse");
    private final static QName _GetRedemptionCardInformationResponse_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "getRedemptionCardInformationResponse");
    private final static QName _GetUpdatedEsnAttributesResponse_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "getUpdatedEsnAttributesResponse");
    private final static QName _GetBrandFromEsnResponse_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "getBrandFromEsnResponse");
    private final static QName _VoidPinRequest_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "voidPinRequest");
    private final static QName _GetHandSetInformationResponse_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "getHandSetInformationResponse");
    private final static QName _BurnRedemptionCardResponse_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "burnRedemptionCardResponse");
    private final static QName _GetDataUsageRequest_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "getDataUsageRequest");
    private final static QName _GetDataUsageResponse_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "getDataUsageResponse");
    private final static QName _GetUpdatedEsnAttributesRequest_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "getUpdatedEsnAttributesRequest");
    private final static QName _SetEsnAttributesResponse_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "setEsnAttributesResponse");
    private final static QName _GetCardRedeemedDetailsFromSMPsResponse_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "getCardRedeemedDetailsFromSMPsResponse");
    private final static QName _GetAllModelsRequest_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "getAllModelsRequest");
    private final static QName _GetEsnFromSimResponse_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "getEsnFromSimResponse");
    private final static QName _GetPurchaseDetailsRequest_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "getPurchaseDetailsRequest");
    private final static QName _GetAllModelsResponse_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "getAllModelsResponse");
    private final static QName _GetEsnAttributesRequest_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "getEsnAttributesRequest");
    private final static QName _GetRedemptionCardInformationRequest_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "getRedemptionCardInformationRequest");
    private final static QName _IsPhoneValidForStoreFrontResponseType_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "isPhoneValidForStoreFrontResponseType");
    private final static QName _GetEsnInformationResponse_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "getEsnInformationResponse");
    private final static QName _UpdatePhoneInformationRequest_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "updatePhoneInformationRequest");
    private final static QName _GetEsnFromMinResponse_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "getEsnFromMinResponse");
    private final static QName _GetPINCardInformationForEsnResponse_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "getPINCardInformationForEsnResponse");
    private final static QName _GetBrandFromEsnRequest_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "getBrandFromEsnRequest");
    private final static QName _GetHandSetInformationRequest_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "getHandSetInformationRequest");
    private final static QName _UpdatePhoneInformationResponse_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "updatePhoneInformationResponse");
    private final static QName _GetDeviceServiceInformationRequest_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "getDeviceServiceInformationRequest");
    private final static QName _GetCardRedeemedDetailsFromSMPsRequest_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "getCardRedeemedDetailsFromSMPsRequest");
    private final static QName _GetWifiEligibilityStatusRequest_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "getWifiEligibilityStatusRequest");
    private final static QName _GetPinCardInformationResponse_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "getPinCardInformationResponse");
    private final static QName _IsTracfoneProductRequest_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "isTracfoneProductRequest");
    private final static QName _GetPinCardInformationRequest_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "getPinCardInformationRequest");
    private final static QName _IsPhoneValidForStoreFrontRequestType_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "isPhoneValidForStoreFrontRequestType");
    private final static QName _GetWifiEligibilityStatusResponse_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "getWifiEligibilityStatusResponse");
    private final static QName _PlanInfoRequest_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "planInfoRequest");
    private final static QName _CheckCarrierPendingRequest_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "checkCarrierPendingRequest");
    private final static QName _GetAllManufacturersRequest_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "getAllManufacturersRequest");
    private final static QName _BurnRedemptionCardRequest_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "burnRedemptionCardRequest");
    private final static QName _VoidPinResponseTypeErrNum_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "err_num");
    private final static QName _VoidPinResponseTypeERRMsg_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "ERR_msg");
    private final static QName _VoidPinResponseTypeOutRefundItem_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "out_refund_item");
    private final static QName _IsTracfoneProductResponseTypeRespItems_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "resp_items");
    private final static QName _IsTracfoneProductResponseTypeErrMsg_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "err_msg");
    private final static QName _VoidPinRequestTypeInRefundItem_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "in_refund_item");
    private final static QName _VoidPinRequestTypeOrderId_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "order_id");
    private final static QName _VoidPinRequestTypeOrderType_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "order_type");
    private final static QName _VoidPinRequestTypeRmaId_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "rma_id");
    private final static QName _GetCardRedeemedDetailsFromSMPsRequestTypeInPinStatus_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "in_pin_status");
    private final static QName _IsTracfoneProductRequestTypeReqItems_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "req_items");
    private final static QName _IsTracfoneProductRequestTypeStage_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "stage");
    private final static QName _GetCardRedeemedDetailsFromSMPsResponseTypeOutPinStatus_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "out_pin_status");
    private final static QName _ItemRecordIsTracfone_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "is_tracfone");
    private final static QName _ItemRecordPartNumber_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "part_number");
    private final static QName _ItemRecordSmp_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "smp");
    private final static QName _ItemRecordEsn_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "esn");
    private final static QName _PinStatusRecordStatus_QNAME = new QName("http://b2b.tracfone.com/PhoneServices", "status");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.tracfone.b2b.phoneservices
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link EsnFromMinResponseType }
     * 
     */
    public EsnFromMinResponseType createEsnFromMinResponseType() {
        return new EsnFromMinResponseType();
    }

    /**
     * Create an instance of {@link PhoneInformationRequestType }
     * 
     */
    public PhoneInformationRequestType createPhoneInformationRequestType() {
        return new PhoneInformationRequestType();
    }

    /**
     * Create an instance of {@link EsnInformationResponseType }
     * 
     */
    public EsnInformationResponseType createEsnInformationResponseType() {
        return new EsnInformationResponseType();
    }

    /**
     * Create an instance of {@link IsPhoneValidForStoreFrontResponseType }
     * 
     */
    public IsPhoneValidForStoreFrontResponseType createIsPhoneValidForStoreFrontResponseType() {
        return new IsPhoneValidForStoreFrontResponseType();
    }

    /**
     * Create an instance of {@link RedemptionCardInformationRequestType }
     * 
     */
    public RedemptionCardInformationRequestType createRedemptionCardInformationRequestType() {
        return new RedemptionCardInformationRequestType();
    }

    /**
     * Create an instance of {@link GetEsnAttributesRequestType }
     * 
     */
    public GetEsnAttributesRequestType createGetEsnAttributesRequestType() {
        return new GetEsnAttributesRequestType();
    }

    /**
     * Create an instance of {@link GetAllModelsResponseType }
     * 
     */
    public GetAllModelsResponseType createGetAllModelsResponseType() {
        return new GetAllModelsResponseType();
    }

    /**
     * Create an instance of {@link PurchaseDetailsRequestType }
     * 
     */
    public PurchaseDetailsRequestType createPurchaseDetailsRequestType() {
        return new PurchaseDetailsRequestType();
    }

    /**
     * Create an instance of {@link GetEsnFromSimResponseType }
     * 
     */
    public GetEsnFromSimResponseType createGetEsnFromSimResponseType() {
        return new GetEsnFromSimResponseType();
    }

    /**
     * Create an instance of {@link GetAllModelsRequestType }
     * 
     */
    public GetAllModelsRequestType createGetAllModelsRequestType() {
        return new GetAllModelsRequestType();
    }

    /**
     * Create an instance of {@link GetCardRedeemedDetailsFromSMPsResponseType }
     * 
     */
    public GetCardRedeemedDetailsFromSMPsResponseType createGetCardRedeemedDetailsFromSMPsResponseType() {
        return new GetCardRedeemedDetailsFromSMPsResponseType();
    }

    /**
     * Create an instance of {@link SetEsnAttributesResponseType }
     * 
     */
    public SetEsnAttributesResponseType createSetEsnAttributesResponseType() {
        return new SetEsnAttributesResponseType();
    }

    /**
     * Create an instance of {@link GetDataUsageResponseType }
     * 
     */
    public GetDataUsageResponseType createGetDataUsageResponseType() {
        return new GetDataUsageResponseType();
    }

    /**
     * Create an instance of {@link BurnRedemptionCardRequestType }
     * 
     */
    public BurnRedemptionCardRequestType createBurnRedemptionCardRequestType() {
        return new BurnRedemptionCardRequestType();
    }

    /**
     * Create an instance of {@link GetAllManufacturersRequestType }
     * 
     */
    public GetAllManufacturersRequestType createGetAllManufacturersRequestType() {
        return new GetAllManufacturersRequestType();
    }

    /**
     * Create an instance of {@link CheckCarrierPendingRequestType }
     * 
     */
    public CheckCarrierPendingRequestType createCheckCarrierPendingRequestType() {
        return new CheckCarrierPendingRequestType();
    }

    /**
     * Create an instance of {@link PlanInfoRequestType }
     * 
     */
    public PlanInfoRequestType createPlanInfoRequestType() {
        return new PlanInfoRequestType();
    }

    /**
     * Create an instance of {@link WifiEligibilityStatusResponseType }
     * 
     */
    public WifiEligibilityStatusResponseType createWifiEligibilityStatusResponseType() {
        return new WifiEligibilityStatusResponseType();
    }

    /**
     * Create an instance of {@link IsPhoneValidForStoreFrontRequestType }
     * 
     */
    public IsPhoneValidForStoreFrontRequestType createIsPhoneValidForStoreFrontRequestType() {
        return new IsPhoneValidForStoreFrontRequestType();
    }

    /**
     * Create an instance of {@link GetPinCardInformationRequestType }
     * 
     */
    public GetPinCardInformationRequestType createGetPinCardInformationRequestType() {
        return new GetPinCardInformationRequestType();
    }

    /**
     * Create an instance of {@link IsTracfoneProductRequestType }
     * 
     */
    public IsTracfoneProductRequestType createIsTracfoneProductRequestType() {
        return new IsTracfoneProductRequestType();
    }

    /**
     * Create an instance of {@link GetPinCardInformationResponseType }
     * 
     */
    public GetPinCardInformationResponseType createGetPinCardInformationResponseType() {
        return new GetPinCardInformationResponseType();
    }

    /**
     * Create an instance of {@link WifiEligibilityStatusRequestType }
     * 
     */
    public WifiEligibilityStatusRequestType createWifiEligibilityStatusRequestType() {
        return new WifiEligibilityStatusRequestType();
    }

    /**
     * Create an instance of {@link GetCardRedeemedDetailsFromSMPsRequestType }
     * 
     */
    public GetCardRedeemedDetailsFromSMPsRequestType createGetCardRedeemedDetailsFromSMPsRequestType() {
        return new GetCardRedeemedDetailsFromSMPsRequestType();
    }

    /**
     * Create an instance of {@link PhoneInformationResponseType }
     * 
     */
    public PhoneInformationResponseType createPhoneInformationResponseType() {
        return new PhoneInformationResponseType();
    }

    /**
     * Create an instance of {@link DeviceServiceInformationRequestType }
     * 
     */
    public DeviceServiceInformationRequestType createDeviceServiceInformationRequestType() {
        return new DeviceServiceInformationRequestType();
    }

    /**
     * Create an instance of {@link GetHandSetInformationRequestType }
     * 
     */
    public GetHandSetInformationRequestType createGetHandSetInformationRequestType() {
        return new GetHandSetInformationRequestType();
    }

    /**
     * Create an instance of {@link GetBrandFromEsnRequestType }
     * 
     */
    public GetBrandFromEsnRequestType createGetBrandFromEsnRequestType() {
        return new GetBrandFromEsnRequestType();
    }

    /**
     * Create an instance of {@link PINCardInformationForESNResponseType }
     * 
     */
    public PINCardInformationForESNResponseType createPINCardInformationForESNResponseType() {
        return new PINCardInformationForESNResponseType();
    }

    /**
     * Create an instance of {@link ValidatePinForESNResponseType }
     * 
     */
    public ValidatePinForESNResponseType createValidatePinForESNResponseType() {
        return new ValidatePinForESNResponseType();
    }

    /**
     * Create an instance of {@link PurchaseDetailsResponseType }
     * 
     */
    public PurchaseDetailsResponseType createPurchaseDetailsResponseType() {
        return new PurchaseDetailsResponseType();
    }

    /**
     * Create an instance of {@link GetAllManufacturersResponseType }
     * 
     */
    public GetAllManufacturersResponseType createGetAllManufacturersResponseType() {
        return new GetAllManufacturersResponseType();
    }

    /**
     * Create an instance of {@link VoidPinResponseType }
     * 
     */
    public VoidPinResponseType createVoidPinResponseType() {
        return new VoidPinResponseType();
    }

    /**
     * Create an instance of {@link EsnFromMinRequestType }
     * 
     */
    public EsnFromMinRequestType createEsnFromMinRequestType() {
        return new EsnFromMinRequestType();
    }

    /**
     * Create an instance of {@link SetEsnAttributesRequestType }
     * 
     */
    public SetEsnAttributesRequestType createSetEsnAttributesRequestType() {
        return new SetEsnAttributesRequestType();
    }

    /**
     * Create an instance of {@link GetEsnFromSimRequestType }
     * 
     */
    public GetEsnFromSimRequestType createGetEsnFromSimRequestType() {
        return new GetEsnFromSimRequestType();
    }

    /**
     * Create an instance of {@link GetEsnAttributesResponseType }
     * 
     */
    public GetEsnAttributesResponseType createGetEsnAttributesResponseType() {
        return new GetEsnAttributesResponseType();
    }

    /**
     * Create an instance of {@link PINCardInformationForESNRequestType }
     * 
     */
    public PINCardInformationForESNRequestType createPINCardInformationForESNRequestType() {
        return new PINCardInformationForESNRequestType();
    }

    /**
     * Create an instance of {@link DeviceServiceInformationResponseType }
     * 
     */
    public DeviceServiceInformationResponseType createDeviceServiceInformationResponseType() {
        return new DeviceServiceInformationResponseType();
    }

    /**
     * Create an instance of {@link IsTracfoneProductResponseType }
     * 
     */
    public IsTracfoneProductResponseType createIsTracfoneProductResponseType() {
        return new IsTracfoneProductResponseType();
    }

    /**
     * Create an instance of {@link GetDataUsageRequestType }
     * 
     */
    public GetDataUsageRequestType createGetDataUsageRequestType() {
        return new GetDataUsageRequestType();
    }

    /**
     * Create an instance of {@link BurnRedemptionCardResponseType }
     * 
     */
    public BurnRedemptionCardResponseType createBurnRedemptionCardResponseType() {
        return new BurnRedemptionCardResponseType();
    }

    /**
     * Create an instance of {@link GetHandSetInformationResponseType }
     * 
     */
    public GetHandSetInformationResponseType createGetHandSetInformationResponseType() {
        return new GetHandSetInformationResponseType();
    }

    /**
     * Create an instance of {@link VoidPinRequestType }
     * 
     */
    public VoidPinRequestType createVoidPinRequestType() {
        return new VoidPinRequestType();
    }

    /**
     * Create an instance of {@link GetBrandFromEsnResponseType }
     * 
     */
    public GetBrandFromEsnResponseType createGetBrandFromEsnResponseType() {
        return new GetBrandFromEsnResponseType();
    }

    /**
     * Create an instance of {@link RedemptionCardInformationResponseType }
     * 
     */
    public RedemptionCardInformationResponseType createRedemptionCardInformationResponseType() {
        return new RedemptionCardInformationResponseType();
    }

    /**
     * Create an instance of {@link DeviceInformationResponseType }
     * 
     */
    public DeviceInformationResponseType createDeviceInformationResponseType() {
        return new DeviceInformationResponseType();
    }

    /**
     * Create an instance of {@link EsnInformationRequestType }
     * 
     */
    public EsnInformationRequestType createEsnInformationRequestType() {
        return new EsnInformationRequestType();
    }

    /**
     * Create an instance of {@link ValidatePinForESNRequestType }
     * 
     */
    public ValidatePinForESNRequestType createValidatePinForESNRequestType() {
        return new ValidatePinForESNRequestType();
    }

    /**
     * Create an instance of {@link DeviceInformationRequestType }
     * 
     */
    public DeviceInformationRequestType createDeviceInformationRequestType() {
        return new DeviceInformationRequestType();
    }

    /**
     * Create an instance of {@link PlanInfoResponseType }
     * 
     */
    public PlanInfoResponseType createPlanInfoResponseType() {
        return new PlanInfoResponseType();
    }

    /**
     * Create an instance of {@link CheckCarrierPendingResponseType }
     * 
     */
    public CheckCarrierPendingResponseType createCheckCarrierPendingResponseType() {
        return new CheckCarrierPendingResponseType();
    }

    /**
     * Create an instance of {@link ItemTable }
     * 
     */
    public ItemTable createItemTable() {
        return new ItemTable();
    }

    /**
     * Create an instance of {@link RedemptionCardType }
     * 
     */
    public RedemptionCardType createRedemptionCardType() {
        return new RedemptionCardType();
    }

    /**
     * Create an instance of {@link PinStatusTable }
     * 
     */
    public PinStatusTable createPinStatusTable() {
        return new PinStatusTable();
    }

    /**
     * Create an instance of {@link PinStatusRecord }
     * 
     */
    public PinStatusRecord createPinStatusRecord() {
        return new PinStatusRecord();
    }

    /**
     * Create an instance of {@link ItemRecord }
     * 
     */
    public ItemRecord createItemRecord() {
        return new ItemRecord();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeviceServiceInformationResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "getDeviceServiceInformationResponse")
    public JAXBElement<DeviceServiceInformationResponseType> createGetDeviceServiceInformationResponse(DeviceServiceInformationResponseType value) {
        return new JAXBElement<DeviceServiceInformationResponseType>(_GetDeviceServiceInformationResponse_QNAME, DeviceServiceInformationResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsTracfoneProductResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "isTracfoneProductResponse")
    public JAXBElement<IsTracfoneProductResponseType> createIsTracfoneProductResponse(IsTracfoneProductResponseType value) {
        return new JAXBElement<IsTracfoneProductResponseType>(_IsTracfoneProductResponse_QNAME, IsTracfoneProductResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PINCardInformationForESNRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "getPINCardInformationForEsnRequest")
    public JAXBElement<PINCardInformationForESNRequestType> createGetPINCardInformationForEsnRequest(PINCardInformationForESNRequestType value) {
        return new JAXBElement<PINCardInformationForESNRequestType>(_GetPINCardInformationForEsnRequest_QNAME, PINCardInformationForESNRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEsnAttributesResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "getEsnAttributesResponse")
    public JAXBElement<GetEsnAttributesResponseType> createGetEsnAttributesResponse(GetEsnAttributesResponseType value) {
        return new JAXBElement<GetEsnAttributesResponseType>(_GetEsnAttributesResponse_QNAME, GetEsnAttributesResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEsnFromSimRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "getEsnFromSimRequest")
    public JAXBElement<GetEsnFromSimRequestType> createGetEsnFromSimRequest(GetEsnFromSimRequestType value) {
        return new JAXBElement<GetEsnFromSimRequestType>(_GetEsnFromSimRequest_QNAME, GetEsnFromSimRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetEsnAttributesRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "setEsnAttributesRequest")
    public JAXBElement<SetEsnAttributesRequestType> createSetEsnAttributesRequest(SetEsnAttributesRequestType value) {
        return new JAXBElement<SetEsnAttributesRequestType>(_SetEsnAttributesRequest_QNAME, SetEsnAttributesRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EsnFromMinRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "getEsnFromMinRequest")
    public JAXBElement<EsnFromMinRequestType> createGetEsnFromMinRequest(EsnFromMinRequestType value) {
        return new JAXBElement<EsnFromMinRequestType>(_GetEsnFromMinRequest_QNAME, EsnFromMinRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PurchaseDetailsResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "getPurchaseDetailsResponse")
    public JAXBElement<PurchaseDetailsResponseType> createGetPurchaseDetailsResponse(PurchaseDetailsResponseType value) {
        return new JAXBElement<PurchaseDetailsResponseType>(_GetPurchaseDetailsResponse_QNAME, PurchaseDetailsResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllManufacturersResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "getAllManufacturersResponse")
    public JAXBElement<GetAllManufacturersResponseType> createGetAllManufacturersResponse(GetAllManufacturersResponseType value) {
        return new JAXBElement<GetAllManufacturersResponseType>(_GetAllManufacturersResponse_QNAME, GetAllManufacturersResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VoidPinResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "voidPinResponse")
    public JAXBElement<VoidPinResponseType> createVoidPinResponse(VoidPinResponseType value) {
        return new JAXBElement<VoidPinResponseType>(_VoidPinResponse_QNAME, VoidPinResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidatePinForESNResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "validatePinForESNResponse")
    public JAXBElement<ValidatePinForESNResponseType> createValidatePinForESNResponse(ValidatePinForESNResponseType value) {
        return new JAXBElement<ValidatePinForESNResponseType>(_ValidatePinForESNResponse_QNAME, ValidatePinForESNResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PlanInfoResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "planInfoResponse")
    public JAXBElement<PlanInfoResponseType> createPlanInfoResponse(PlanInfoResponseType value) {
        return new JAXBElement<PlanInfoResponseType>(_PlanInfoResponse_QNAME, PlanInfoResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckCarrierPendingResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "checkCarrierPendingResponse")
    public JAXBElement<CheckCarrierPendingResponseType> createCheckCarrierPendingResponse(CheckCarrierPendingResponseType value) {
        return new JAXBElement<CheckCarrierPendingResponseType>(_CheckCarrierPendingResponse_QNAME, CheckCarrierPendingResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeviceInformationRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "getDeviceInformationRequest")
    public JAXBElement<DeviceInformationRequestType> createGetDeviceInformationRequest(DeviceInformationRequestType value) {
        return new JAXBElement<DeviceInformationRequestType>(_GetDeviceInformationRequest_QNAME, DeviceInformationRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValidatePinForESNRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "validatePinForESNRequest")
    public JAXBElement<ValidatePinForESNRequestType> createValidatePinForESNRequest(ValidatePinForESNRequestType value) {
        return new JAXBElement<ValidatePinForESNRequestType>(_ValidatePinForESNRequest_QNAME, ValidatePinForESNRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EsnInformationRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "getEsnInformationRequest")
    public JAXBElement<EsnInformationRequestType> createGetEsnInformationRequest(EsnInformationRequestType value) {
        return new JAXBElement<EsnInformationRequestType>(_GetEsnInformationRequest_QNAME, EsnInformationRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeviceInformationResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "getDeviceInformationResponse")
    public JAXBElement<DeviceInformationResponseType> createGetDeviceInformationResponse(DeviceInformationResponseType value) {
        return new JAXBElement<DeviceInformationResponseType>(_GetDeviceInformationResponse_QNAME, DeviceInformationResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RedemptionCardInformationResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "getRedemptionCardInformationResponse")
    public JAXBElement<RedemptionCardInformationResponseType> createGetRedemptionCardInformationResponse(RedemptionCardInformationResponseType value) {
        return new JAXBElement<RedemptionCardInformationResponseType>(_GetRedemptionCardInformationResponse_QNAME, RedemptionCardInformationResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEsnAttributesResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "getUpdatedEsnAttributesResponse")
    public JAXBElement<GetEsnAttributesResponseType> createGetUpdatedEsnAttributesResponse(GetEsnAttributesResponseType value) {
        return new JAXBElement<GetEsnAttributesResponseType>(_GetUpdatedEsnAttributesResponse_QNAME, GetEsnAttributesResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBrandFromEsnResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "getBrandFromEsnResponse")
    public JAXBElement<GetBrandFromEsnResponseType> createGetBrandFromEsnResponse(GetBrandFromEsnResponseType value) {
        return new JAXBElement<GetBrandFromEsnResponseType>(_GetBrandFromEsnResponse_QNAME, GetBrandFromEsnResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VoidPinRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "voidPinRequest")
    public JAXBElement<VoidPinRequestType> createVoidPinRequest(VoidPinRequestType value) {
        return new JAXBElement<VoidPinRequestType>(_VoidPinRequest_QNAME, VoidPinRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetHandSetInformationResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "getHandSetInformationResponse")
    public JAXBElement<GetHandSetInformationResponseType> createGetHandSetInformationResponse(GetHandSetInformationResponseType value) {
        return new JAXBElement<GetHandSetInformationResponseType>(_GetHandSetInformationResponse_QNAME, GetHandSetInformationResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BurnRedemptionCardResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "burnRedemptionCardResponse")
    public JAXBElement<BurnRedemptionCardResponseType> createBurnRedemptionCardResponse(BurnRedemptionCardResponseType value) {
        return new JAXBElement<BurnRedemptionCardResponseType>(_BurnRedemptionCardResponse_QNAME, BurnRedemptionCardResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDataUsageRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "getDataUsageRequest")
    public JAXBElement<GetDataUsageRequestType> createGetDataUsageRequest(GetDataUsageRequestType value) {
        return new JAXBElement<GetDataUsageRequestType>(_GetDataUsageRequest_QNAME, GetDataUsageRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDataUsageResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "getDataUsageResponse")
    public JAXBElement<GetDataUsageResponseType> createGetDataUsageResponse(GetDataUsageResponseType value) {
        return new JAXBElement<GetDataUsageResponseType>(_GetDataUsageResponse_QNAME, GetDataUsageResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEsnAttributesRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "getUpdatedEsnAttributesRequest")
    public JAXBElement<GetEsnAttributesRequestType> createGetUpdatedEsnAttributesRequest(GetEsnAttributesRequestType value) {
        return new JAXBElement<GetEsnAttributesRequestType>(_GetUpdatedEsnAttributesRequest_QNAME, GetEsnAttributesRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetEsnAttributesResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "setEsnAttributesResponse")
    public JAXBElement<SetEsnAttributesResponseType> createSetEsnAttributesResponse(SetEsnAttributesResponseType value) {
        return new JAXBElement<SetEsnAttributesResponseType>(_SetEsnAttributesResponse_QNAME, SetEsnAttributesResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCardRedeemedDetailsFromSMPsResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "getCardRedeemedDetailsFromSMPsResponse")
    public JAXBElement<GetCardRedeemedDetailsFromSMPsResponseType> createGetCardRedeemedDetailsFromSMPsResponse(GetCardRedeemedDetailsFromSMPsResponseType value) {
        return new JAXBElement<GetCardRedeemedDetailsFromSMPsResponseType>(_GetCardRedeemedDetailsFromSMPsResponse_QNAME, GetCardRedeemedDetailsFromSMPsResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllModelsRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "getAllModelsRequest")
    public JAXBElement<GetAllModelsRequestType> createGetAllModelsRequest(GetAllModelsRequestType value) {
        return new JAXBElement<GetAllModelsRequestType>(_GetAllModelsRequest_QNAME, GetAllModelsRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEsnFromSimResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "getEsnFromSimResponse")
    public JAXBElement<GetEsnFromSimResponseType> createGetEsnFromSimResponse(GetEsnFromSimResponseType value) {
        return new JAXBElement<GetEsnFromSimResponseType>(_GetEsnFromSimResponse_QNAME, GetEsnFromSimResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PurchaseDetailsRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "getPurchaseDetailsRequest")
    public JAXBElement<PurchaseDetailsRequestType> createGetPurchaseDetailsRequest(PurchaseDetailsRequestType value) {
        return new JAXBElement<PurchaseDetailsRequestType>(_GetPurchaseDetailsRequest_QNAME, PurchaseDetailsRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllModelsResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "getAllModelsResponse")
    public JAXBElement<GetAllModelsResponseType> createGetAllModelsResponse(GetAllModelsResponseType value) {
        return new JAXBElement<GetAllModelsResponseType>(_GetAllModelsResponse_QNAME, GetAllModelsResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEsnAttributesRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "getEsnAttributesRequest")
    public JAXBElement<GetEsnAttributesRequestType> createGetEsnAttributesRequest(GetEsnAttributesRequestType value) {
        return new JAXBElement<GetEsnAttributesRequestType>(_GetEsnAttributesRequest_QNAME, GetEsnAttributesRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RedemptionCardInformationRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "getRedemptionCardInformationRequest")
    public JAXBElement<RedemptionCardInformationRequestType> createGetRedemptionCardInformationRequest(RedemptionCardInformationRequestType value) {
        return new JAXBElement<RedemptionCardInformationRequestType>(_GetRedemptionCardInformationRequest_QNAME, RedemptionCardInformationRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsPhoneValidForStoreFrontResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "isPhoneValidForStoreFrontResponseType")
    public JAXBElement<IsPhoneValidForStoreFrontResponseType> createIsPhoneValidForStoreFrontResponseType(IsPhoneValidForStoreFrontResponseType value) {
        return new JAXBElement<IsPhoneValidForStoreFrontResponseType>(_IsPhoneValidForStoreFrontResponseType_QNAME, IsPhoneValidForStoreFrontResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EsnInformationResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "getEsnInformationResponse")
    public JAXBElement<EsnInformationResponseType> createGetEsnInformationResponse(EsnInformationResponseType value) {
        return new JAXBElement<EsnInformationResponseType>(_GetEsnInformationResponse_QNAME, EsnInformationResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PhoneInformationRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "updatePhoneInformationRequest")
    public JAXBElement<PhoneInformationRequestType> createUpdatePhoneInformationRequest(PhoneInformationRequestType value) {
        return new JAXBElement<PhoneInformationRequestType>(_UpdatePhoneInformationRequest_QNAME, PhoneInformationRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EsnFromMinResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "getEsnFromMinResponse")
    public JAXBElement<EsnFromMinResponseType> createGetEsnFromMinResponse(EsnFromMinResponseType value) {
        return new JAXBElement<EsnFromMinResponseType>(_GetEsnFromMinResponse_QNAME, EsnFromMinResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PINCardInformationForESNResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "getPINCardInformationForEsnResponse")
    public JAXBElement<PINCardInformationForESNResponseType> createGetPINCardInformationForEsnResponse(PINCardInformationForESNResponseType value) {
        return new JAXBElement<PINCardInformationForESNResponseType>(_GetPINCardInformationForEsnResponse_QNAME, PINCardInformationForESNResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetBrandFromEsnRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "getBrandFromEsnRequest")
    public JAXBElement<GetBrandFromEsnRequestType> createGetBrandFromEsnRequest(GetBrandFromEsnRequestType value) {
        return new JAXBElement<GetBrandFromEsnRequestType>(_GetBrandFromEsnRequest_QNAME, GetBrandFromEsnRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetHandSetInformationRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "getHandSetInformationRequest")
    public JAXBElement<GetHandSetInformationRequestType> createGetHandSetInformationRequest(GetHandSetInformationRequestType value) {
        return new JAXBElement<GetHandSetInformationRequestType>(_GetHandSetInformationRequest_QNAME, GetHandSetInformationRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PhoneInformationResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "updatePhoneInformationResponse")
    public JAXBElement<PhoneInformationResponseType> createUpdatePhoneInformationResponse(PhoneInformationResponseType value) {
        return new JAXBElement<PhoneInformationResponseType>(_UpdatePhoneInformationResponse_QNAME, PhoneInformationResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeviceServiceInformationRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "getDeviceServiceInformationRequest")
    public JAXBElement<DeviceServiceInformationRequestType> createGetDeviceServiceInformationRequest(DeviceServiceInformationRequestType value) {
        return new JAXBElement<DeviceServiceInformationRequestType>(_GetDeviceServiceInformationRequest_QNAME, DeviceServiceInformationRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCardRedeemedDetailsFromSMPsRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "getCardRedeemedDetailsFromSMPsRequest")
    public JAXBElement<GetCardRedeemedDetailsFromSMPsRequestType> createGetCardRedeemedDetailsFromSMPsRequest(GetCardRedeemedDetailsFromSMPsRequestType value) {
        return new JAXBElement<GetCardRedeemedDetailsFromSMPsRequestType>(_GetCardRedeemedDetailsFromSMPsRequest_QNAME, GetCardRedeemedDetailsFromSMPsRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WifiEligibilityStatusRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "getWifiEligibilityStatusRequest")
    public JAXBElement<WifiEligibilityStatusRequestType> createGetWifiEligibilityStatusRequest(WifiEligibilityStatusRequestType value) {
        return new JAXBElement<WifiEligibilityStatusRequestType>(_GetWifiEligibilityStatusRequest_QNAME, WifiEligibilityStatusRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPinCardInformationResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "getPinCardInformationResponse")
    public JAXBElement<GetPinCardInformationResponseType> createGetPinCardInformationResponse(GetPinCardInformationResponseType value) {
        return new JAXBElement<GetPinCardInformationResponseType>(_GetPinCardInformationResponse_QNAME, GetPinCardInformationResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsTracfoneProductRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "isTracfoneProductRequest")
    public JAXBElement<IsTracfoneProductRequestType> createIsTracfoneProductRequest(IsTracfoneProductRequestType value) {
        return new JAXBElement<IsTracfoneProductRequestType>(_IsTracfoneProductRequest_QNAME, IsTracfoneProductRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPinCardInformationRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "getPinCardInformationRequest")
    public JAXBElement<GetPinCardInformationRequestType> createGetPinCardInformationRequest(GetPinCardInformationRequestType value) {
        return new JAXBElement<GetPinCardInformationRequestType>(_GetPinCardInformationRequest_QNAME, GetPinCardInformationRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsPhoneValidForStoreFrontRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "isPhoneValidForStoreFrontRequestType")
    public JAXBElement<IsPhoneValidForStoreFrontRequestType> createIsPhoneValidForStoreFrontRequestType(IsPhoneValidForStoreFrontRequestType value) {
        return new JAXBElement<IsPhoneValidForStoreFrontRequestType>(_IsPhoneValidForStoreFrontRequestType_QNAME, IsPhoneValidForStoreFrontRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WifiEligibilityStatusResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "getWifiEligibilityStatusResponse")
    public JAXBElement<WifiEligibilityStatusResponseType> createGetWifiEligibilityStatusResponse(WifiEligibilityStatusResponseType value) {
        return new JAXBElement<WifiEligibilityStatusResponseType>(_GetWifiEligibilityStatusResponse_QNAME, WifiEligibilityStatusResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PlanInfoRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "planInfoRequest")
    public JAXBElement<PlanInfoRequestType> createPlanInfoRequest(PlanInfoRequestType value) {
        return new JAXBElement<PlanInfoRequestType>(_PlanInfoRequest_QNAME, PlanInfoRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckCarrierPendingRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "checkCarrierPendingRequest")
    public JAXBElement<CheckCarrierPendingRequestType> createCheckCarrierPendingRequest(CheckCarrierPendingRequestType value) {
        return new JAXBElement<CheckCarrierPendingRequestType>(_CheckCarrierPendingRequest_QNAME, CheckCarrierPendingRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAllManufacturersRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "getAllManufacturersRequest")
    public JAXBElement<GetAllManufacturersRequestType> createGetAllManufacturersRequest(GetAllManufacturersRequestType value) {
        return new JAXBElement<GetAllManufacturersRequestType>(_GetAllManufacturersRequest_QNAME, GetAllManufacturersRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BurnRedemptionCardRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "burnRedemptionCardRequest")
    public JAXBElement<BurnRedemptionCardRequestType> createBurnRedemptionCardRequest(BurnRedemptionCardRequestType value) {
        return new JAXBElement<BurnRedemptionCardRequestType>(_BurnRedemptionCardRequest_QNAME, BurnRedemptionCardRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "err_num", scope = VoidPinResponseType.class)
    public JAXBElement<BigDecimal> createVoidPinResponseTypeErrNum(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_VoidPinResponseTypeErrNum_QNAME, BigDecimal.class, VoidPinResponseType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "ERR_msg", scope = VoidPinResponseType.class)
    public JAXBElement<String> createVoidPinResponseTypeERRMsg(String value) {
        return new JAXBElement<String>(_VoidPinResponseTypeERRMsg_QNAME, String.class, VoidPinResponseType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PinStatusTable }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "out_refund_item", scope = VoidPinResponseType.class)
    public JAXBElement<PinStatusTable> createVoidPinResponseTypeOutRefundItem(PinStatusTable value) {
        return new JAXBElement<PinStatusTable>(_VoidPinResponseTypeOutRefundItem_QNAME, PinStatusTable.class, VoidPinResponseType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ItemTable }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "resp_items", scope = IsTracfoneProductResponseType.class)
    public JAXBElement<ItemTable> createIsTracfoneProductResponseTypeRespItems(ItemTable value) {
        return new JAXBElement<ItemTable>(_IsTracfoneProductResponseTypeRespItems_QNAME, ItemTable.class, IsTracfoneProductResponseType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "err_num", scope = IsTracfoneProductResponseType.class)
    public JAXBElement<BigDecimal> createIsTracfoneProductResponseTypeErrNum(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_VoidPinResponseTypeErrNum_QNAME, BigDecimal.class, IsTracfoneProductResponseType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "err_msg", scope = IsTracfoneProductResponseType.class)
    public JAXBElement<String> createIsTracfoneProductResponseTypeErrMsg(String value) {
        return new JAXBElement<String>(_IsTracfoneProductResponseTypeErrMsg_QNAME, String.class, IsTracfoneProductResponseType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PinStatusTable }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "in_refund_item", scope = VoidPinRequestType.class)
    public JAXBElement<PinStatusTable> createVoidPinRequestTypeInRefundItem(PinStatusTable value) {
        return new JAXBElement<PinStatusTable>(_VoidPinRequestTypeInRefundItem_QNAME, PinStatusTable.class, VoidPinRequestType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "order_id", scope = VoidPinRequestType.class)
    public JAXBElement<String> createVoidPinRequestTypeOrderId(String value) {
        return new JAXBElement<String>(_VoidPinRequestTypeOrderId_QNAME, String.class, VoidPinRequestType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "order_type", scope = VoidPinRequestType.class)
    public JAXBElement<String> createVoidPinRequestTypeOrderType(String value) {
        return new JAXBElement<String>(_VoidPinRequestTypeOrderType_QNAME, String.class, VoidPinRequestType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "rma_id", scope = VoidPinRequestType.class)
    public JAXBElement<String> createVoidPinRequestTypeRmaId(String value) {
        return new JAXBElement<String>(_VoidPinRequestTypeRmaId_QNAME, String.class, VoidPinRequestType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "order_id", scope = GetCardRedeemedDetailsFromSMPsRequestType.class)
    public JAXBElement<String> createGetCardRedeemedDetailsFromSMPsRequestTypeOrderId(String value) {
        return new JAXBElement<String>(_VoidPinRequestTypeOrderId_QNAME, String.class, GetCardRedeemedDetailsFromSMPsRequestType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "order_type", scope = GetCardRedeemedDetailsFromSMPsRequestType.class)
    public JAXBElement<String> createGetCardRedeemedDetailsFromSMPsRequestTypeOrderType(String value) {
        return new JAXBElement<String>(_VoidPinRequestTypeOrderType_QNAME, String.class, GetCardRedeemedDetailsFromSMPsRequestType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PinStatusTable }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "in_pin_status", scope = GetCardRedeemedDetailsFromSMPsRequestType.class)
    public JAXBElement<PinStatusTable> createGetCardRedeemedDetailsFromSMPsRequestTypeInPinStatus(PinStatusTable value) {
        return new JAXBElement<PinStatusTable>(_GetCardRedeemedDetailsFromSMPsRequestTypeInPinStatus_QNAME, PinStatusTable.class, GetCardRedeemedDetailsFromSMPsRequestType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "rma_id", scope = GetCardRedeemedDetailsFromSMPsRequestType.class)
    public JAXBElement<String> createGetCardRedeemedDetailsFromSMPsRequestTypeRmaId(String value) {
        return new JAXBElement<String>(_VoidPinRequestTypeRmaId_QNAME, String.class, GetCardRedeemedDetailsFromSMPsRequestType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "order_id", scope = IsTracfoneProductRequestType.class)
    public JAXBElement<String> createIsTracfoneProductRequestTypeOrderId(String value) {
        return new JAXBElement<String>(_VoidPinRequestTypeOrderId_QNAME, String.class, IsTracfoneProductRequestType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "order_type", scope = IsTracfoneProductRequestType.class)
    public JAXBElement<String> createIsTracfoneProductRequestTypeOrderType(String value) {
        return new JAXBElement<String>(_VoidPinRequestTypeOrderType_QNAME, String.class, IsTracfoneProductRequestType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ItemTable }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "req_items", scope = IsTracfoneProductRequestType.class)
    public JAXBElement<ItemTable> createIsTracfoneProductRequestTypeReqItems(ItemTable value) {
        return new JAXBElement<ItemTable>(_IsTracfoneProductRequestTypeReqItems_QNAME, ItemTable.class, IsTracfoneProductRequestType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "stage", scope = IsTracfoneProductRequestType.class)
    public JAXBElement<String> createIsTracfoneProductRequestTypeStage(String value) {
        return new JAXBElement<String>(_IsTracfoneProductRequestTypeStage_QNAME, String.class, IsTracfoneProductRequestType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "rma_id", scope = IsTracfoneProductRequestType.class)
    public JAXBElement<String> createIsTracfoneProductRequestTypeRmaId(String value) {
        return new JAXBElement<String>(_VoidPinRequestTypeRmaId_QNAME, String.class, IsTracfoneProductRequestType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "err_num", scope = GetCardRedeemedDetailsFromSMPsResponseType.class)
    public JAXBElement<BigDecimal> createGetCardRedeemedDetailsFromSMPsResponseTypeErrNum(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_VoidPinResponseTypeErrNum_QNAME, BigDecimal.class, GetCardRedeemedDetailsFromSMPsResponseType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PinStatusTable }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "out_pin_status", scope = GetCardRedeemedDetailsFromSMPsResponseType.class)
    public JAXBElement<PinStatusTable> createGetCardRedeemedDetailsFromSMPsResponseTypeOutPinStatus(PinStatusTable value) {
        return new JAXBElement<PinStatusTable>(_GetCardRedeemedDetailsFromSMPsResponseTypeOutPinStatus_QNAME, PinStatusTable.class, GetCardRedeemedDetailsFromSMPsResponseType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "err_msg", scope = GetCardRedeemedDetailsFromSMPsResponseType.class)
    public JAXBElement<String> createGetCardRedeemedDetailsFromSMPsResponseTypeErrMsg(String value) {
        return new JAXBElement<String>(_IsTracfoneProductResponseTypeErrMsg_QNAME, String.class, GetCardRedeemedDetailsFromSMPsResponseType.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "is_tracfone", scope = ItemRecord.class)
    public JAXBElement<String> createItemRecordIsTracfone(String value) {
        return new JAXBElement<String>(_ItemRecordIsTracfone_QNAME, String.class, ItemRecord.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "part_number", scope = ItemRecord.class)
    public JAXBElement<String> createItemRecordPartNumber(String value) {
        return new JAXBElement<String>(_ItemRecordPartNumber_QNAME, String.class, ItemRecord.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "smp", scope = ItemRecord.class)
    public JAXBElement<String> createItemRecordSmp(String value) {
        return new JAXBElement<String>(_ItemRecordSmp_QNAME, String.class, ItemRecord.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "esn", scope = ItemRecord.class)
    public JAXBElement<String> createItemRecordEsn(String value) {
        return new JAXBElement<String>(_ItemRecordEsn_QNAME, String.class, ItemRecord.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "status", scope = PinStatusRecord.class)
    public JAXBElement<String> createPinStatusRecordStatus(String value) {
        return new JAXBElement<String>(_PinStatusRecordStatus_QNAME, String.class, PinStatusRecord.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/PhoneServices", name = "smp", scope = PinStatusRecord.class)
    public JAXBElement<String> createPinStatusRecordSmp(String value) {
        return new JAXBElement<String>(_ItemRecordSmp_QNAME, String.class, PinStatusRecord.class, value);
    }

}
