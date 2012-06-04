package com.googlecode.vkapi.exceptions;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.googlecode.vkapi.domain.VkOAuthToken;
import com.googlecode.vkapi.domain.error.VkErrorResponse;

public class VkExceptionsTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void checkIfTokenExpired() {
        VkOAuthToken authToken = new VkOAuthToken("accessToken", 10, 10);
        try {
            VkExceptions.checkIfTokenExpired(authToken);
        } catch (VkException e) {
            fail();
        }
    }

    @Test(expected = VkTokenExpiredException.class)
    public void checkIfTokenExpiredExpired() throws VkTokenExpiredException {
        VkOAuthToken authToken = new VkOAuthToken("accessToken", -10, 10);
        VkExceptions.checkIfTokenExpired(authToken);
    }

    @Test(expected = VkException.class)
    public void rethrowIfVkException() throws VkException {
        Throwable throwable = new VkException("message");
        VkExceptions.rethrowIfVkException(throwable);
    }

    @Test
    public void rethrowIfVkExceptionNotVk() throws VkException {
        Throwable throwable = new Exception("message");
        try {
            VkExceptions.rethrowIfVkException(throwable);
        } catch (VkException e) {
            fail();
        }
    }

    @Test(expected = VkTokenExpiredException.class)
    public void throwAppropriateVkTokenExpiredException() throws VkException {
        VkOAuthToken authToken = new VkOAuthToken("accessToken", 10, 10);
        VkErrorResponse error = new VkErrorResponse(5, "message");
        VkExceptions.throwAppropriate(error, authToken);
    }
    
    @Test(expected = VkApplicatioBlockedException.class)
    public void throwAppropriateVkApplicatioBlockedException() throws VkException {
        VkOAuthToken authToken = new VkOAuthToken("accessToken", 10, 10);
        VkErrorResponse error = new VkErrorResponse(8, "message");
        VkExceptions.throwAppropriate(error, authToken);
    }
    
    @Test(expected = VkException.class)
    public void throwAppropriateVkExceptions() throws VkException {
        VkOAuthToken authToken = new VkOAuthToken("accessToken", 10, 10);
        VkErrorResponse error = new VkErrorResponse(10, "message");
        VkExceptions.throwAppropriate(error, authToken);
    }

}
