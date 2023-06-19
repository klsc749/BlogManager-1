//this class is used to encrypt the password
package com.example.blogmanager.util;
import org.springframework.util.DigestUtils;

public class MD5Util {
    public static String md5Encrypt(String password, String salt) {
        return DigestUtils.md5DigestAsHex((password + salt).getBytes());
    }

    public static String generateSalt() {
        return DigestUtils.md5DigestAsHex(String.valueOf(System.currentTimeMillis()).getBytes());
    }
}
