package com.module.hrm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * A dedicated service for publishing messages to Redis channels.
 */
@Service
public class RedisPublisher {

    private static final Logger log = LoggerFactory.getLogger(RedisPublisher.class);

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisPublisher(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * Publishes a message to a specific Redis channel.
     *
     * @param channel The channel to publish to.
     * @param message The message to send (can be any object that is serializable to JSON).
     */
    public void publish(String channel, Object message) {
        log.info("Publishing message to channel '{}': {}", channel, message);
        redisTemplate.convertAndSend(channel, message);
    }
}
