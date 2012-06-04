package com.googlecode.vkapi.domain.message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Builder for constructing immutable {@link VkWallMessage} objects
 * 
 * @author Alexey Grigorev
 * @see VkWallMessage
 */
public class VkWallMessageBuilder {

    private final int messageId;

    private VkMessageSender sender;
    private VkMessageSender receiver;

    private long date;
    private String text;
    private VkMessageSender signer;

    private List<VkAttachment> attachments = new ArrayList<VkAttachment>();

    public static VkWallMessageBuilder message(int messageId) {
        return new VkWallMessageBuilder(messageId);
    }

    private VkWallMessageBuilder(int messageId) {
        this.messageId = messageId;
    }

    public int getMessageId() {
        return messageId;
    }

    public VkWallMessageBuilder addDate(long date) {
        this.date = date;
        return this;
    }

    public long getDate() {
        return date;
    }

    public VkWallMessageBuilder addSender(VkMessageSender sender) {
        this.sender = sender;
        return this;
    }

    public VkWallMessageBuilder addSender(int senderId) {
        return addSender(VkMessageSender.of(senderId));
    }

    public VkMessageSender getSender() {
        return sender;
    }

    public VkWallMessageBuilder addReceiver(VkMessageSender receiver) {
        this.receiver = receiver;
        return this;
    }

    public VkWallMessageBuilder addReceiver(int receiverId) {
        return addReceiver(VkMessageSender.of(receiverId));
    }

    public VkMessageSender getReceiver() {
        return receiver;
    }

    public VkWallMessageBuilder addText(String text) {
        this.text = text;
        return this;
    }

    public String getText() {
        return text;
    }

    public VkWallMessageBuilder addSigner(VkMessageSender signer) {
        this.signer = signer;
        return this;
    }

    public VkMessageSender getSigner() {
        return signer;
    }

    public VkWallMessageBuilder addAttachment(VkAttachment attachment) {
        attachments.add(attachment);
        return this;
    }

    public List<VkAttachment> getAttachments() {
        return Collections.unmodifiableList(attachments);
    }

    public VkWallMessage build() {
        return new VkWallMessage(this);
    }

}
