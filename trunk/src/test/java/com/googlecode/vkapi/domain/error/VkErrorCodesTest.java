package com.googlecode.vkapi.domain.error;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class VkErrorCodesTest {

    @Test
    public void EXPIRED_AUTH_TOKEN_codeIs5() {
        assertEquals(5, VkErrorCode.EXPIRED_AUTH_TOKEN.getErrorCode());
    }

    @Test
    public void ofEXPIRED_AUTH_TOKEN() {
        assertEquals(VkErrorCode.EXPIRED_AUTH_TOKEN, VkErrorCode.of(5));
    }

    @Test
    public void ofUnknown() {
        assertEquals(VkErrorCode.UNKNOWN, VkErrorCode.of(10));
    }

}
