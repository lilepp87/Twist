
package com.tracfone.b2b.inventoryservices;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.tracfone.b2b.inventoryservices package. 
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

    private final static QName _CreateResourceResponse_QNAME = new QName("http://b2b.tracfone.com/InventoryServices", "createResourceResponse");
    private final static QName _CreateResourceRequest_QNAME = new QName("http://b2b.tracfone.com/InventoryServices", "createResourceRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.tracfone.b2b.inventoryservices
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CreateResourceRequestType }
     * 
     */
    public CreateResourceRequestType createCreateResourceRequestType() {
        return new CreateResourceRequestType();
    }

    /**
     * Create an instance of {@link CreateResourceResponseType }
     * 
     */
    public CreateResourceResponseType createCreateResourceResponseType() {
        return new CreateResourceResponseType();
    }

    /**
     * Create an instance of {@link ExtensionType }
     * 
     */
    public ExtensionType createExtensionType() {
        return new ExtensionType();
    }

    /**
     * Create an instance of {@link ResourceOrderItemType }
     * 
     */
    public ResourceOrderItemType createResourceOrderItemType() {
        return new ResourceOrderItemType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateResourceResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/InventoryServices", name = "createResourceResponse")
    public JAXBElement<CreateResourceResponseType> createCreateResourceResponse(CreateResourceResponseType value) {
        return new JAXBElement<CreateResourceResponseType>(_CreateResourceResponse_QNAME, CreateResourceResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateResourceRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://b2b.tracfone.com/InventoryServices", name = "createResourceRequest")
    public JAXBElement<CreateResourceRequestType> createCreateResourceRequest(CreateResourceRequestType value) {
        return new JAXBElement<CreateResourceRequestType>(_CreateResourceRequest_QNAME, CreateResourceRequestType.class, null, value);
    }

}
