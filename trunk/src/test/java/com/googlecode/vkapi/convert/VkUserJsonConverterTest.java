package com.googlecode.vkapi.convert;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.googlecode.vkapi.domain.user.VkUser;

public class VkUserJsonConverterTest {


    // sut
    JsonConverter jsonConverter = JsonConverter.INSTANCE;
    
    // test data
    private static final int userId = 150469440;
    private static final List<Integer> users = Arrays.asList(userId, 155583474, 163661323, 169873993);
  
    private static final String userListResponse = "{\"" + "response\":" + "[" +
		"{\"uid\":" + users.get(0) + ",\"first_name\":\"Ivan\",\"last_name\":\"Solovey\"," +
		    "\"photo\":\"http://cs316721.userapi.com/u150469440/e_430fdb09.jpg\",\"bdate\":\"7.5.1983\",\"online\":1}," +
		"{\"uid\":" + users.get(1) + ",\"first_name\":\"Максим\",\"last_name\":\"Карпатов\"," +
		    "\"photo\":\"http://cs9896.userapi.com/u155583474/e_dec7f343.jpg\",\"bdate\":\"6.5.1987\",\"online\":0}," +
		"{\"uid\":" + users.get(2) + ",\"first_name\":\"Михаил\",\"last_name\":\"Лаймов\"," +
		    "\"photo\":\"http://cs5995.userapi.com/u163661323/e_c1e2d955.jpg\",\"bdate\":\"8.5.1992\",\"online\":0}," +
		"{\"uid\":" + users.get(3) + ",\"first_name\":\"Tele\",\"last_name\":\"Test\"," +
		    "\"photo\":\"http://cs302214.userapi.com/u169873993/e_f3152e1c.jpg\",\"bdate\":\"3.5.1987\",\"online\":0}" +
	"]}";
    
    private static final String name = "Ivan";
    private static final String lastName = "Solovey";
    private static final String photo = "http://cs316721.userapi.com/u150469440/e_430fdb09.jpg";
    private static final String bdate = "7.5.1983";
    
    private static final String oneUserResponse = "{\"" + "response\":" + "[" +
        "{\"uid\":" + userId + ",\"first_name\":\"" + name + "\",\"last_name\":\"" + lastName + "\"," +
            "\"photo\":\"" + photo + "\",\"bdate\":\"" + bdate + "\",\"online\":1}" +
    "]}";
    
    @Test
    public void jsonToVkGroups() {
        List<VkUser> result = jsonConverter.jsonToUserList(userListResponse);
        
        Set<Integer> excepted = extractExpectedIds(); 
        Set<Integer> actual = extractActualIds(result);
        
        assertEquals(excepted, actual);
    }

    private static Set<Integer> extractActualIds(List<VkUser> result) {
        Set<Integer> actual = new LinkedHashSet<Integer>(); 
        for (VkUser user : result) {
            actual.add(user.getVkUserId());
        }
        return actual;
    }

    private static Set<Integer> extractExpectedIds() {
        return new LinkedHashSet<Integer>(users);
    }
    
    
    @Test
    public void jsonToVkGroupsOneUser() {
        List<VkUser> result = jsonConverter.jsonToUserList(oneUserResponse);
        VkUser vkUser = result.get(0);
        
        assertEquals(name, vkUser.getFirstName());
        assertEquals(lastName, vkUser.getLastName());
        assertEquals(photo, vkUser.getPhoto());
        assertEquals(bdate, vkUser.getBdate());
    }
}
