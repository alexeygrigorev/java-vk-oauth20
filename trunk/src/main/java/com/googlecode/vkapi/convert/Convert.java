package com.googlecode.vkapi.convert;

import java.util.Iterator;

import org.apache.commons.lang3.Validate;
import org.codehaus.jackson.JsonNode;

import com.googlecode.vkapi.domain.VkOAuthToken;
import com.googlecode.vkapi.domain.error.VkMethodParam;
import com.googlecode.vkapi.domain.group.VkGroup;
import com.googlecode.vkapi.domain.group.VkGroupBuilder;
import com.googlecode.vkapi.domain.message.*;
import com.googlecode.vkapi.domain.user.VkUser;
import com.googlecode.vkapi.domain.user.VkUserBuilder;

/**
 * Utility class for converting from {@link JsonNode} instances to vk.com domain
 * objects
 * 
 * @author Alexey Grigorev
 */
final class Convert {

    private Convert() {
    }

    public static VkOAuthToken toAuthToken(JsonNode node) {
        String accessToken = node.get("access_token").getValueAsText();
        int expiresIn = node.get("expires_in").getValueAsInt();
        int userId = node.get("user_id").getValueAsInt();
        return new VkOAuthToken(accessToken, expiresIn, userId);
    }

    public static VkUser toVkUser(JsonNode node) {
        int vkUserId = node.get("uid").getValueAsInt();
        String firstName = node.get("first_name").getValueAsText();
        String lastName = node.get("last_name").getValueAsText();
        String photo = node.get("photo").getValueAsText();

        VkUserBuilder builder = VkUserBuilder.user(vkUserId).addName(firstName, lastName);
        builder.addPhoto(photo);

        JsonNode bdate = node.get("bdate");
        if (bdate != null) {
            // date format: "7.7.1987"
            builder.addBirthday(bdate.getValueAsText());
        }

        return builder.build();
    }

    public static VkWallMessage toVkWallMessage(JsonNode node) {
        int messageId = node.get("id").getValueAsInt();
        VkWallMessageBuilder builder = VkWallMessageBuilder.message(messageId);

        int senderId = node.get("from_id").getValueAsInt();
        builder.addSender(senderId);
        int receiverId = node.get("to_id").getValueAsInt();
        builder.addReceiver(receiverId);
        builder.addDate(node.get("date").getLongValue());
        builder.addText(node.get("text").getValueAsText());

        JsonNode signerNode = node.get("signer_id");
        if (signerNode != null) {
            builder.addSigner(VkMessageSender.of(signerNode.getIntValue()));
        }

        addWallMessageAttachments(builder, node);

        return builder.build();
    }

    private static void addWallMessageAttachments(VkWallMessageBuilder builder, JsonNode node) {
        JsonNode attachmentsNode = node.get("attachments");
        if (attachmentsNode == null) {
            return;
        }

        Validate.isTrue(attachmentsNode.isArray(), "attachments is expected to be an array, got %s", attachmentsNode);
        Iterator<JsonNode> nodes = attachmentsNode.iterator();

        while (nodes.hasNext()) {
            JsonNode attachmentNode = nodes.next();
            VkAttachment attachment = toAttachment(attachmentNode);
            // TODO: get rig of null
            if (attachment != null) {
                builder.addAttachment(attachment);
            }
        }
    }

    private static VkAttachment toAttachment(JsonNode attachmentNode) {
        String typeString = attachmentNode.get("type").getValueAsText();
        VkAttachmentType type = VkAttachmentType.valueOf(typeString.toUpperCase());

        switch (type) {
            case LINK:
                return linkAttachment(attachmentNode);

        }
        // TODO: get rig of null
        return null;
    }

    private static VkLinkAttachment linkAttachment(JsonNode attachmentNode) {
        JsonNode linkNode = attachmentNode.get("link");

        String title = linkNode.get("title").getValueAsText();
        String url = linkNode.get("url").getValueAsText();

        return new VkLinkAttachment(title, url);
    }

    public static VkGroup toVkGroup(JsonNode node) {
        int gid = node.get("gid").getValueAsInt();
        VkGroupBuilder builder = VkGroupBuilder.group(gid);

        JsonNode name = node.get("name");
        if (name != null) {
            builder.addGroupName(name.getValueAsText());
        }

        JsonNode screenName = node.get("screen_name");
        if (screenName != null) {
            builder.addScreenName(screenName.getValueAsText());
        }

        JsonNode isClosed = node.get("is_closed");
        if (isClosed != null) {
            builder.setClosed(isClosed.getValueAsInt());
        }

        JsonNode type = node.get("type");
        if (type != null) {
            builder.setGroupType(type.getValueAsText());
        }

        JsonNode photo = node.get("photo");
        if (photo != null) {
            builder.addPhoto(photo.getValueAsText());
        }

        JsonNode photoMedium = node.get("photo_medium");
        if (photoMedium != null) {
            builder.addPhotoMedium(photoMedium.getValueAsText());
        }

        JsonNode photoBig = node.get("photo_big");
        if (photoBig != null) {
            builder.addPhotoBig(photoBig.getValueAsText());
        }

        return builder.build();
    }

    public static VkMethodParam toVkMethodParam(JsonNode node) {
        return new VkMethodParam(node.get("key").getValueAsText(), node.get("value").getValueAsText());
    }

}
