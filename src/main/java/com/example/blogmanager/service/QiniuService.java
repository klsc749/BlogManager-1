package com.example.blogmanager.service;

import com.example.blogmanager.config.QiniuConfig;
import com.example.blogmanager.util.QiniuUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QiniuService {

    private final QiniuConfig qiniuConfig;

    public String getUploadToken() {
        return QiniuUtil.generateUploadToken(qiniuConfig.getAccessKey(), qiniuConfig.getSecretKey(), qiniuConfig.getBucketName(), qiniuConfig.getExpireSeconds());
    }
}
