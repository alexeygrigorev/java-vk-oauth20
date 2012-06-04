package com.googlecode.vkapi.domain.user;

import org.apache.commons.lang3.StringUtils;

/**
 * VK.com user with all user related information. Immutable.
 * 
 * @author Alexey Grigorev
 */
public class VkUser {

    private final int vkUserId;
    private final String firstName;
    private final String lastName;
    private final String photo;
    private final String bdate;

    public VkUser(VkUserBuilder vkUserBuilder) {
        this.vkUserId = vkUserBuilder.getVkUserId();
        this.firstName = vkUserBuilder.getFirstName();
        this.lastName = vkUserBuilder.getLastName();
        this.photo = vkUserBuilder.getPhoto();
        this.bdate = vkUserBuilder.getBdate();
    }

    public int getVkUserId() {
        return vkUserId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoto() {
        return photo;
    }

    public String getBdate() {
        return bdate;
    }

    public boolean withBirthday() {
        return StringUtils.isNotBlank(bdate);
    }

    @Override
    public String toString() {
        return "VkUser [" + firstName + " " + lastName + ", id=" + vkUserId + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof VkUser) {
            VkUser another = (VkUser) obj;
            return vkUserId == another.vkUserId;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return vkUserId;
    }

}
