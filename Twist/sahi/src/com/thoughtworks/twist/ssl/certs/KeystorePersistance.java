/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.jce.provider.BouncyCastleProvider
 */
package com.thoughtworks.twist.ssl.certs;

import com.thoughtworks.twist.ssl.certs.PublicCertPrivateKeyPair;
import com.thoughtworks.twist.ssl.utils.FileUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/*
 * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
 */
public class KeystorePersistance {
    private final File keystoreFile;
    private final KeyStore keyStore;
    private final String password;

    public KeystorePersistance(File keystoreFile, KeyStore keyStore, String password) {
        Security.addProvider((Provider)new BouncyCastleProvider());
        this.keystoreFile = keystoreFile;
        this.keyStore = keyStore;
        this.password = password;
    }

    public static KeystorePersistance load(String keystore, String password) throws Exception {
        return KeystorePersistance.load(new File(keystore), password);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static KeystorePersistance load(File keystoreFile, String password) throws IOException, GeneralSecurityException {
        FileInputStream inputStream = null;
        try {
            KeyStore store = KeyStore.getInstance("JKS");
            if (keystoreFile.exists()) {
                inputStream = new FileInputStream(keystoreFile);
                store.load(inputStream, password.toCharArray());
            } else {
                store.load(null, null);
            }
            KeystorePersistance keystorePersistance = new KeystorePersistance(keystoreFile, store, password);
            return keystorePersistance;
        }
        finally {
            FileUtils.closeQuietly(inputStream);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void writeStore() throws IOException, GeneralSecurityException {
        FileOutputStream fileOutputStream = null;
        try {
            FileUtils.mkdir_p(this.keystoreFile.getParentFile());
            fileOutputStream = new FileOutputStream(this.keystoreFile);
            this.keyStore.store(fileOutputStream, this.password.toCharArray());
        }
        finally {
            FileUtils.closeQuietly(fileOutputStream);
        }
    }

    public void setEntry(String alias, KeyStore.Entry entry) throws KeyStoreException {
        KeyStore.PasswordProtection passwordProtection = new KeyStore.PasswordProtection(this.password.toCharArray());
        this.keyStore.setEntry(alias, entry, passwordProtection);
    }

    public void setCertificateEntry(String alias, X509Certificate certificate) throws KeyStoreException {
        this.keyStore.setCertificateEntry(alias, certificate);
    }

    public List<String> aliases() throws KeyStoreException {
        return Collections.list(this.keyStore.aliases());
    }

    public X509Certificate getCertificate(String alias) throws KeyStoreException {
        return (X509Certificate)this.keyStore.getCertificate(alias);
    }

    public PublicCertPrivateKeyPair keyPair(final String alias) {
        return new PublicCertPrivateKeyPair(){

            public X509Certificate[] certChain() throws KeyStoreException {
                return KeystorePersistance.this.chain(alias);
            }

            public PrivateKey privateKey() throws GeneralSecurityException {
                return KeystorePersistance.this.getPrivateKey(alias);
            }
        };
    }

    public PrivateKey getPrivateKey(String alias) throws GeneralSecurityException {
        KeyStore.PasswordProtection passwordProtection = new KeyStore.PasswordProtection(this.password.toCharArray());
        return ((KeyStore.PrivateKeyEntry)this.keyStore.getEntry(alias, passwordProtection)).getPrivateKey();
    }

    public X509Certificate[] chain(String alias) throws KeyStoreException {
        Certificate[] certificateChain = this.keyStore.getCertificateChain(alias);
        return Arrays.asList(certificateChain).toArray(new X509Certificate[0]);
    }

    public List<X509Certificate> getCertificates() {
        ArrayList<X509Certificate> result = new ArrayList<X509Certificate>();
        try {
            for (String alias : this.aliases()) {
                result.add(this.getCertificate(alias));
            }
            return result;
        }
        catch (KeyStoreException e) {
            throw new RuntimeException("Could not read from keystore", e);
        }
    }

    public boolean containsCertificate(X509Certificate cert) {
        try {
            return this.keyStore.getCertificateAlias(cert) != null;
        }
        catch (KeyStoreException e) {
            throw new RuntimeException("Could not check if certificate issued to '" + cert.getSubjectDN() + "' is present is keystore", e);
        }
    }

}

