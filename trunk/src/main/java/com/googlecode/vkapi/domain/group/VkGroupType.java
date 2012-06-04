package com.googlecode.vkapi.domain.group;

/**
 * Type of groups in vk.com
 * 
 * @author Alexey Grigorev
 */
public enum VkGroupType {

    GROUP,

    PAGE,

    EVENT;

    public static VkGroupType of(String name) {
        return valueOf(name.toUpperCase());
    }

}
