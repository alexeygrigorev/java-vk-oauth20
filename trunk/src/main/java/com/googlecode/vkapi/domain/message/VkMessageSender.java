package com.googlecode.vkapi.domain.message;

/**
 * Sender of the message - it may be either group or user
 * 
 * @author Alexey Grigorev
 */
public class VkMessageSender {

    private final int id;
    private final VkSenderType senderType;

    private VkMessageSender(int id, VkSenderType senderType) {
        this.id = id;
        this.senderType = senderType;
    }

    public static VkMessageSender of(int id) {
        if (id < 0) {
            return new VkMessageSender(id, VkSenderType.GROUP);
        } else {
            return new VkMessageSender(id, VkSenderType.USER);
        }
    }

    public int getId() {
        return id;
    }

    public VkSenderType getSenderType() {
        return senderType;
    }

    @Override
    public String toString() {
        return "VkMessageSender [id=" + id + ", senderType=" + senderType + "]";
    }

}
