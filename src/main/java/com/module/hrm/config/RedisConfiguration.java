package com.module.hrm.config;

import com.module.hrm.service.DelegatingRedisMessageSubscriber;
import com.module.hrm.service.RedisChannelSubscriber;
import com.module.hrm.service.RedisPublisher;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Standard Redis configuration.
 * <p>
 * This configuration provides:
 * 1. A RedisTemplate bean for direct Redis operations, using JSON for values.
 * 2. A RedisCacheManager bean to enable Spring's caching abstraction (@Cacheable), also using JSON.
 */
@Configuration
@EnableCaching
public class RedisConfiguration {

    private final Logger logger = LoggerFactory.getLogger(RedisConfiguration.class);

    /**
     * Configures the RedisCacheManager to integrate with Spring's caching mechanism.
     * It sets up JSON serialization for cache values.
     *
     * @param connectionFactory The auto-configured Redis connection factory.
     * @return A configured RedisCacheManager.
     */
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        // Default cache configuration: no TTL, and JSON serialization
        RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()));

        return RedisCacheManager.builder(connectionFactory).cacheDefaults(cacheConfig).build();
    }

    /**
     * Configures the RedisTemplate for direct, low-level interaction with Redis.
     * It is set up to use String keys and JSON values for better readability and interoperability.
     *
     * @param connectionFactory The auto-configured Redis connection factory.
     * @return A configured RedisTemplate.
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // Use String serializer for keys
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());

        // Use JSON serializer for values
        GenericJackson2JsonRedisSerializer jsonSerializer = new GenericJackson2JsonRedisSerializer();
        template.setValueSerializer(jsonSerializer);
        template.setHashValueSerializer(jsonSerializer);

        template.afterPropertiesSet();
        return template;
    }

    /**
     * Creates the central dispatcher that will delegate messages.
     * Spring automatically injects a list of all beans that implement RedisChannelSubscriber.
     *
     * @param subscribers A list of all subscriber beans found in the application context.
     * @return The delegating message listener.
     */
    @Bean
    public DelegatingRedisMessageSubscriber delegatingRedisMessageSubscriber(List<RedisChannelSubscriber> subscribers) {
        return new DelegatingRedisMessageSubscriber(subscribers);
    }

    /**
     * The message listener container that dynamically registers all channels from subscribers.
     * This is the core of the reusable design.
     */
    @Bean
    public RedisMessageListenerContainer messageListenerContainer(
        RedisConnectionFactory connectionFactory,
        DelegatingRedisMessageSubscriber delegatingSubscriber,
        List<RedisChannelSubscriber> subscribers
    ) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        // Dynamically create ChannelTopic from all found subscribers
        List<ChannelTopic> topics = subscribers
            .stream()
            .map(sub -> new ChannelTopic(sub.getChannelToListen()))
            .collect(Collectors.toList());

        if (topics.isEmpty()) {
            logger.warn("No Redis channel subscribers found to register.");
        } else {
            container.addMessageListener(delegatingSubscriber, topics);
        }

        return container;
    }

    // Publisher bean không thay đổi, vẫn rất hữu ích
    @Bean
    public RedisPublisher redisPublisher(RedisTemplate<String, Object> redisTemplate) {
        return new RedisPublisher(redisTemplate);
    }
}
