package com.graduation.practice.dao;

import com.graduation.practice.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface UserDao {
    User findUserByAccount(User user);
}
