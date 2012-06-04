package com.googlecode.vkapi.domain.group;

import org.apache.commons.lang3.BooleanUtils;

/**
 * Builder for constructing immutable {@link VkGroup}
 * 
 * @author Alexey Grigorev
 */
public class VkGroupBuilder {

    private final long groupId;
    private String groupName;
    private String screenName;
    private boolean closed = false;
    private VkGroupType groupType = VkGroupType.GROUP;
    private String photo;
    private String photoMedium;
    private String photoBig;

    private VkGroupBuilder(long groupId) {
        this.groupId = groupId;
    }

    public static VkGroupBuilder group(long groupId) {
        return new VkGroupBuilder(groupId);
    }

    public VkGroupBuilder addGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public VkGroupBuilder addScreenName(String screenName) {
        this.screenName = screenName;
        return this;
    }

    public VkGroupBuilder setClosed(boolean closed) {
        this.closed = closed;
        return this;
    }
    
    public VkGroupBuilder setClosed(int closed) {
        return setClosed(BooleanUtils.toBoolean(closed));
    }

    public VkGroupBuilder setGroupType(VkGroupType groupType) {
        this.groupType = groupType;
        return this;
    }
    
    public VkGroupBuilder setGroupType(String groupType) {
        return setGroupType(VkGroupType.of(groupType));
    }

    public VkGroupBuilder addPhoto(String photo) {
        this.photo = photo;
        return this;
    }

    public VkGroupBuilder addPhotoMedium(String photoMedium) {
        this.photoMedium = photoMedium;
        return this;
    }

    public VkGroupBuilder addPhotoBig(String photoBig) {
        this.photoBig = photoBig;
        return this;
    }

    public long getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public String getScreenName() {
        return screenName;
    }

    public boolean isClosed() {
        return closed;
    }

    public VkGroupType getGroupType() {
        return groupType;
    }

    public String getPhoto() {
        return photo;
    }

    public String getPhotoMedium() {
        return photoMedium;
    }

    public String getPhotoBig() {
        return photoBig;
    }

    public VkGroup build() {
        return new VkGroup(this);
    }

}
