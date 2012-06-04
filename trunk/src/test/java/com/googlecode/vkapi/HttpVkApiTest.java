package com.googlecode.vkapi;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import testutils.TestUtils;

import com.googlecode.vkapi.convert.JsonConverter;
import com.googlecode.vkapi.domain.VkOAuthToken;
import com.googlecode.vkapi.domain.error.VkErrorResponse;
import com.googlecode.vkapi.domain.user.GroupUsers;
import com.googlecode.vkapi.exceptions.VkException;

public class HttpVkApiTest {

    // sut
    HttpVkApi vkApi;

    @Mock HttpClientWrapper httpClient;
    @Mock JsonConverter jsonConverter;
    @Spy UriCreator uriCreator = new UriCreator();

    // data
    String appId = "2904263";
    String appKey = "wSaMbeVyZhdgzzOAQT9l";
    String responseUri = "http://tele2russia.9123.ru/private/bind";

    private VkOAuthToken authToken;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        vkApi = new HttpVkApi(appId, appKey, responseUri);
        vkApi.setHttpClient(httpClient);
        vkApi.setJsonConverter(jsonConverter);
        vkApi.setUriCreator(uriCreator);
        
        authToken = new VkOAuthToken(RandomStringUtils.randomAlphanumeric(10), 1002, 1023);
    }

    @Test
    public void authUser() throws VkException {
        String code = RandomStringUtils.randomAlphanumeric(10);

        vkApi.authUser(code);

        verify(uriCreator).accessTokenUri(appId, appKey, code);
        verify(httpClient).executeGet(anyString());
        verify(jsonConverter).jsonToAuthToken(anyString());
    }

    @Test
    public void currentUserInfo() throws Exception {
        vkApi.currentUserInfo(authToken);

        verify(uriCreator).userInfoUri(authToken.getUserId(), HttpVkApi.USER_FIELDS, authToken);
        verify(httpClient).executeGet(anyString());
        verify(jsonConverter).jsonToUserList(anyString());
    }

    @Test
    public void getAuthUri() throws Exception {
        vkApi.getAuthUri();
        verify(uriCreator).authUri(appId, HttpVkApi.APP_SCOPES, responseUri);
    }

    @Test
    public void getFriends() throws Exception {
        vkApi.getFriends(authToken);

        verify(uriCreator).userFriendsUri(authToken.getUserId(), HttpVkApi.USER_FIELDS, authToken);
        verify(httpClient).executeGet(anyString());
        verify(jsonConverter).jsonToUserList(anyString());
    }
    
    @Test(expected = VkException.class)
    public void getFriendsWithExpiredToken() throws Exception {
        givenVkErrorInResponse();
        vkApi.getFriends(authToken);
    }

    private void givenVkErrorInResponse() throws VkException {
        String errorResponseString = "{\"error\": someError}";
        when(httpClient.executeGet(anyString())).thenReturn(errorResponseString);
        VkErrorResponse value = new VkErrorResponse(10, "message");
        when(jsonConverter.jsonToVkError(errorResponseString)).thenReturn(value);
    }

    @Test
    public void getFriendsWithUserId() throws Exception {
        int userId = 1024;
        vkApi.getFriends(userId, authToken);

        verify(uriCreator).userFriendsUri(userId, HttpVkApi.USER_FIELDS, authToken);
        verify(httpClient).executeGet(anyString());
        verify(jsonConverter).jsonToUserList(anyString());
    }

    @Test
    public void groupInfo() throws Exception {
        long groupId = 1034;
        vkApi.groupInfo(groupId, authToken);

        verify(uriCreator).groupInfo(groupId, authToken);
        verify(httpClient).executeGet(anyString());
        verify(jsonConverter).jsonToVkGroups(anyString());
    }

    @Test
    public void lastGroupWallMessages() throws Exception {
        WallFiler filter = WallFiler.ALL;
        long groupId = 1034;
        int noLimit = 0;

        vkApi.lastGroupWallMessages(groupId, filter, authToken);

        verify(uriCreator).groupWallMessages(groupId, filter, noLimit, authToken);
        verify(httpClient).executeGet(anyString());
        verify(jsonConverter).jsonToWallMessage(anyString());
    }
    
    @Test
    public void lastGroupWallMessagesWithLimit() throws Exception {
        WallFiler filter = WallFiler.ALL;
        long groupId = 1034;
        int limit = 10;

        vkApi.lastGroupWallMessages(groupId, filter, limit, authToken);

        verify(uriCreator).groupWallMessages(groupId, filter, limit, authToken);
        verify(httpClient).executeGet(anyString());
        verify(jsonConverter).jsonToWallMessage(anyString());
    }

    @Test
    public void mutualFriends() throws Exception {
        int user1Id = 12, user2Id = 13;
        
        vkApi.mutualFriends(user1Id, user2Id, authToken);
        
        verify(uriCreator).mutualFriends(user1Id, user2Id, authToken);
        verify(httpClient).executeGet(anyString());
        verify(jsonConverter).jsonToIntegerSet(anyString());
    }
    
    @Test
    public void groupUsersOneQuery() throws VkException {
        long groupId = 1230L;
        
        givenLessThen1000Users();
        
        vkApi.groupUsers(groupId, authToken);
        
        verify(uriCreator).groupUsers(groupId, 1000, 0, authToken);
        verify(httpClient).executeGet(anyString());
        verify(jsonConverter).jsonToGroupUsers(anyString());
    }

    private void givenLessThen1000Users() {
        int totalCount = 999;
        Set<Integer> users = TestUtils.setRange(0, totalCount);
        GroupUsers response = new GroupUsers(totalCount, users);
        when(jsonConverter.jsonToGroupUsers(anyString())).thenReturn(response);
    }
    
    @Test
    public void groupUsersTwoQuery() throws VkException {
        long groupId = 1230L;
        
        givenGreaterThen1000Users();
        
        vkApi.groupUsers(groupId, authToken);
        
        verify(uriCreator).groupUsers(groupId, 1000, 0, authToken);
        verify(uriCreator).groupUsers(groupId, 1000, 1000, authToken);
    }

    private void givenGreaterThen1000Users() {
        int totalCount = 1200;
        
        Set<Integer> users1 = TestUtils.setRange(0, 1001);
        GroupUsers response1 = new GroupUsers(totalCount, users1);
        
        Set<Integer> users2 = TestUtils.setRange(1001, 1200);
        GroupUsers response2 = new GroupUsers(totalCount, users2);
        
        when(jsonConverter.jsonToGroupUsers(anyString())).thenReturn(response1, response2);
    }
    
    @Test
    public void groupUsersTwoQueryJoinedCorrectly() throws VkException {
        long groupId = 1230L;
        
        givenGreaterThen1000Users();
        
        Set<Integer> actual = vkApi.groupUsers(groupId, authToken);
        Set<Integer> expected = TestUtils.setRange(0, 1200);
        
        assertEquals(expected, actual);
    }
}
