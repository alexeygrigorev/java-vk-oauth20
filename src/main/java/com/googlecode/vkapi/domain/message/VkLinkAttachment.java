package com.googlecode.vkapi.domain.message;

/**
 * Attachment with link
 * 
 * @author Alexey Grigorev
 * 
 */
public class VkLinkAttachment extends VkAttachment {

    private final String title;
    private final String url;

    public VkLinkAttachment(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "VkLinkAttachment [title=" + title + ", url=" + url + "]";
    }

}
