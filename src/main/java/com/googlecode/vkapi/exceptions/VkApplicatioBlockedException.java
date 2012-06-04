package com.googlecode.vkapi.exceptions;

import com.googlecode.vkapi.domain.OAuthToken;
import com.googlecode.vkapi.domain.error.VkErrorCode;
import com.googlecode.vkapi.domain.error.VkErrorResponse;

/**
 * Thrown then got error with errorCode=8
 * 
 * @author Alexey Grigorev
 * @see VkErrorCode
 * @see VkErrorResponse
 * 
 */
@SuppressWarnings("serial")
public class VkApplicatioBlockedException extends VkException {

    private final OAuthToken authToken;

    public VkApplicatioBlockedException(VkErrorResponse error, OAuthToken authToken) {
        super(error);
        this.authToken = authToken;
    }

    public OAuthToken getAuthToken() {
        return authToken;
    }

}
