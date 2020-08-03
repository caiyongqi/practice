package com.graduation.practice.dao;

import com.graduation.practice.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserDao {
    // 根据账户查询用户
    User findUserByAccount(User user);
    // 查找出所有的管理员、不包括自己
    List<User> findAllAdmin(User user);
    // 添加管理员
    int saveUser(User user);
    // 批量删除
    int deleteSelectedUser(List<String> accountList);
    // 删除单个用户
    int deleteUser(@Param("account") String account);
    // 更新用户
    int updateUser(User user);
    // 根据账户，模糊查询
    List<User> searchAllAdminByAccount(@Param("keyword") String keyword, @Param("account") String account);
    //插入user,用于学生的添加
    int insertUser(User user);

}
