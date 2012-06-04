package com.googlecode.vkapi;

import org.apache.commons.lang3.StringUtils;

import com.googlecode.vkapi.domain.OAuthToken;

/**
 * Creates urls for accessing VK.com api fuctions
 * 
 * @author Alexey Grigorev
 */
class UriCreator {

    private static final String METHOD_URI = "https://api.vk.com/method/";

    /**
     * Generates uri for sending authorization requests
     * 
     * @param appId id of the application
     * @param scopes needed functions (such as "friends", "messages", etc)
     * @param responseUri the uri to which the response will be sent
     * @return uri to be shown to the user to authorization
     */
    public String authUri(String appId, String[] scopes, String responseUri) {
        return "http://oauth.vk.com/authorize?" + 
                "client_id=" + appId + "&" + 
                "scope=" + StringUtils.join(scopes, ",") + "&" + 
                "redirect_uri=" + responseUri + "&" + 
                "response_type=code";
    }

    public String accessTokenUri(String appId, String appKey, String code) {
        return "https://oauth.vk.com/access_token?client_id=" + appId + "&client_secret=" + appKey + "&code=" + code;
    }

    public String userInfoUri(int vkUserId, String[] fields, OAuthToken authToken) {
        return METHOD_URI + "users.get?" + 
                "uids=" + vkUserId + "&" + 
                "fields=" + StringUtils.join(fields, ",") + "&" + 
                "access_token=" + authToken.getAccessToken();
    }

    public String userFriendsUri(int vkUserId, String[] fields, OAuthToken authToken) {
        return METHOD_URI + "friends.get?" + 
                "uid=" + vkUserId + "&" + 
                "fields=" + StringUtils.join(fields, ",") + "&" + 
                "access_token=" + authToken.getAccessToken();
    }

    public String groupWallMessages(long groupId, WallFiler filter, int limit, OAuthToken authToken) {
        // minus indicates that the id belongs to a group
        return wallMessages(-groupId, filter, limit, authToken);
    }
    
    public String wallMessages(long userId, WallFiler filter, int limit, OAuthToken authToken) {
        StringBuilder builder = new StringBuilder(32);
        builder.append(METHOD_URI);
        builder.append("wall.get?");
        builder.append("owner_id=").append(userId).append("&");
        if (limit > 0) {
            builder.append("count=").append(limit);
        }
        builder.append("filter=").append(filter.filterName()).append("&");
        builder.append("access_token=").append(authToken.getAccessToken());
        return builder.toString();
    }

    public String mutualFriends(int user1Id, int user2Id, OAuthToken authToken) {
        return METHOD_URI + "friends.getMutual?" + 
                "target_uid=" + user2Id + "&" +
                "source_uid" + user1Id + "&" +
                "access_token=" + authToken.getAccessToken();
    }

    public String groupInfo(long groupId, OAuthToken authToken) {
        return METHOD_URI + "groups.getById?" + 
                "gid=" + groupId + "&" + 
                "access_token=" + authToken.getAccessToken();
    }

    public String groupUsers(long groupId, int count, int offset, OAuthToken authToken) {
        return METHOD_URI + "groups.getMembers?" + 
                "gid=" + groupId + "&" + 
                "count=" + count + "&" +
                "offset=" + offset + "&" + 
                "access_token=" + authToken.getAccessToken();
    }

}
