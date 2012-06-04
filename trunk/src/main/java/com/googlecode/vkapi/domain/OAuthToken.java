package com.googlecode.vkapi.domain;

import java.util.Date;

/**
 * Authentication token for interaction with VK.com
 * 
 * @author Alexey Grigorev
 */
public interface OAuthToken {

    /**
     * @return accessCode needed for performing http queries to vk.cpm
     */
    String getAccessToken();

    /**
     * @return id of user obtained this token
     */
    int getUserId();

    /**
     * @return <code>true</code> if this token is expired
     */
    boolean isExpired();

    /**
     * @return date when token is going to be expired
     */
    Date getExpirationMoment();

}