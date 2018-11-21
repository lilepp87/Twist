/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.ASN1Encodable
 *  org.bouncycastle.asn1.ASN1ObjectIdentifier
 *  org.bouncycastle.asn1.DERObject
 *  org.bouncycastle.asn1.x500.X500Name
 *  org.bouncycastle.asn1.x509.BasicConstraints
 *  org.bouncycastle.asn1.x509.ExtendedKeyUsage
 *  org.bouncycastle.asn1.x509.KeyPurposeId
 *  org.bouncycastle.asn1.x509.KeyUsage
 *  org.bouncycastle.asn1.x509.SubjectPublicKeyInfo
 *  org.bouncycastle.asn1.x509.X509Extension
 *  org.bouncycastle.cert.X509CertificateHolder
 *  org.bouncycastle.cert.X509v3CertificateBuilder
 *  org.bouncycastle.cert.jcajce.JcaX509CertificateConverter
 *  org.bouncycastle.jce.X509Principal
 *  org.bouncycastle.operator.ContentSigner
 *  org.bouncycastle.operator.OperatorCreationException
 *  org.bouncycastle.operator.jcajce.JcaContentSignerBuilder
 *  org.bouncycastle.x509.extension.AuthorityKeyIdentifierStructure
 *  org.bouncycastle.x509.extension.SubjectKeyIdentifierStructure
 */
package com.thoughtworks.twist.ssl.certs;

import com.thoughtworks.twist.ssl.utils.Clock;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Vector;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.ExtendedKeyUsage;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x509.X509Extension;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.x509.extension.AuthorityKeyIdentifierStructure;
import org.bouncycastle.x509.extension.SubjectKeyIdentifierStructure;

public class X509CertificateGenerator {
    private final X509v3CertificateBuilder v3CertGen;

    public X509CertificateGenerator(Date startDate, X509Principal issuerDn, X509Principal subjectDn, PublicKey publicKey) {
        this.v3CertGen = new X509v3CertificateBuilder(X500Name.getInstance((Object)issuerDn.toASN1Object()), this.serialNumber(), startDate, Clock.yearsFromNow(10), X500Name.getInstance((Object)subjectDn.toASN1Object()), SubjectPublicKeyInfo.getInstance((Object)publicKey.getEncoded()));
    }

    public void addSubjectKeyIdExtension(PublicKey key) throws CertificateParsingException, InvalidKeyException {
        this.v3CertGen.addExtension(X509Extension.subjectKeyIdentifier, false, (ASN1Encodable)new SubjectKeyIdentifierStructure(key));
    }

    public void addAuthorityKeyIdExtension(X509Certificate cert) throws CertificateParsingException {
        this.v3CertGen.addExtension(X509Extension.authorityKeyIdentifier, false, (ASN1Encodable)new AuthorityKeyIdentifierStructure(cert));
    }

    public void addAuthorityKeyIdExtension(PublicKey key) throws InvalidKeyException {
        this.v3CertGen.addExtension(X509Extension.authorityKeyIdentifier, false, (ASN1Encodable)new AuthorityKeyIdentifierStructure(key));
    }

    public void addBasicConstraintsExtension() {
        this.v3CertGen.addExtension(X509Extension.basicConstraints, true, (ASN1Encodable)new BasicConstraints(true));
    }

    public X509Certificate generate(PrivateKey caPrivKey) throws GeneralSecurityException {
        try {
            ContentSigner signer = new JcaContentSignerBuilder("SHA1WithRSA").setProvider("BC").build(caPrivKey);
            X509CertificateHolder certHolder = this.v3CertGen.build(signer);
            return new JcaX509CertificateConverter().setProvider("BC").getCertificate(certHolder);
        }
        catch (OperatorCreationException e) {
            throw new GeneralSecurityException((Throwable)e);
        }
    }

    public void addKeyUsageExtension() {
        this.v3CertGen.addExtension(X509Extension.keyUsage, true, (ASN1Encodable)new KeyUsage(6));
    }

    private BigInteger serialNumber() {
        return new BigInteger(Long.toString(Math.round(Math.random() * 1.1234455544545E13)));
    }

    public void addExtendedKeyUsageExtension() {
        Vector<KeyPurposeId> usages = new Vector<KeyPurposeId>();
        usages.add(KeyPurposeId.id_kp_serverAuth);
        usages.add(KeyPurposeId.id_kp_clientAuth);
        this.v3CertGen.addExtension(X509Extension.extendedKeyUsage, false, (ASN1Encodable)new ExtendedKeyUsage(usages));
    }
}

