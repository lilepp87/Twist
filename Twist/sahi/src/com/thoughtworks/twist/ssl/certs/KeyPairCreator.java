/*
 * Decompiled with CFR 0_118.
 */
package com.thoughtworks.twist.ssl.certs;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.KeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

public class KeyPairCreator {
    public static KeyPair createKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair seed = keyPairGenerator.generateKeyPair();
            RSAPrivateKey privateSeed = (RSAPrivateKey)seed.getPrivate();
            RSAPublicKey publicSeed = (RSAPublicKey)seed.getPublic();
            KeyFactory fact = KeyFactory.getInstance("RSA", "BC");
            RSAPrivateKeySpec privateKeySpec = new RSAPrivateKeySpec(privateSeed.getModulus(), privateSeed.getPrivateExponent());
            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(publicSeed.getModulus(), publicSeed.getPublicExponent());
            return new KeyPair(fact.generatePublic(publicKeySpec), fact.generatePrivate(privateKeySpec));
        }
        catch (Exception e) {
            throw new RuntimeException("Couldn't create public-private key pair", e);
        }
    }
}

