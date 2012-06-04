package com.googlecode.vkapi;

import java.util.Collection;
import java.util.Set;

import com.googlecode.vkapi.domain.OAuthToken;
import com.googlecode.vkapi.domain.group.VkGroup;
import com.googlecode.vkapi.domain.message.VkWallMessage;
import com.googlecode.vkapi.domain.user.VkUser;
import com.googlecode.vkapi.exceptions.VkException;

/**
 * Api for interaction with public method of vk.com.<br>
 * <br>
 * 
 * Firstly, you will need to register an app on vk.com and obtain needed
 * application id, application secret key and set up an uri on which the
 * application will get codes with responce from vk.com.<br>
 * <br>
 * 
 * <b>Interaction:</b>
 * <ol>
 * 
 * <li>Application gets auth uri via {@link #getAuthUri()} and shows it in
 * popup;</li>
 * 
 * <li>In the popup user is asked to allow the application to proceed;</li>
 * 
 * <li>If user allows, vk.com sends a code to specified responseUri, and
 * application should pass it to {@link #authUser(String)} for obtaining
 * {@link OAuthToken}. If user denies to allow, vk.com doesn't send a code;</li>
 * 
 * <li>With gotten {@link OAuthToken} all other methods of this interface can be
 * invoked until the token becomes expired;</li>
 * 
 * <li>When token becomes expired, application should ask for new one using
 * first and second steps.</li>
 * 
 * </ol>
 * 
 * @author Alexey Grigorev
 * @see HttpVkApi
 */
public interface VkApi {

    /**
     * Auth url is url in vk.com on which user is asked for an access for our
     * application
     * 
     * @return auth url
     */
    String getAuthUri();

    /**
     * Authenticates user by given code. Should be handled when vk.com returns
     * code for further processing
     * 
     * @param code returned by vk.com
     * @return token for further interaction with vk.com
     * @throws VkException if something goes wrong
     */
    OAuthToken authUser(String code) throws VkException;

    /**
     * Retrieves from authToken the user code and gets the info about it
     * 
     * @param authToken for accessing vk.com
     * @return info about owner of authToken
     * @throws VkException if something goes wrong
     */
    VkUser currentUserInfo(OAuthToken authToken) throws VkException;

    /**
     * Gets friends of owner of given authToken
     * 
     * @param authToken for accessing vk.com
     * @return collection of user's fiends
     * @throws VkException if something goes wrong
     */
    Collection<VkUser> getFriends(OAuthToken authToken) throws VkException;

    /**
     * Gets friends of given user by its id
     * 
     * @param userId of the user
     * @param token for accessing vk.com
     * @return collection of user's fiends
     * @throws VkException if something goes wrong
     */
    Collection<VkUser> getFriends(int userId, OAuthToken token) throws VkException;

    /**
     * Gets last messages from given group by its id
     * 
     * @param groupId of the group
     * @param filter for filtering the result
     * @param authToken for accessing vk.com
     * @return collection of wall messages
     * @throws VkException if something goes wrong
     */
    Collection<VkWallMessage> lastGroupWallMessages(long groupId, WallFiler filter, OAuthToken authToken)
            throws VkException;

    /**
     * Gets last messages from given group by its id, limited
     * 
     * @param groupId of the group
     * @param filter for filtering the result
     * @param limit amount of messages, should be between 0 and 100. If 0 used,
     * then no limit is set
     * @param authToken for accessing vk.com
     * @return collection of messages from group's wall
     * @throws VkException if something goes wrong
     * @throws IllegalArgumentException if the limit falls out of the boundaries
     */
    Collection<VkWallMessage> lastGroupWallMessages(long groupId, WallFiler filter, int limit, OAuthToken authToken)
            throws VkException;

    /**
     * Gets ids of mutual friends of two users
     * 
     * @param user1Id first user
     * @param user2Id second user
     * @param authToken for accessing vk.com
     * @return set of mutual friends
     * @throws VkException if something goes wrong
     */
    Set<Integer> mutualFriends(int user1Id, int user2Id, OAuthToken authToken) throws VkException;

    /**
     * Extracts info about the group
     * 
     * @param groupId id of the group
     * @param authToken for accessing vk.com
     * @return group
     * @throws VkException if something goes wrong
     */
    VkGroup groupInfo(long groupId, OAuthToken authToken) throws VkException;

    /**
     * Extracts all users of the given group by its id
     * 
     * @param vkGroupId of the group
     * @param authToken for extraction
     * @return collection of users' ids
     * @throws VkException if something goes wrong
     */
    Set<Integer> groupUsers(long vkGroupId, OAuthToken authToken) throws VkException;

}
