package com.googlecode.vkapi.convert;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;

import com.googlecode.vkapi.domain.error.VkErrorResponse;
import com.googlecode.vkapi.domain.error.VkMethodParam;

public class VkErrorConverterTest {

    // sut
    JsonConverter jsonConverter = JsonConverter.INSTANCE;
    
    final int errorCode = 5;
    final String errorMessage = "User authorization failed: access_token have heen expired.";
    
    final String json = "{\"error\":" +"{" +
		"\"error_code\":" + errorCode + "," +
		"\"error_msg\":\"" + errorMessage + "\"," +
		"\"request_params\":" +
    		"[" +
        		"{\"key\":\"oauth\",\"value\":\"1\"}," +
        		"{\"key\":\"method\",\"value\":\"groups.getById\"}," +
        		"{\"key\":\"gid\",\"value\":\"34243323\"}," +
        		"{\"key\":\"access_token\",\"value\":\"75b3e2cc32dcc69c7d44198c567d68494b77d447d44198eae0396b8696be5d4\"}" +
    		"]" +
	"}" + "}";
    
    @Test 
    public void jsonToVkError() {
        VkErrorResponse vkError = jsonConverter.jsonToVkError(json);
        assertEquals(errorCode, vkError.getErrorCode());
        assertEquals(errorMessage, vkError.getMessage());
    }
    
    @Test 
    public void jsonToVkErrorParams() {
        VkErrorResponse vkError = jsonConverter.jsonToVkError(json);
        Collection<VkMethodParam> params = vkError.getParams();
        assertContainsParams(params);
    }

    private static void assertContainsParams(Collection<VkMethodParam> params) {
        VkMethodParam oauth = new VkMethodParam("oauth", "1");
        assertTrue(params.contains(oauth));

        VkMethodParam method = new VkMethodParam("method", "groups.getById");
        assertTrue(params.contains(method));
        
        VkMethodParam gid = new VkMethodParam("gid", "34243323");
        assertTrue(params.contains(gid));
        
        VkMethodParam accessToken = new VkMethodParam("access_token",
                "75b3e2cc32dcc69c7d44198c567d68494b77d447d44198eae0396b8696be5d4");
        assertTrue(params.contains(accessToken));
    }
}
