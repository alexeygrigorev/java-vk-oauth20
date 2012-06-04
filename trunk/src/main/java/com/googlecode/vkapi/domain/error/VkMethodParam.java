package com.googlecode.vkapi.domain.error;

import org.apache.commons.lang3.ObjectUtils;

/**
 * Param of method invoked which returned error
 * 
 * @author Alexey Grigorev
 */
public class VkMethodParam {

    private final String key;
    private final String value;

    public VkMethodParam(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return ObjectUtils.hashCodeMulti(key, value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof VkMethodParam) {
            VkMethodParam other = (VkMethodParam) obj;
            return ObjectUtils.equals(key, other.key) && ObjectUtils.equals(value, other.value);
        }
        return false;
    }

    @Override
    public String toString() {
        return "{" + key + ": " + value + "}";
    }

}
