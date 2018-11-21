/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.jce.X509Principal
 *  org.bouncycastle.jce.provider.BouncyCastleProvider
 */
package com.thoughtworks.twist.ssl.certs;

import com.thoughtworks.twist.ssl.certs.KeyPairCreator;
import com.thoughtworks.twist.ssl.certs.KeystorePersistance;
import com.thoughtworks.twist.ssl.certs.LuauCA;
import com.thoughtworks.twist.ssl.certs.PublicCertPrivateKeyPair;
import com.thoughtworks.twist.ssl.certs.X509PrincipalGenerator;
import java.io.File;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class CertificateGenerator {
    private static final String KEY_ALIAS = "sahi";
    private static final String WEB_CERTIFICATE_NAME = "Twist WebServer Certificate";
    private static final String INTERMEDIATE_CA_ALIAS = "intermediate-ca";
    private final KeystorePersistance keystore;
    private final KeystorePersistance webserverStore;
    private final String password;

    public CertificateGenerator(String keystore, String webserverStore, String password) throws Exception {
        this(new File(keystore), new File(webserverStore), password);
    }

    public CertificateGenerator(File keystore, File webserverStore, String password) throws Exception {
        this.password = password;
        Security.addProvider((Provider)new BouncyCastleProvider());
        this.keystore = KeystorePersistance.load(keystore, this.password);
        this.webserverStore = KeystorePersistance.load(webserverStore, password);
    }

    public void generateWebserverCertificate(String hostname) throws Exception {
        PublicCertPrivateKeyPair keyPair = this.generateSingedCertAndPrivateKey(hostname, "Twist WebServer Certificate");
        this.webserverStore.setEntry("sahi", new KeyStore.PrivateKeyEntry(keyPair.privateKey(), keyPair.certChain()));
        this.webserverStore.writeStore();
    }

    private PublicCertPrivateKeyPair generateSingedCertAndPrivateKey(String cn, String ou) throws Exception {
        final KeyPair keyPair = KeyPairCreator.createKeyPair();
        final X509Principal subjectDn = X509PrincipalGenerator.createX509Principal(X509PrincipalGenerator.withCN(cn), X509PrincipalGenerator.withOU(ou));
        return new PublicCertPrivateKeyPair(){

            public X509Certificate[] certChain() throws Exception {
                return CertificateGenerator.this.createIntermediateLuauCA().createLuauCASignedCertificate(subjectDn, keyPair.getPublic());
            }

            public PrivateKey privateKey() throws GeneralSecurityException {
                return keyPair.getPrivate();
            }
        };
    }

    private LuauCA createIntermediateLuauCA() throws Exception {
        return new LuauCA(this.keystore.keyPair("intermediate-ca"));
    }

}

