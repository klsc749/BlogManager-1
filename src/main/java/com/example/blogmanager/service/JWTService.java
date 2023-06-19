package com.example.blogmanager.service;

import com.example.blogmanager.dao.JWTDao;
import com.example.blogmanager.util.JWTUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class JWTService {
    @Resource
    private  JWTDao jwtDao;

    public String saveJWT(String username) {
        String token = JWTUtil.genToken(username);
        jwtDao.saveJWT(username, token);
        return token;
    }

    public boolean checkJWT(String username, String token) {
        String jwt = jwtDao.getJWT(username);
        if(jwt == null)
            return false;
        long remainHours = jwtDao.getExpireTime(username);
        if(remainHours < 24)
            jwtDao.updateExpireTime(username);
        return jwt.equals(token);
    }

    public String extractUsername(String token) {
        return JWTUtil.getFieldFromToken(token, "username");
    }

    public void deleteJWT(String jwt) {
        String username = extractUsername(jwt);
        jwtDao.deleteJWT(username);
    }
}
