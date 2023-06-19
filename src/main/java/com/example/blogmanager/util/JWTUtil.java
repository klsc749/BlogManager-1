package com.example.blogmanager.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
public class JWTUtil {
    private static final long expireTime = 24 * 60 * 60 * 1000;
    private static final String secretKey = "mini_weibo";

    public static String genToken(String username){
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        return generateTokenWithExpirationTime(expireTime, map);
    }


    public static String generateTokenWithExpirationTime(long expireTime, Map<String, String> params){
        Date expire = new Date(System.currentTimeMillis() + expireTime);
        JWTCreator.Builder builder = JWT.create().withExpiresAt(expire);
        for(String key : params.keySet()){
            builder = builder.withClaim(key, params.get(key));
        }
        return builder.sign(Algorithm.HMAC256(secretKey));
    }

    public static boolean verify(String token){
        try{
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey)).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public static boolean printTokenInfo(String token, Map<String, String> params){
        try{
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey)).build();
            DecodedJWT jwt = verifier.verify(token);
            for(String key : params.keySet()){
                System.out.println(key + ":" + jwt.getClaim(key));
            }
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public static String getFieldFromToken(String token, String field){
        try{
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secretKey)).build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim(field).asString();
        }catch (Exception e){
            return null;
        }
    }
}
