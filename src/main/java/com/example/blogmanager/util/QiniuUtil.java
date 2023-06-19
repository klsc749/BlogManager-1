package com.example.blogmanager.util;

// QiniuUtil.java
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.UrlSafeBase64;

public class QiniuUtil {
    public static String generateUploadToken(String accessKey, String secretKey, String bucket, long expireSeconds) {
        Auth auth = Auth.create(accessKey, secretKey);
        return auth.uploadToken(bucket, null, expireSeconds, null, true);
    }
}

