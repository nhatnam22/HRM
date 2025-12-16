package com.module.hrm.service;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * An interface representing a subscriber for a specific Redis channel.
 * Any module wanting to listen to a Redis channel must implement this interface.
 */
public interface RedisChannelSubscriber {
    /**
     * The specific channel name this subscriber listens to.
     * e.g., "hrm:user-notifications", "inventory:stock-updates"
     * @return The channel name.
     */
    String getChannelToListen();

    /**
     * The logic to execute when a message is received on the channel.
     * @param message The raw message body as a String.
     */
    void handleMessage(String message);

    /**
     * Provides a default ObjectMapper for deserialization.
     * Can be overridden if custom ObjectMapper configuration is needed.
     * @return A Jackson ObjectMapper.
     */
    default ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }
}
