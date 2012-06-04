package com.googlecode.vkapi.domain.user;

/**
 * Builder for {@link VkUser} class
 * 
 * @author Alexey Grigorev
 * @see VkUser
 */
public class VkUserBuilder {

    private final int vkUserId;

    private String firstName;
    private String lastName;
    private String photo;
    private String bdate;

    private VkUserBuilder(int vkUserId) {
        this.vkUserId = vkUserId;
    }

    public static VkUserBuilder user(int vkUserId) {
        return new VkUserBuilder(vkUserId);
    }

    public VkUserBuilder addName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        return this;
    }

    public VkUserBuilder addPhoto(String photo) {
        this.photo = photo;
        return this;
    }

    public VkUserBuilder addBirthday(String bdate) {
        this.bdate = bdate;
        return this;
    }

    public VkUser build() {
        return new VkUser(this);
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
}
