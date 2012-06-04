package com.googlecode.vkapi.convert;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.List;

import org.junit.Test;

import com.googlecode.vkapi.domain.message.VkAttachment;
import com.googlecode.vkapi.domain.message.VkLinkAttachment;
import com.googlecode.vkapi.domain.message.VkWallMessage;

public class WallMessageJsonConverterTest {
    
    // sut
    JsonConverter jsonConverter = JsonConverter.INSTANCE;
    
    // data
    static final int messageId = 157;
    static final int senderId = -34243323;
    static final int receiverId = -34243323;
    static final long date = 1333369708L;
    
    static final String text = "DJ Smash feat. Maury - Rendez Vous <br><br>" +
    		"http://gudok.tele2.ru/melody/7274";
    
    static final int signerId = 1987307;
    
    static final String attachmentLink = "http://gudok.tele2.ru/melody/7274";
    static final String attachmentTitle = "Maury - Rendez-Vous";
    
    static final String wallMessage = 
        "{\"response\":" +
            "[39," +
                "{\"id\":" + messageId + "," +
                "\"from_id\":" + senderId + "," +
                "\"to_id\":" + receiverId + "," +
                "\"date\":" + date + "," +
                "\"text\":\"" + text + "\"," +
                "\"signer_id\":" + signerId + "," +
                "\"media\":" +
                    "{\"type\":\"share\",\"share_url\":\"http://gudok.tele2.ru/melody/7274\",\"share_title\":\"Maury - Rendez-Vous\"}," +
                "\"attachment\":" +
                    "{\"type\":\"link\",\"link\":{\"url\":\"http://gudok.tele2.ru/melody/7274\",\"title\":\"" + attachmentTitle + "\",\"description\":\"avs;\",\"image_src\":\"http://cs6073.userapi.com/u1987307/-2/x_a692dff6.jpg\"}}," +
                "\"attachments\":" +
                    "[" +
                        "{\"type\":\"link\",\"link\":{\"url\":\"" + attachmentLink + "\",\"title\":\"Maury - Rendez-Vous\",\"description\":\"avs;\",\"image_src\":\"http://cs6073.userapi.com/u1987307/-2/x_a692dff6.jpg\"}}" +
                    "]," +
                "\"comments\":" +
                    "{\"count\":0,\"can_post\":1}," +
                "\"likes\":" +
                    "{\"count\":0,\"user_likes\":0,\"can_like\":1,\"can_publish\":1}," +
                "\"reposts\":" +
                    "{\"count\":0,\"user_reposted\":0}," +
                "\"post_source\":" +
                    "{\"type\":\"vk\"}," +
                "\"online\":0," +
                "\"reply_count\":0}" +
            "]" +
        "}";

    
    @Test
    public void jsonToWallMessage() {
        Collection<VkWallMessage> result = jsonConverter.jsonToWallMessage(wallMessage);
        VkWallMessage message = result.iterator().next();
        
        assertEquals(messageId, message.getMessageId());
        assertEquals(senderId, message.getSender().getId());
        assertEquals(receiverId, message.getReceiver().getId());
        
        assertEquals(date, message.getDate());
        
        List<VkAttachment> attachments = message.getAttachments();
        VkLinkAttachment attachment = (VkLinkAttachment) attachments.get(0);
        
        assertEquals(attachmentTitle, attachment.getTitle());
        assertEquals(attachmentLink, attachment.getUrl());
    }

}
