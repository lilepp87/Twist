/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.openssl.PEMWriter
 */
package com.thoughtworks.twist.ssl.certs;

import com.thoughtworks.twist.ssl.certs.CertificateGenerator;
import com.thoughtworks.twist.ssl.certs.KeystorePersistance;
import com.thoughtworks.twist.ssl.jetty.SslConnector;
import com.thoughtworks.twist.ssl.utils.StringUtils;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import org.bouncycastle.openssl.PEMWriter;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class SecurityCenter {
    private static SecurityCenter instance = null;
    private SslConnector sslConnector;
    private final CertificateGenerator certGen;
    private String storePass;
    private final String caCertStore;

    public static SecurityCenter getInstance() throws Exception {
        if (instance == null) {
            String luauConfigDir = System.getProperty("luau.config.dir");
            if (luauConfigDir == null) {
                throw new RuntimeException("Failed to initailize Security Center because luau.config.dir did not set");
            }
            String keyStorePass = System.getProperty("luau.ssl.keystore.password");
            instance = new SecurityCenter(luauConfigDir + "/keystore.jks", luauConfigDir + "/jetty.jks", keyStorePass);
        }
        return instance;
    }

    public SecurityCenter(String caCertStore, String serverCertStore, String storePass) throws Exception {
        this.caCertStore = caCertStore;
        this.storePass = storePass;
        this.certGen = new CertificateGenerator(caCertStore, serverCertStore, storePass);
    }

    public void setSSLConnector(SslConnector sslConnector) {
        this.sslConnector = sslConnector;
    }

    public void generateWebserverCertificate(String hostName) throws Exception {
        this.certGen.generateWebserverCertificate(hostName);
        if (this.sslConnector != null) {
            this.sslConnector.reload();
        }
    }

    public String fetchCaCertChainInPemFormat() throws Exception {
        return this.toPemString(KeystorePersistance.load(this.caCertStore, this.storePass).chain("intermediate-ca"));
    }

    private String toPemString(X509Certificate[] certs) throws IOException {
        return this.toPemString(Arrays.asList(certs));
    }

    private String toPemString(Iterable<X509Certificate> certs) throws IOException {
        ArrayList<String> result = new ArrayList<String>();
        for (X509Certificate cert : certs) {
            result.add(this.toPemString(cert));
        }
        return StringUtils.join(result, "\n\n");
    }

    private String toPemString(Object cert) throws IOException {
        StringWriter writer = new StringWriter();
        PEMWriter pemWriter = new PEMWriter((Writer)writer);
        pemWriter.writeObject(cert);
        pemWriter.close();
        return writer.toString();
    }
}

