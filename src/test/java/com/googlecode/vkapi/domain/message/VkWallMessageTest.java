package com.googlecode.vkapi.domain.message;

import org.junit.Test;

import com.google.common.testing.EqualsTester;

public class VkWallMessageTest {

    @Test
    public void testEquals() {
        EqualsTester equalsTester = new EqualsTester();

        VkWallMessageBuilder builder1 = VkWallMessageBuilder.message(10).addText("text");
        equalsTester.addEqualityGroup(builder1.build(), builder1.build());

        VkWallMessageBuilder builder2 = VkWallMessageBuilder.message(11).addText("text");
        equalsTester.addEqualityGroup(builder2.build(), builder2.build());

        equalsTester.addEqualityGroup(new Object());

        equalsTester.testEquals();
    }

}
