package com.googlecode.vkapi.exceptions;

import com.googlecode.vkapi.domain.OAuthToken;
import com.googlecode.vkapi.domain.error.VkErrorCode;
import com.googlecode.vkapi.domain.error.VkErrorResponse;

/**
 * Utility class for working with subclasses of {@link VkException}
 * 
 * @author Alexey Grigorev
 */
public final class VkExceptions {

    private VkExceptions() {
    }

    /**
     * Checks given {@link OAuthToken} whether it is expired or not, and throws
     * {@link VkTokenExpiredException} if it is
     * 
     * @param authToken to be checked
     * @return the same authToken
     * @throws VkTokenExpiredException if token is expired
     */
    public static OAuthToken checkIfTokenExpired(OAuthToken authToken) throws VkTokenExpiredException {
        if (authToken.isExpired()) {
            throw new VkTokenExpiredException(authToken);
        }

        return authToken;
    }

    /**
     * Throws the given throwable if it is an instance of VkException
     * 
     * @param throwable to be checked
     * @throws VkException if the throwable is VkException
     */
    public static void rethrowIfVkException(Throwable throwable) throws VkException {
        if (throwable instanceof VkException) {
            throw (VkException) throwable;
        }
    }

    /**
     * For given {@link VkErrorResponse}, picks up appropriate type of exception
     * and throws it. If no appropriate exception is found, VkException is
     * thrown
     * 
     * @param error to be throwned in exception
     * @param authToken with which the error was obtained
     * @throws VkException always
     * @see VkErrorCode
     * @see VkErrorResponse
     */
    public static void throwAppropriate(VkErrorResponse error, OAuthToken authToken) throws VkException {
        switch (error.getError()) {
            case EXPIRED_AUTH_TOKEN:
                throw new VkTokenExpiredException(error, authToken);

            case APPLICATION_BLOCKED:
                throw new VkApplicatioBlockedException(error, authToken);

            default:
                throw new VkException(error);
        }
    }

}
