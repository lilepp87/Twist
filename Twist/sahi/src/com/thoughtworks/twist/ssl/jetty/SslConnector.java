/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  org.mortbay.jetty.security.SslSelectChannelConnector
 *  org.mortbay.log.Log
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.thoughtworks.twist.ssl.jetty;

import com.thoughtworks.twist.ssl.certs.SecurityCenter;
import java.lang.reflect.Field;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;
import org.mortbay.jetty.security.SslSelectChannelConnector;
import org.mortbay.log.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SslConnector
extends SslSelectChannelConnector {
    private static final Logger LOG = LoggerFactory.getLogger(SslConnector.class);

    public SslConnector() {
        try {
            SecurityCenter.getInstance().setSSLConnector(this);
        }
        catch (Exception e) {
            LOG.error("Could not create SSL connector", (Throwable)e);
            throw new RuntimeException("Could not create SSL connector", e);
        }
    }

    public void reload() throws Exception {
        Field context = SslSelectChannelConnector.class.getDeclaredField("_context");
        context.setAccessible(true);
        SSLContext sslContext = this.createSSLContext();
        context.set((Object)this, sslContext);
        SSLSession session = sslContext.createSSLEngine().getSession();
        if (this.getHeaderBufferSize() < session.getApplicationBufferSize()) {
            this.setHeaderBufferSize(session.getApplicationBufferSize());
        }
        if (this.getRequestBufferSize() < session.getApplicationBufferSize()) {
            this.setRequestBufferSize(session.getApplicationBufferSize());
        }
        Log.info((String)"Reloaded {}", (Object)((Object)this));
    }
}

