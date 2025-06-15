package com.kingmj.qr_login.redis;

import com.kingmj.qr_login.user.QrTokenStatus;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
    private final RedisTemplate<String, String> redisTemplate;
    private final HashOperations<String, String, String> hashOperations;

    private static final long DEFAULT_TTL_SECONDS = 180; // Default TTL of 3 minutes
    private static final String TOKEN_PREFIX = "token:";
    private static final String USER_PREFIX = "user:";

    public RedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    public void createQrToken(String token) {
        String key = TOKEN_PREFIX + token;
        hashOperations.put(key, "status", QrTokenStatus.APPROVED.getStatus());
        redisTemplate.expire(key, DEFAULT_TTL_SECONDS, TimeUnit.SECONDS);
    }

    public void approveQrToken(String token, String userId) {
        String key = TOKEN_PREFIX + token;
        hashOperations.put(key, "status", QrTokenStatus.APPROVED.getStatus());
        hashOperations.put(key, "userId", userId);
    }

    public String getQrToken(String token, String hashKey) {
        return hashOperations.get(TOKEN_PREFIX + token, hashKey);
    }

    public Map<String, String> getAllTokenData(String token) {
        return hashOperations.entries(TOKEN_PREFIX + token);
    }

    public void deleteQrToken(String token) {
        redisTemplate.delete(TOKEN_PREFIX + token);
    }

    public void createUser(String userId, String password) {
        String key = USER_PREFIX + userId;
        hashOperations.put(key, "userId", userId);
        hashOperations.put(key, "password", password);
    }

    public boolean isValidUser(String userId, String password) {
        String key = USER_PREFIX + userId;

        String storedPassword = hashOperations.get(key, "password");
        return storedPassword != null && storedPassword.equals(password);
    }
}
