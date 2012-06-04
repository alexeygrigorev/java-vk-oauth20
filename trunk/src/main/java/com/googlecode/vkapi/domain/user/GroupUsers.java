package com.googlecode.vkapi.domain.user;

import java.util.Set;

import com.googlecode.vkapi.HttpVkApi;

/**
 * This class contains answer from vk with users of a group. Vk.com does not
 * provide all the users per one query, instead, it returns the list by
 * 1000-element pieces. Used in
 * {@link HttpVkApi#groupUsers(long, com.googlecode.vkapi.domain.OAuthToken)}
 * 
 * @author Alexey Grigorev
 * 
 */
public class GroupUsers {

    private final int totalCount;
    private final Set<Integer> users;

    public GroupUsers(int totalCount, Set<Integer> users) {
        this.totalCount = totalCount;
        this.users = users;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public Set<Integer> getUsers() {
        return users;
    }

    /**
     * @return <code>true</code> if all users are extracted per one current
     * query
     */
    public boolean allExtracted() {
        return totalCount == users.size();
    }

}
