
package com.tracfone.b2b.redemptionservices;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.tracfone.b2b.redemptionservices package. 
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

    private final static QName _QueuePINCardResponse_QNAME = new QName("http://b2b.tracfone.com/RedemptionServices", "queuePINCardResponse");
    private final static QName _QueuePINCardRequest_QNAME = new QName("http://b2b.tracfone.com/RedemptionServices", "queuePINCardRequest");
    private final static QName _RedeemQueuedPINCardResponse_QNAME = new QName("http://b2b.tracfone.com/RedemptionServices", "redeemQueuedPINCardResponse");
    private final static QName _RedeemPINCardResponse_QNAME = new QName("http://b2b.tracfone.com/RedemptionServices", "redeemPINCardResponse");
    private final static QName _RedeemPINCardRequest_QNAME = new QName("http://b2b.tracfone.com/RedemptionServices", "redeemPINCardRequest");
    private final static QName _RedeemQueuedPINCardRequest_QNAME = new QName("http://b2b.tracfone.com/RedemptionServices", "redeemQueuedPINCardRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.tracfone.b2b.redemptionservices
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PINCardRedemptionRequestType }
     * 
     */
    public PINCardRedemptionRequestType createPINCardRedemptionRequestType() {
        return new PINCardRedemptionRequestType();
    }

    /**
     * Create an instance of {@link QueuedPINCardRedemptionRequestType }
     * 
     */
    public QueuedPINCardRedemptionRequestType createQueuedPINCardRedemptionRequestType() {
        return new QueuedPINCardRedemptionRequestType();
    }

    /**
     * Create an instance of {@link PINCardQueuingRequestType }
     * 
     */
    public PINCardQueuingRequestType createPINCardQueuingRequestType() {
        return new PINCardQueuingRequestType();
    }

    /**
     * Create an instance of {@link PINCardQueuingResponseType }
     * 
     */
    public PINCardQueuingResponseType createPINCardQueuingResponseType() {
        return new PINCardQueuingResponseType();
    }

    /**
     * Create an instance of {@link PINCardRedemptionResponseType }
     * 
     */
    public PINCardRedemptionResponseType createPINCardRedemptionResponseType() {
        return new PINCardRedemptionResponseType();
    }

    /**
     * Create an instance of {@link QueuedPINCardRedemptionResponseType }
     * 
     */
    public QueuedPINCardRedemptionResponseType createQueuedPINCardRedemptionResponseType() {
        return new QueuedPINCardRedemptionResponseType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PINCardQueuingResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/RedemptionServices", name = "queuePINCardResponse")
    public JAXBElement<PINCardQueuingResponseType> createQueuePINCardResponse(PINCardQueuingResponseType value) {
        return new JAXBElement<PINCardQueuingResponseType>(_QueuePINCardResponse_QNAME, PINCardQueuingResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PINCardQueuingRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/RedemptionServices", name = "queuePINCardRequest")
    public JAXBElement<PINCardQueuingRequestType> createQueuePINCardRequest(PINCardQueuingRequestType value) {
        return new JAXBElement<PINCardQueuingRequestType>(_QueuePINCardRequest_QNAME, PINCardQueuingRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueuedPINCardRedemptionResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/RedemptionServices", name = "redeemQueuedPINCardResponse")
    public JAXBElement<QueuedPINCardRedemptionResponseType> createRedeemQueuedPINCardResponse(QueuedPINCardRedemptionResponseType value) {
        return new JAXBElement<QueuedPINCardRedemptionResponseType>(_RedeemQueuedPINCardResponse_QNAME, QueuedPINCardRedemptionResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PINCardRedemptionResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/RedemptionServices", name = "redeemPINCardResponse")
    public JAXBElement<PINCardRedemptionResponseType> createRedeemPINCardResponse(PINCardRedemptionResponseType value) {
        return new JAXBElement<PINCardRedemptionResponseType>(_RedeemPINCardResponse_QNAME, PINCardRedemptionResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PINCardRedemptionRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/RedemptionServices", name = "redeemPINCardRequest")
    public JAXBElement<PINCardRedemptionRequestType> createRedeemPINCardRequest(PINCardRedemptionRequestType value) {
        return new JAXBElement<PINCardRedemptionRequestType>(_RedeemPINCardRequest_QNAME, PINCardRedemptionRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueuedPINCardRedemptionRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/RedemptionServices", name = "redeemQueuedPINCardRequest")
    public JAXBElement<QueuedPINCardRedemptionRequestType> createRedeemQueuedPINCardRequest(QueuedPINCardRedemptionRequestType value) {
        return new JAXBElement<QueuedPINCardRedemptionRequestType>(_RedeemQueuedPINCardRequest_QNAME, QueuedPINCardRedemptionRequestType.class, null, value);
    }

}
