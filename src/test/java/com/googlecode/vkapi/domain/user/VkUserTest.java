package com.googlecode.vkapi.domain.user;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.common.testing.EqualsTester;
import com.googlecode.vkapi.domain.user.VkUser;

public class VkUserTest {

    @Test
    public void testEquals() {
        EqualsTester equalsTester = new EqualsTester();

        VkUser user1 = VkUserBuilder.user(10).addName("firstName", "lastName").build();
        VkUser user2 = VkUserBuilder.user(10).addName("firstName2", "lastName2").build();
        
        equalsTester.addEqualityGroup(user1, user2);
        
        VkUserBuilder builder = VkUserBuilder.user(100).addPhoto("photo").addBirthday("7.7.1999");
        equalsTester.addEqualityGroup(builder.build(), builder.build());
        
        equalsTester.addEqualityGroup(new Object());

        equalsTester.testEquals();
    }
    
    @Test
    public void withBirthday() {
        VkUser user = VkUserBuilder.user(100).addBirthday("7.7.1999").build();
        assertTrue(user.withBirthday());
    }
    
    @Test
    public void withoutBirthday() {
        VkUser user = VkUserBuilder.user(100).build();
        assertFalse(user.withBirthday());
    }
}
