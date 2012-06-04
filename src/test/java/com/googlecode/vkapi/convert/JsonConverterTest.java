package com.googlecode.vkapi.convert;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.googlecode.vkapi.domain.user.GroupUsers;

public class JsonConverterTest {

    // sut
    JsonConverter jsonConverter = JsonConverter.INSTANCE;

    // test data
    static final List<Integer> intsList = Arrays.asList(55568, 86409);
    static final String ints = StringUtils.join(intsList, ",");
    static final String intResponse = "{\"response\":[" + ints + "]}";

    static final int count = 15123;
    static final String groupResponse = "{\"response\":{\"count\":" + count + ",\"users\":[" + ints + "]}}";
    
    @Test
    public void jsonToIntegerList() {
        Collection<Integer> result = jsonConverter.jsonToIntegerSet(intResponse);
        assertTrue(intsList.containsAll(result));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void toJsonNodeUnparsable() {
        jsonConverter.jsonToIntegerSet("{{{");
    }
    
    @Test
    public void jsonToGroupUsers() {
        GroupUsers result = jsonConverter.jsonToGroupUsers(groupResponse);
        assertEquals(count, result.getTotalCount());
        assertTrue(intsList.containsAll(result.getUsers()));
    }
}
