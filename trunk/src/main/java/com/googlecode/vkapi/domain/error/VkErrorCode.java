package com.googlecode.vkapi.domain.error;

import java.util.HashMap;
import java.util.Map;

/**
 * Error codes returned from vk.com when something goes wrong
 * 
 * @author Alexey Grigorev
 */
public enum VkErrorCode {

    /**
     * When querying vk api with expired auth token
     */
    EXPIRED_AUTH_TOKEN(5),

    /**
     * When querying vk api with token of deleted application; or when the
     * application was blocked by vk
     */
    APPLICATION_BLOCKED(8),

    /**
     * Unknown error (not added to this class)
     */
    UNKNOWN;

    private static final Map<Integer, VkErrorCode> errorCodes = new HashMap<Integer, VkErrorCode>();

    static {
        for (VkErrorCode code : values()) {
            if (code != UNKNOWN) {
                errorCodes.put(code.getErrorCode(), code);
            }
        }
    }

    private int errorCode;

    private VkErrorCode() {
    }

    private VkErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public static VkErrorCode of(int errorCode) {
        if (errorCodes.containsKey(errorCode)) {
            return errorCodes.get(errorCode);
        } else {
            return UNKNOWN;
        }
    }
}
