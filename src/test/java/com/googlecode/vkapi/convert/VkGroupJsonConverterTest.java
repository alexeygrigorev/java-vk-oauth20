package com.googlecode.vkapi.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import com.googlecode.vkapi.domain.group.VkGroup;
import com.googlecode.vkapi.domain.group.VkGroupType;

public class VkGroupJsonConverterTest {

    // sut
    JsonConverter jsonConverter = JsonConverter.INSTANCE;
    
    // test data
    final String groupName = "Tele2 Гудок";
    final String screenName = "tele2gudok";
    final String isClosed = "0";
    final String groupType = "page";
    final String photo = "http://cs5604.userapi.com/g34243323/e_a8f6a321.jpg";
    final String photoMedium = "http://cs5604.userapi.com/g34243323/d_236937ba.jpg";
    final String photoBig = "http://cs5604.userapi.com/g34243323/a_a1002d1f.jpg";
    
    final int groupId = 34243323;
    
    final String groupResponse = "{" + "\"response\":" +
        "[" +
            "{" +
                "\"gid\":" + groupId + "," +
                "\"name\":\"" + groupName + "\"," +
                "\"screen_name\":\"" + screenName + "\"," +
                "\"is_closed\":" + isClosed + "," +
                "\"type\":\"" + groupType + "\"," +
                "\"photo\":\"" + photo + "\"," +
                "\"photo_medium\":\"" + photoMedium + "\"," +
                "\"photo_big\":\"" + photoBig + "\"" +
            "}" +
        "]" +
    "}";

    @Test
    public void jsonToVkGroups() {
        VkGroup result = jsonConverter.jsonToVkGroups(groupResponse).get(0);
        
        assertGroupConvertedProperly(result);
    }

    private void assertGroupConvertedProperly(VkGroup result) {
        assertEquals(groupId, result.getGroupId());
        assertEquals(groupName, result.getGroupName());
        assertEquals(screenName, result.getScreenName());
        assertFalse(result.isClosed());
        assertEquals(VkGroupType.PAGE, result.getGroupType());
        assertEquals(photo, result.getPhoto());
        assertEquals(photoMedium, result.getPhotoMedium());
        assertEquals(photoBig, result.getPhotoBig());
    }

}
