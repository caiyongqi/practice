package com.graduation.practice.dao;

import com.graduation.practice.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserDao {
    // 根据账户查询用户
    User findUserByAccount(User user);
    // 查找出所有的管理员、不包括自己
    List<User> findAllAdmin(User user);
    //插入user
    int insertUser(User user);
}
