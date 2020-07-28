package com.graduation.practice.service.impl;

import com.graduation.practice.dao.UserDao;
import com.graduation.practice.entity.User;
import com.graduation.practice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;
    // 不能忘记添加@Autowired
    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    public UserServiceImpl() {}

    @Override
    public User findUserByAccount(User user) {
        return userDao.findUserByAccount(user);
    }
}
