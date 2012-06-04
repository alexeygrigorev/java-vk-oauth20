package com.googlecode.vkapi.domain.error;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;

/**
 * Class containing response with error returned from vk.com
 * 
 * @author Alexey Grigorev
 */
public class VkErrorResponse {

    private final int errorCode;
    private final String message;
    private final Collection<VkMethodParam> params = new LinkedHashSet<VkMethodParam>();

    public VkErrorResponse(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    /**
     * Gets error code of this error. For more details use {@link #getError()}
     * or {@link #getMessage()}
     * 
     * @return error code
     */
    public int getErrorCode() {
        return errorCode;
    }

    public VkErrorCode getError() {
        return VkErrorCode.of(errorCode);
    }

    public String getMessage() {
        return message;
    }

    /**
     * @param param which was used when invoked method returned this error
     */
    public void addMethodParam(VkMethodParam param) {
        this.params.add(param);
    }

    /**
     * Returns the params used when invoked the method returned this error
     * 
     * @return unmodifiable collection of method params
     */
    public Collection<VkMethodParam> getParams() {
        return Collections.unmodifiableCollection(params);
    }

    @Override
    public String toString() {
        return "VkErrorResponse [errorCode=" + errorCode + ", message=" + message + ", params=" + params + "]";
    }

}
