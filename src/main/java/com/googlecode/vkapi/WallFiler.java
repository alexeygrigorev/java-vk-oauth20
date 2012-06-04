package com.googlecode.vkapi;

/**
 * Filter applied to retrieving of messages from wall
 * 
 * @author Alexey Grigorev
 * 
 */
public enum WallFiler {
    /**
     * Return only messages posted by the owner
     */
    OWNER, 
    
    /**
     * Return messages posted by all but the owner
     */
    OTHERS, 
    
    /**
     * Return all messages
     */
    ALL;

    public String filterName() {
        return name().toLowerCase();
    }
}