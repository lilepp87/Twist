/*
 * Decompiled with CFR 0_118.
 */
package com.thoughtworks.twist.ssl.certs;

import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public abstract class PublicCertPrivateKeyPair {
    public abstract X509Certificate[] certChain() throws Exception;

    public abstract PrivateKey privateKey() throws GeneralSecurityException;

    public X509Certificate cert() throws Exception {
        return this.certChain()[0];
    }
}

