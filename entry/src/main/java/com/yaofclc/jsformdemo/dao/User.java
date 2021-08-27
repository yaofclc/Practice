package com.yaofclc.jsformdemo.dao;

import ohos.data.orm.OrmObject;
import ohos.data.orm.annotation.Entity;
import ohos.data.orm.annotation.PrimaryKey;

/**
 * @Author: yaofengchao
 * @CreateDate: 2021/8/28 12:33 AM
 * @Description: TODO
 */
@Entity(tableName = "user")
public class User extends OrmObject {
    @PrimaryKey(autoGenerate = true)
    private Integer  userId;
    private String name;
    private int age;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
