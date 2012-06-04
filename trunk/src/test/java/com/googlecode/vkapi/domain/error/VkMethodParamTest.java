package com.googlecode.vkapi.domain.error;

import org.junit.Test;

import com.google.common.testing.EqualsTester;

public class VkMethodParamTest {

    @Test
    public void testEquals() {
        
        VkMethodParam param1 = new VkMethodParam("key1", "value1");
        VkMethodParam param2 = new VkMethodParam("key1", "value1");
        
        EqualsTester equalsTester = new EqualsTester();
        equalsTester.addEqualityGroup(param1, param2);
        
        VkMethodParam param3 = new VkMethodParam("key2", "value2");
        VkMethodParam param4 = new VkMethodParam("key2", "value2");
        equalsTester.addEqualityGroup(param3, param4);
        
        equalsTester.addEqualityGroup(new Object());
        
        equalsTester.testEquals();
    }

}
