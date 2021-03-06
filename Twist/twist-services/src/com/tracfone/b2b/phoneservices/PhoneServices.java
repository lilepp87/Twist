
package com.tracfone.b2b.phoneservices;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebService(name = "PhoneServices", targetNamespace = "http://b2b.tracfone.com/PhoneServices/")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({
    com.tracfone.runtimefault.ObjectFactory.class,
    com.tracfone.accountcommontypes.ObjectFactory.class,
    com.tracfone.b2b.phoneservices.ObjectFactory.class,
    com.tracfone.carriercommontypes.ObjectFactory.class,
    com.tracfone.commontypes.ObjectFactory.class,
    com.tracfone.paymentcommontypes.ObjectFactory.class,
    com.tracfone.phonecommontypes.ObjectFactory.class,
    com.tracfone.securitycommontypes.ObjectFactory.class,
    com.tracfone.serviceplancommontypes.ObjectFactory.class
})
public interface PhoneServices {


    /**
     * 
     * @param parameters
     * @return
     *     returns com.tracfone.b2b.phoneservices.WifiEligibilityStatusResponseType
     */
    @WebMethod(action = "http://b2b.tracfone.com/PhoneServices/getWifiEligibilityStatus")
    @WebResult(name = "getWifiEligibilityStatusResponse", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
    public WifiEligibilityStatusResponseType getWifiEligibilityStatus(
        @WebParam(name = "getWifiEligibilityStatusRequest", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
        WifiEligibilityStatusRequestType parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns com.tracfone.b2b.phoneservices.RedemptionCardInformationResponseType
     */
    @WebMethod(action = "http://b2b.tracfone.com/PhoneServices/getRedemptionCardInformation")
    @WebResult(name = "getRedemptionCardInformationResponse", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
    public RedemptionCardInformationResponseType getRedemptionCardInformation(
        @WebParam(name = "getRedemptionCardInformationRequest", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
        RedemptionCardInformationRequestType parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns com.tracfone.b2b.phoneservices.GetEsnAttributesResponseType
     */
    @WebMethod(action = "http://b2b.tracfone.com/PhoneServices/getUpdatedEsnAttributes")
    @WebResult(name = "getUpdatedEsnAttributesResponse", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
    public GetEsnAttributesResponseType getUpdatedEsnAttributes(
        @WebParam(name = "getUpdatedEsnAttributesRequest", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
        GetEsnAttributesRequestType parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns com.tracfone.b2b.phoneservices.PhoneInformationResponseType
     */
    @WebMethod(action = "http://b2b.tracfone.com/PhoneServices/updatePhoneInformation")
    @WebResult(name = "updatePhoneInformationResponse", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
    public PhoneInformationResponseType updatePhoneInformation(
        @WebParam(name = "updatePhoneInformationRequest", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
        PhoneInformationRequestType parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns com.tracfone.b2b.phoneservices.EsnInformationResponseType
     */
    @WebMethod(action = "http://b2b.tracfone.com/PhoneServices/getEsnInformation")
    @WebResult(name = "getEsnInformationResponse", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
    public EsnInformationResponseType getEsnInformation(
        @WebParam(name = "getEsnInformationRequest", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
        EsnInformationRequestType parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns com.tracfone.b2b.phoneservices.PINCardInformationForESNResponseType
     */
    @WebMethod(action = "http://b2b.tracfone.com/PhoneServices/getPinCardInformationForESN")
    @WebResult(name = "getPINCardInformationForEsnResponse", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
    public PINCardInformationForESNResponseType getPinCardInformationForESN(
        @WebParam(name = "getPINCardInformationForEsnRequest", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
        PINCardInformationForESNRequestType parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns com.tracfone.b2b.phoneservices.PurchaseDetailsResponseType
     */
    @WebMethod(action = "http://b2b.tracfone.com/PhoneServices/getPinCardPurchaseDetails")
    @WebResult(name = "getPurchaseDetailsResponse", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
    public PurchaseDetailsResponseType getPinCardPurchaseDetails(
        @WebParam(name = "getPurchaseDetailsRequest", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
        PurchaseDetailsRequestType parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns com.tracfone.b2b.phoneservices.EsnFromMinResponseType
     */
    @WebMethod(action = "http://b2b.tracfone.com/PhoneServices/getEsnFromMin")
    @WebResult(name = "getEsnFromMinResponse", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
    public EsnFromMinResponseType getEsnFromMin(
        @WebParam(name = "getEsnFromMinRequest", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
        EsnFromMinRequestType parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns com.tracfone.b2b.phoneservices.GetAllManufacturersResponseType
     */
    @WebMethod(action = "http://b2b.tracfone.com/PhoneServices/getAllManufacturers")
    @WebResult(name = "getAllManufacturersResponse", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
    public GetAllManufacturersResponseType getAllManufacturers(
        @WebParam(name = "getAllManufacturersRequest", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
        GetAllManufacturersRequestType parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns com.tracfone.b2b.phoneservices.GetAllModelsResponseType
     */
    @WebMethod(action = "http://b2b.tracfone.com/PhoneServices/getAllModels")
    @WebResult(name = "getAllModelsResponse", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
    public GetAllModelsResponseType getAllModels(
        @WebParam(name = "getAllModelsRequest", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
        GetAllModelsRequestType parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns com.tracfone.b2b.phoneservices.BurnRedemptionCardResponseType
     */
    @WebMethod(action = "http://b2b.tracfone.com/PhoneServices/burnRedemptionCard")
    @WebResult(name = "burnRedemptionCardResponse", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
    public BurnRedemptionCardResponseType burnRedemptionCard(
        @WebParam(name = "burnRedemptionCardRequest", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
        BurnRedemptionCardRequestType parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns com.tracfone.b2b.phoneservices.GetHandSetInformationResponseType
     */
    @WebMethod(action = "http://b2b.tracfone.com/PhoneServices/getHandSetInfo")
    @WebResult(name = "getHandSetInformationResponse", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
    public GetHandSetInformationResponseType getHandSetInfo(
        @WebParam(name = "getHandSetInformationRequest", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
        GetHandSetInformationRequestType parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns com.tracfone.b2b.phoneservices.DeviceInformationResponseType
     */
    @WebMethod(action = "http://b2b.tracfone.com/PhoneServices/getDeviceInformation")
    @WebResult(name = "getDeviceInformationResponse", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
    public DeviceInformationResponseType getDeviceInformation(
        @WebParam(name = "getDeviceInformationRequest", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
        DeviceInformationRequestType parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns com.tracfone.b2b.phoneservices.DeviceServiceInformationResponseType
     */
    @WebMethod(action = "http://b2b.tracfone.com/PhoneServices/getDeviceServiceInformation")
    @WebResult(name = "getDeviceServiceInformationResponse", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
    public DeviceServiceInformationResponseType getDeviceServiceInformation(
        @WebParam(name = "getDeviceServiceInformationRequest", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
        DeviceServiceInformationRequestType parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns com.tracfone.b2b.phoneservices.GetBrandFromEsnResponseType
     */
    @WebMethod(action = "http://b2b.tracfone.com/PhoneServices/getBrandFromEsn")
    @WebResult(name = "getBrandFromEsnResponse", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
    public GetBrandFromEsnResponseType getBrandFromEsn(
        @WebParam(name = "getBrandFromEsnRequest", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
        GetBrandFromEsnRequestType parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns com.tracfone.b2b.phoneservices.GetPinCardInformationResponseType
     */
    @WebMethod(action = "http://b2b.tracfone.com/PhoneServices/getPinCardInformation")
    @WebResult(name = "getPinCardInformationResponse", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
    public GetPinCardInformationResponseType getPinCardInformation(
        @WebParam(name = "getPinCardInformationRequest", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
        GetPinCardInformationRequestType parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns com.tracfone.b2b.phoneservices.GetEsnAttributesResponseType
     */
    @WebMethod(action = "http://b2b.tracfone.com/PhoneServices/getEsnAttributes")
    @WebResult(name = "getEsnAttributesResponse", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
    public GetEsnAttributesResponseType getEsnAttributes(
        @WebParam(name = "getEsnAttributesRequest", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
        GetEsnAttributesRequestType parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns com.tracfone.b2b.phoneservices.SetEsnAttributesResponseType
     */
    @WebMethod(action = "http://b2b.tracfone.com/PhoneServices/setEsnAttributes")
    @WebResult(name = "setEsnAttributesResponse", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
    public SetEsnAttributesResponseType setEsnAttributes(
        @WebParam(name = "setEsnAttributesRequest", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
        SetEsnAttributesRequestType parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns com.tracfone.b2b.phoneservices.GetEsnFromSimResponseType
     */
    @WebMethod(action = "http://b2b.tracfone.com/PhoneServices/getESNFromSim")
    @WebResult(name = "getEsnFromSimResponse", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
    public GetEsnFromSimResponseType getESNFromSim(
        @WebParam(name = "getEsnFromSimRequest", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
        GetEsnFromSimRequestType parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns com.tracfone.b2b.phoneservices.IsPhoneValidForStoreFrontResponseType
     */
    @WebMethod(action = "http://b2b.tracfone.com/PhoneServices/isPhoneValidForStoreFront")
    @WebResult(name = "isPhoneValidForStoreFrontResponseType", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
    public IsPhoneValidForStoreFrontResponseType isPhoneValidForStoreFront(
        @WebParam(name = "isPhoneValidForStoreFrontRequestType", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
        IsPhoneValidForStoreFrontRequestType parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns com.tracfone.b2b.phoneservices.ValidatePinForESNResponseType
     */
    @WebMethod(action = "http://b2b.tracfone.com/PhoneServices/validatePinForESN")
    @WebResult(name = "validatePinForESNResponse", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
    public ValidatePinForESNResponseType validatePinForESN(
        @WebParam(name = "validatePinForESNRequest", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
        ValidatePinForESNRequestType parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns com.tracfone.b2b.phoneservices.CheckCarrierPendingResponseType
     */
    @WebMethod(action = "http://b2b.tracfone.com/PhoneServices/checkCarrierPending")
    @WebResult(name = "checkCarrierPendingResponse", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
    public CheckCarrierPendingResponseType checkCarrierPending(
        @WebParam(name = "checkCarrierPendingRequest", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
        CheckCarrierPendingRequestType parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns com.tracfone.b2b.phoneservices.GetDataUsageResponseType
     */
    @WebMethod(action = "http://b2b.tracfone.com/PhoneServices/getDataUsage")
    @WebResult(name = "getDataUsageResponse", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
    public GetDataUsageResponseType getDataUsage(
        @WebParam(name = "getDataUsageRequest", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
        GetDataUsageRequestType parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns com.tracfone.b2b.phoneservices.VoidPinResponseType
     */
    @WebMethod(action = "http://www.tracfone.com/VoidPinDS/voidPin")
    @WebResult(name = "voidPinResponse", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
    public VoidPinResponseType voidPin(
        @WebParam(name = "voidPinRequest", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
        VoidPinRequestType parameters);

    /**
     * 
     * @param parameters
     * @return
     *     returns com.tracfone.b2b.phoneservices.GetCardRedeemedDetailsFromSMPsResponseType
     */
    @WebMethod(action = "http://www.tracfone.com/GetCardRedeemedDtlsFrmSMPsDS/getCardRedeemedDtlsFrmSMPs")
    @WebResult(name = "getCardRedeemedDetailsFromSMPsResponse", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
    public GetCardRedeemedDetailsFromSMPsResponseType getCardRedeemedDtlsFrmSMPs(
        @WebParam(name = "getCardRedeemedDetailsFromSMPsRequest", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "parameters")
        GetCardRedeemedDetailsFromSMPsRequestType parameters);

    /**
     * 
     * @param payload
     * @return
     *     returns com.tracfone.b2b.phoneservices.IsTracfoneProductResponseType
     */
    @WebMethod(action = "http://www.tracfone.com/IsTracfoneProductDS/isTracfoneProduct")
    @WebResult(name = "isTracfoneProductResponse", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "payload")
    public IsTracfoneProductResponseType isTracfoneProduct(
        @WebParam(name = "isTracfoneProductRequest", targetNamespace = "http://b2b.tracfone.com/PhoneServices", partName = "payload")
        IsTracfoneProductRequestType payload);

}
