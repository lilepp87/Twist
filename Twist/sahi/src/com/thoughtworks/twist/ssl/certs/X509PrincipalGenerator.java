/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  org.bouncycastle.asn1.DERObjectIdentifier
 *  org.bouncycastle.jce.X509Principal
 */
package com.thoughtworks.twist.ssl.certs;

import java.util.Hashtable;
import java.util.Vector;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.jce.X509Principal;

public class X509PrincipalGenerator {
    private final Hashtable<DERObjectIdentifier, String> attrs = new Hashtable();
    private final Vector<DERObjectIdentifier> order = new Vector();

    public static /* varargs */ X509Principal createX509Principal(PrincipalIdentifier ... identifiers) {
        return new X509PrincipalGenerator(identifiers).principal();
    }

    public static PrincipalIdentifier withOU(String ou) {
        return new PrincipalIdentifier(X509Principal.OU, ou);
    }

    public static PrincipalIdentifier withCN(String cn) {
        return new PrincipalIdentifier(X509Principal.CN, cn);
    }

    public static PrincipalIdentifier withEmailAddress(String emailAddress) {
        return new PrincipalIdentifier(X509Principal.EmailAddress, emailAddress);
    }

    private /* varargs */ X509PrincipalGenerator(PrincipalIdentifier ... identifiers) {
        for (PrincipalIdentifier identifier : identifiers) {
            this.order.addElement(identifier.identifier);
            this.attrs.put(identifier.identifier, identifier.value);
        }
    }

    private X509Principal principal() {
        return new X509Principal(this.order, this.attrs);
    }

    public static class PrincipalIdentifier {
        private DERObjectIdentifier identifier;
        private String value;

        private PrincipalIdentifier(DERObjectIdentifier identifier, String value) {
            this.identifier = identifier;
            this.value = value;
        }
    }

}

