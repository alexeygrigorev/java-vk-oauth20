package com.googlecode.vkapi;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.net.URI;

import org.apache.commons.io.input.NullInputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class HttpClientWrapperTest {

    HttpClientWrapper httpClientWrapper;

    @Mock HttpClient httpClient;

    @Mock HttpResponse httpResponse;
    @Mock HttpEntity httpEntity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        httpClientWrapper = new HttpClientWrapper(httpClient);
    }

    @Test
    public void executeGet() throws Exception {
        givenEntityInResponse();
        
        String uriStr = "http://gudok.tele2.ru/";
        httpClientWrapper.executeGet(uriStr);
        
        assertHttpGetPerformed(uriStr);
    }

    private void assertHttpGetPerformed(String uri) throws IOException, ClientProtocolException {
        HttpUriRequest result = captureRequest();
        
        assertEquals(URI.create(uri), result.getURI());
        assertEquals("GET", result.getMethod());
    }

    private HttpUriRequest captureRequest() throws IOException, ClientProtocolException {
        ArgumentCaptor<HttpUriRequest> captor = ArgumentCaptor.forClass(HttpUriRequest.class);
        verify(httpClient).execute(captor.capture());
        return captor.getValue();
    }

    private void givenEntityInResponse() throws Exception {
        when(httpClient.execute(any(HttpGet.class))).thenReturn(httpResponse);
        when(httpResponse.getEntity()).thenReturn(httpEntity);
        when(httpEntity.getContent()).thenReturn(new NullInputStream(0));
    }

}
