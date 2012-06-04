package com.googlecode.vkapi.domain.group;

import org.junit.Test;

import com.google.common.testing.EqualsTester;

public class VkGroupTest {

    @Test
    public void testEquals() {
        VkGroup group1 = VkGroupBuilder.group(10).addGroupName("groupName1").build();
        VkGroup group2 = VkGroupBuilder.group(10).addGroupName("groupName2").build();
        
        EqualsTester equalsTester = new EqualsTester();
        equalsTester.addEqualityGroup(group1, group2);
        
        VkGroupBuilder builder = VkGroupBuilder.group(200);
        equalsTester.addEqualityGroup(builder.build(), builder.build());

        equalsTester.addEqualityGroup(new Object());
        
        equalsTester.testEquals();
    }

}
