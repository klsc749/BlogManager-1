package com.example.blogmanager.config;

// QiniuConfig.java
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QiniuConfig {

    @Value("${qiniu.bucketName}")
    private String bucketName;

    @Value("${qiniu.accessKey}")
    private String accessKey;

    @Value("${qiniu.secretKey}")
    private String secretKey;

    @Value("${qiniu.expireSeconds}")
    private long expireSeconds = 3600;

    public String getBucketName() {
        return bucketName;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public long getExpireSeconds() {
        return expireSeconds;
    }
}
