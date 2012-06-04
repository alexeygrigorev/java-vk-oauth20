package com.googlecode.vkapi.exceptions;

import com.googlecode.vkapi.domain.error.VkErrorCode;
import com.googlecode.vkapi.domain.error.VkErrorResponse;

/**
 * Base exception for errors returned from vk.com while querying it
 * 
 * @author Alexey Grigorev
 * @see VkTokenExpiredException
 * @see VkErrorCode
 * @see VkErrorResponse
 */
@SuppressWarnings("serial")
public class VkException extends Exception {

    private final VkErrorResponse error;

    public VkException(String message) {
        super(message);
        this.error = null;
    }

    public VkException(VkErrorResponse error) {
        super("Attached error: " + error);
        this.error = error;
    }

    public VkException(VkErrorResponse error, String message) {
        super(message);
        this.error = error;
    }

    public VkErrorResponse getError() {
        return error;
    }

}
