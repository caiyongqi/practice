package com.graduation.practice.service;

import com.graduation.practice.entity.User;

import java.util.List;

public interface UserService {
    User findUserByAccount(User user);
    List<User> findAllAdmin(User user);
    int insertUser(User user);
}
