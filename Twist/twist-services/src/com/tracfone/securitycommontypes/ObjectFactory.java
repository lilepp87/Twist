
package com.tracfone.securitycommontypes;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.tracfone.securitycommontypes package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.tracfone.securitycommontypes
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DeviceAccountCredentialsType }
     * 
     */
    public DeviceAccountCredentialsType createDeviceAccountCredentialsType() {
        return new DeviceAccountCredentialsType();
    }

    /**
     * Create an instance of {@link AuthenticationCredentialsType }
     * 
     */
    public AuthenticationCredentialsType createAuthenticationCredentialsType() {
        return new AuthenticationCredentialsType();
    }

    /**
     * Create an instance of {@link AuthenticationCredentialsTypeMB }
     * 
     */
    public AuthenticationCredentialsTypeMB createAuthenticationCredentialsTypeMB() {
        return new AuthenticationCredentialsTypeMB();
    }

    /**
     * Create an instance of {@link DpCryptoType }
     * 
     */
    public DpCryptoType createDpCryptoType() {
        return new DpCryptoType();
    }

}
