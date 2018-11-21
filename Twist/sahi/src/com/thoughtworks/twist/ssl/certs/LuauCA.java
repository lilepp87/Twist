/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.jce.PrincipalUtil
 *  org.bouncycastle.jce.X509Principal
 */
package com.thoughtworks.twist.ssl.certs;

import com.thoughtworks.twist.ssl.certs.KeyPairCreator;
import com.thoughtworks.twist.ssl.certs.PublicCertPrivateKeyPair;
import com.thoughtworks.twist.ssl.certs.X509CertificateGenerator;
import com.thoughtworks.twist.ssl.certs.X509PrincipalGenerator;
import com.thoughtworks.twist.ssl.utils.Clock;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import org.bouncycastle.jce.PrincipalUtil;
import org.bouncycastle.jce.X509Principal;

public class LuauCA {
    private final PrivateKey caPrivKey;
    private final X509Certificate[] caCertChain;

    public LuauCA(PublicCertPrivateKeyPair pair) throws Exception {
        this(pair.privateKey(), pair.certChain());
    }

    private /* varargs */ LuauCA(PrivateKey caPrivKey, X509Certificate ... caCertChain) throws Exception {
        this.caPrivKey = caPrivKey;
        this.caCertChain = caCertChain;
    }

    public X509Certificate[] signCACertificate(X509Principal subjectDn, PublicKey publicKey) throws Exception {
        X509Principal issuerDn = PrincipalUtil.getSubjectX509Principal((X509Certificate)this.caCertChain[0]);
        X509CertificateGenerator certGen = new X509CertificateGenerator(LuauCA.longLongAgo(), issuerDn, subjectDn, publicKey);
        certGen.addSubjectKeyIdExtension(publicKey);
        certGen.addAuthorityKeyIdExtension(this.caCertChain[0]);
        certGen.addBasicConstraintsExtension();
        certGen.addKeyUsageExtension();
        certGen.addExtendedKeyUsageExtension();
        X509Certificate cert = certGen.generate(this.caPrivKey);
        Date now = Clock.now();
        cert.checkValidity(now);
        cert.verify(this.caCertChain[0].getPublicKey());
        return this.createChain(cert);
    }

    public static LuauCA createRoot(String principalOU, String principalEmail) throws Exception {
        X509Principal principal = X509PrincipalGenerator.createX509Principal(X509PrincipalGenerator.withOU(principalOU), X509PrincipalGenerator.withEmailAddress(principalEmail));
        KeyPair caKeyPair = KeyPairCreator.createKeyPair();
        X509Certificate caCertificate = LuauCA.createRootCACertificate(principal, caKeyPair);
        return new LuauCA(caKeyPair.getPrivate(), caCertificate);
    }

    private static X509Certificate createRootCACertificate(X509Principal subjectDn, KeyPair keyPair) throws Exception {
        X509CertificateGenerator certGen = new X509CertificateGenerator(LuauCA.longLongAgo(), subjectDn, subjectDn, keyPair.getPublic());
        certGen.addBasicConstraintsExtension();
        certGen.addKeyUsageExtension();
        certGen.addAuthorityKeyIdExtension(keyPair.getPublic());
        certGen.addSubjectKeyIdExtension(keyPair.getPublic());
        return certGen.generate(keyPair.getPrivate());
    }

    public X509Certificate[] createLuauCASignedCertificate(X509Principal subjectDn, PublicKey publicKey) throws Exception {
        X509Principal issuerDn = PrincipalUtil.getSubjectX509Principal((X509Certificate)this.caCertChain[0]);
        X509CertificateGenerator certGen = new X509CertificateGenerator(LuauCA.longLongAgo(), issuerDn, subjectDn, publicKey);
        return this.createChain(certGen.generate(this.caPrivKey));
    }

    private static Date longLongAgo() {
        return Clock.longLongAgo();
    }

    public X509Certificate[] getCertificates() {
        return this.caCertChain;
    }

    private X509Certificate[] createChain(X509Certificate cert) {
        ArrayList<X509Certificate> chain = new ArrayList<X509Certificate>(Arrays.asList(this.caCertChain));
        chain.add(0, cert);
        return chain.toArray(new X509Certificate[0]);
    }
}

