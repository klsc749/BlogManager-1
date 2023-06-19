package com.example.blogmanager.dao;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class JWTDao {
    private final String keyPrefix = "Token:";
    private final int expireTimeDays = 2;

    private final int additionalExpireTimeDay = 1;
    @Resource
    private RedisTemplate<String, String> redisTemplate;


    public void saveJWT(String username, String jwt) {
        redisTemplate.opsForValue().set(keyPrefix + username, jwt, expireTimeDays, TimeUnit.DAYS);
    }

    public String getJWT(String username) {
        return redisTemplate.opsForValue().get(keyPrefix +username);
    }

    public void deleteJWT(String username) {
        redisTemplate.delete(keyPrefix +username);
    }

    public Long getExpireTime(String username) {
        return redisTemplate.getExpire(keyPrefix +username, TimeUnit.HOURS);
    }

    public void updateExpireTime(String username) {
        redisTemplate.expire(keyPrefix +username, additionalExpireTimeDay, TimeUnit.DAYS);
    }
}
