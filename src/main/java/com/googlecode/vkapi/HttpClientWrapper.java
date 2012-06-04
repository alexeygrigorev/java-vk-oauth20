package com.googlecode.vkapi;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;

import com.googlecode.vkapi.exceptions.VkException;

/**
 * Eases using {@link HttpClient} for performing requests to vk.com
 * 
 * @author Alexey Grigorev
 */
class HttpClientWrapper {

    private final HttpClient httpclient;

    public HttpClientWrapper() {
        this(wrapClient(new DefaultHttpClient()));
    }

    HttpClientWrapper(HttpClient httpclient) {
        this.httpclient = httpclient;
    }

    /**
     * Executes GET request and returns response in string
     * 
     * @param uri for the request
     * @return string response
     * @throws VkException
     */
    public String executeGet(String uri) {
        return executeRequest(new HttpGet(uri));
    }

    private String executeRequest(HttpUriRequest request) {
        try {
            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();
            return IOUtils.toString(entity.getContent(), "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Wraps {@link HttpClient} so it can be used for https requests without
     * special certificates and it can work in concurrent environment
     * 
     * @param base object to be wrapped
     * @return wrapped {@link HttpClient}
     */
    private static HttpClient wrapClient(HttpClient base) {
        // http://javaskeleton.blogspot.com/2010/07/avoiding-peer-not-authenticated-with.html
        // wrapping the client to successfully perform https queries without any
        // certificates

        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            ctx.init(null, new TrustManager[] { dontCareTrustManager }, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);

            ClientConnectionManager baseCcm = base.getConnectionManager();
            SchemeRegistry sr = baseCcm.getSchemeRegistry();
            sr.register(new Scheme("https", 443, ssf));

            // http://stackoverflow.com/questions/4612573/exception-using-httprequest-execute-invalid-use-of-singleclientconnmanager-c
            // http://foo.jasonhudgins.com/2010/03/http-connections-revisited.html
            // avoiding
            // "invalid use of SingleClientConnManager: connection still allocated."
            // exception

            ClientConnectionManager safeCcm = new ThreadSafeClientConnManager(sr);
            return new DefaultHttpClient(safeCcm, base.getParams());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Accepts all given certificates
     */
    private static final X509TrustManager dontCareTrustManager = new X509TrustManager() {
        private final X509Certificate[] empty = new X509Certificate[0];

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return empty;
        }

        @Override
        public void checkServerTrusted(X509Certificate[] ar, String st) throws CertificateException {
        }

        @Override
        public void checkClientTrusted(X509Certificate[] ar, String st) throws CertificateException {
        }
    };

}
