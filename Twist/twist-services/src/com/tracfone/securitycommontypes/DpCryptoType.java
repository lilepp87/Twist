
package com.tracfone.securitycommontypes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DpCryptoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DpCryptoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idHash" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="encryptedSessionKey" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cryptoCert" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="keyTransportAlgorithm" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="decryptAlgorithm" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="encryptedValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DpCryptoType", propOrder = {
    "idHash",
    "encryptedSessionKey",
    "cryptoCert",
    "keyTransportAlgorithm",
    "decryptAlgorithm",
    "encryptedValue"
})
public class DpCryptoType {

    @XmlElement(required = true)
    protected String idHash;
    @XmlElement(required = true)
    protected String encryptedSessionKey;
    @XmlElement(required = true)
    protected String cryptoCert;
    @XmlElement(required = true)
    protected String keyTransportAlgorithm;
    @XmlElement(required = true)
    protected String decryptAlgorithm;
    @XmlElement(required = true)
    protected String encryptedValue;

    /**
     * Gets the value of the idHash property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdHash() {
        return idHash;
    }

    /**
     * Sets the value of the idHash property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdHash(String value) {
        this.idHash = value;
    }

    /**
     * Gets the value of the encryptedSessionKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEncryptedSessionKey() {
        return encryptedSessionKey;
    }

    /**
     * Sets the value of the encryptedSessionKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEncryptedSessionKey(String value) {
        this.encryptedSessionKey = value;
    }

    /**
     * Gets the value of the cryptoCert property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCryptoCert() {
        return cryptoCert;
    }

    /**
     * Sets the value of the cryptoCert property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCryptoCert(String value) {
        this.cryptoCert = value;
    }

    /**
     * Gets the value of the keyTransportAlgorithm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKeyTransportAlgorithm() {
        return keyTransportAlgorithm;
    }

    /**
     * Sets the value of the keyTransportAlgorithm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKeyTransportAlgorithm(String value) {
        this.keyTransportAlgorithm = value;
    }

    /**
     * Gets the value of the decryptAlgorithm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDecryptAlgorithm() {
        return decryptAlgorithm;
    }

    /**
     * Sets the value of the decryptAlgorithm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDecryptAlgorithm(String value) {
        this.decryptAlgorithm = value;
    }

    /**
     * Gets the value of the encryptedValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEncryptedValue() {
        return encryptedValue;
    }

    /**
     * Sets the value of the encryptedValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEncryptedValue(String value) {
        this.encryptedValue = value;
    }

}
