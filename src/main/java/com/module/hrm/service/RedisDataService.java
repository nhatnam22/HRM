package com.module.hrm.service;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;

/**
 * A comprehensive service for interacting with Redis's primary data types.
 * This class acts as a facade over RedisTemplate to provide clear, type-safe methods
 * for Strings, Lists, Hashes, Sets, and Sorted Sets.
 */
@Service
public class RedisDataService {

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisDataService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // =================================================================
    // == 1. String Operations
    // =================================================================

    /**
     * Sets a key-value pair.
     * @param key The key.
     * @param value The value.
     */
    public void stringSet(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * Sets a key-value pair with a specific Time-To-Live (TTL).
     * @param key The key.
     * @param value The value.
     * @param ttl The duration until the key expires.
     */
    public void stringSet(String key, Object value, Duration ttl) {
        redisTemplate.opsForValue().set(key, value, ttl);
    }

    /**
     * Gets a value by key, casting it to the specified type.
     * @param key The key.
     * @param type The class to cast the result to.
     * @return The value, or null if the key doesn't exist.
     */
    public <T> T stringGet(String key, Class<T> type) {
        Object value = redisTemplate.opsForValue().get(key);
        return value != null ? type.cast(value) : null;
    }

    // =================================================================
    // == 2. List Operations
    // =================================================================

    /**
     * Adds a value to the beginning of a list (LPUSH).
     * @param key The list key.
     * @param value The value to add.
     */
    public void listLeftPush(String key, Object value) {
        redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * Adds multiple values to the beginning of a list (LPUSH).
     * @param key The list key.
     * @param values The values to add.
     */
    public void listLeftPushAll(String key, Object... values) {
        redisTemplate.opsForList().leftPushAll(key, values);
    }

    /**
     * Gets a range of elements from a list.
     * @param key The list key.
     * @param start The starting index (0-based).
     * @param end The ending index (-1 for the last element).
     * @return A list of objects.
     */
    public List<Object> listGetRange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * Removes and returns the first element of a list (LPOP).
     * @param key The list key.
     * @return The popped value, or null if the list is empty.
     */
    public Object listLeftPop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    // =================================================================
    // == 3. Hash Operations
    // =================================================================

    /**
     * Sets a field-value pair within a hash.
     * @param key The hash key.
     * @param field The field within the hash.
     * @param value The value to set.
     */
    public void hashSet(String key, String field, Object value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * Gets a value from a specific field within a hash.
     * @param key The hash key.
     * @param field The field to retrieve.
     * @param type The class to cast the result to.
     * @return The value, or null if the field doesn't exist.
     */
    public <T> T hashGet(String key, String field, Class<T> type) {
        Object value = redisTemplate.opsForHash().get(key, field);
        return value != null ? type.cast(value) : null;
    }

    /**
     * Gets all field-value pairs from a hash.
     * @param key The hash key.
     * @return A map representing the hash.
     */
    public Map<Object, Object> hashGetAll(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    // =================================================================
    // == 4. Set Operations (Unordered, Unique)
    // =================================================================

    /**
     * Adds one or more members to a set.
     * @param key The set key.
     * @param values The members to add.
     */
    public void setAdd(String key, Object... values) {
        redisTemplate.opsForSet().add(key, values);
    }

    /**
     * Gets all members of a set.
     * @param key The set key.
     * @return A set of objects.
     */
    public Set<Object> setGetMembers(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * Checks if a value is a member of a set.
     * @param key The set key.
     * @param value The value to check.
     * @return True if the value is a member, false otherwise.
     */
    public boolean setIsMember(String key, Object value) {
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(key, value));
    }

    // =================================================================
    // == 5. Sorted Set (ZSet) Operations (Ordered, Unique)
    // =================================================================

    /**
     * Adds a member to a sorted set with a specific score.
     * @param key The sorted set key.
     * @param value The member to add.
     * @param score The score used for ordering.
     */
    public void sortedSetAdd(String key, Object value, double score) {
        redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * Gets a range of members from a sorted set (ordered by score).
     * @param key The sorted set key.
     * @param start The starting index (0-based).
     * @param end The ending index (-1 for the last element).
     * @return An ordered set of objects.
     */
    public Set<Object> sortedSetGetRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    /**
     * Gets a range of members and their scores from a sorted set.
     * @param key The sorted set key.
     * @param start The starting index.
     * @param end The ending index.
     * @return A set of TypedTuple, where each tuple contains the value and its score.
     */
    public Set<TypedTuple<Object>> sortedSetGetRangeWithScores(String key, long start, long end) {
        return redisTemplate.opsForZSet().rangeWithScores(key, start, end);
    }

    /**
     * Removes one or more members from a sorted set.
     * @param key The sorted set key.
     * @param values The members to remove.
     */
    public void sortedSetRemove(String key, Object... values) {
        redisTemplate.opsForZSet().remove(key, values);
    }
}
