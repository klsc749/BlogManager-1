package com.example.blogmanager.service;

import com.example.blogmanager.dao.UserDao;
import com.example.blogmanager.model.DO.User;
import com.example.blogmanager.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean checkUser(String username, String password) {
        User user = userDao.findByUsername(username);
        if (user == null) {
            return false;
        }
        String encryptedPassword = MD5Util.md5Encrypt(password, user.getSalt());
        return user.getPassword().equals(encryptedPassword);
    }

    public void saveUser(User user) {
        String salt = MD5Util.generateSalt();
        String encryptedPassword = MD5Util.md5Encrypt(user.getPassword(), salt);
        user.setSalt(salt);
        user.setPassword(encryptedPassword);
        userDao.save(user);
    }

    public User getUserByUsername(String username) {
        return userDao.findByUsername(username);
    }
}
