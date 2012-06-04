package com.googlecode.vkapi.domain.message;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * Wall message from vk.com
 * 
 * @author Alexey Grigorev
 */
public class VkWallMessage {

    private final int messageId;
    private final long date;
    private final VkMessageSender sender;
    private final VkMessageSender receiver;
    private final String text;
    private final List<VkAttachment> attachments;

    public VkWallMessage(VkWallMessageBuilder builder) {
        messageId = builder.getMessageId();
        date = builder.getDate();

        sender = builder.getSender();
        receiver = builder.getReceiver();

        text = builder.getText();
        attachments = builder.getAttachments();
    }

    public int getMessageId() {
        return messageId;
    }

    public String getDateFormatted(String pattern) {
        return DateFormatUtils.format(date, pattern);
    }

    public long getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public List<VkAttachment> getAttachments() {
        return attachments;
    }

    public VkMessageSender getSender() {
        return sender;
    }

    public VkMessageSender getReceiver() {
        return receiver;
    }

    @Override
    public int hashCode() {
        return messageId;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof VkWallMessage && messageId == ((VkWallMessage) obj).messageId;
    }

    @Override
    public String toString() {
        return "VkWallMessage [messageId=" + messageId + ", date=" + date + ", sender=" + sender + ", receiver="
                + receiver + ", text=" + StringUtils.abbreviate(text, 50) + ", attachments=" + attachments + "]";
    }

}
