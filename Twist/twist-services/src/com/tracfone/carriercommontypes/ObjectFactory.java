
package com.tracfone.carriercommontypes;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.tracfone.carriercommontypes package. 
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


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.tracfone.carriercommontypes
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PortCarrierType }
     * 
     */
    public PortCarrierType createPortCarrierType() {
        return new PortCarrierType();
    }

    /**
     * Create an instance of {@link PortCarrierListType }
     * 
     */
    public PortCarrierListType createPortCarrierListType() {
        return new PortCarrierListType();
    }

    /**
     * Create an instance of {@link CarrierType }
     * 
     */
    public CarrierType createCarrierType() {
        return new CarrierType();
    }

    /**
     * Create an instance of {@link CarrierListType }
     * 
     */
    public CarrierListType createCarrierListType() {
        return new CarrierListType();
    }

    /**
     * Create an instance of {@link LnpPortOutType }
     * 
     */
    public LnpPortOutType createLnpPortOutType() {
        return new LnpPortOutType();
    }

    /**
     * Create an instance of {@link MdnPortOutDetailType }
     * 
     */
    public MdnPortOutDetailType createMdnPortOutDetailType() {
        return new MdnPortOutDetailType();
    }

    /**
     * Create an instance of {@link BaseCarrierRequestType }
     * 
     */
    public BaseCarrierRequestType createBaseCarrierRequestType() {
        return new BaseCarrierRequestType();
    }

}
