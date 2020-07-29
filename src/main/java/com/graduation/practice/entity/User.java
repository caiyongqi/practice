package com.graduation.practice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private int id;
    // 账号
    private String account;
    // 密码
    private String password;
    // 角色名称
    private String role;
    /*
    * 角色类型
    * 1: 系统管理员
    * 2：教务管理员
    * 3: 老师
    * 4： 辅导员
    * 5: 学生
    * */
    private int type;

    public User(String account){
        this.account = account;
    }

    public User(String account, String password, int type, String role) {
        this.account = account;
        this.password = password;
        this.type = type;
        this.role = role;
    }
}
