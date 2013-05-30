package com.vijayrc.supportguy.domain;

import lombok.extern.log4j.Log4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.net.ssl.*;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@Component
@Scope("singleton")
@Log4j
public class SSLWorker implements LinkWorker {

    private HostnameVerifier hostnameVerifier;

    public SSLWorker() throws Exception {
        SSLContext mySSLContext = SSLContext.getInstance("TLS");
        mySSLContext.init(new KeyManager[0], new TrustManager[]{new MyTrustManager()}, new SecureRandom());
        SSLContext.setDefault(mySSLContext);
        hostnameVerifier = new MyHostNameVerifier();
    }

    @Override
    public LinkHit process(Link link) throws Exception {
        if(!link.isSSL())
            return LinkHit.noAction();

        URL url = new URL(link.getUrl());
        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setHostnameVerifier(hostnameVerifier);
        String responseMessage = connection.getResponseMessage();
        int statusCode = connection.getResponseCode();
        connection.disconnect();

        return new LinkHit(link.getFullName(), statusCode, responseMessage);
    }

    private static class MyTrustManager implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        }
        @Override
        public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        }
        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }
    }

    private static class MyHostNameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String s, SSLSession sslSession) {
            return true;
        }
    }
}
