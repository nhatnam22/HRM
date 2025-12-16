package com.module.hrm.service;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

/**
 * A central, delegating message listener.
 * It receives all messages from the container and dispatches them to the appropriate
 * RedisChannelSubscriber implementation based on the channel name.
 */
public class DelegatingRedisMessageSubscriber implements MessageListener {

    private static final Logger log = LoggerFactory.getLogger(DelegatingRedisMessageSubscriber.class);

    private final List<RedisChannelSubscriber> subscribers;
    private Map<String, RedisChannelSubscriber> subscriberMap;

    public DelegatingRedisMessageSubscriber(List<RedisChannelSubscriber> subscribers) {
        this.subscribers = subscribers;
    }

    /**
     * After construction, create a map for quick lookup of subscribers by channel name.
     */
    @PostConstruct
    public void init() {
        this.subscriberMap = subscribers
            .stream()
            .collect(Collectors.toMap(RedisChannelSubscriber::getChannelToListen, Function.identity()));
        log.info("Initialized Redis subscribers for channels: {}", subscriberMap.keySet());
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = new String(message.getChannel());
        RedisChannelSubscriber subscriber = subscriberMap.get(channel);

        if (subscriber != null) {
            String messageBody = new String(message.getBody());
            try {
                subscriber.handleMessage(messageBody);
            } catch (Exception e) {
                log.error("Error handling message from channel '{}'", channel, e);
            }
        } else {
            log.warn("No subscriber found for channel '{}'", channel);
        }
    }
}
