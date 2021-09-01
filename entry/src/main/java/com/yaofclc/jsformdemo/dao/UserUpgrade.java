package com.yaofclc.jsformdemo.dao;

import ohos.data.orm.OrmObject;
import ohos.data.orm.annotation.Entity;
import ohos.data.orm.annotation.PrimaryKey;

/**
 * @Author: yaofengchao
 * @CreateDate: 2021/9/1 1:06 AM
 * @Description: TODO
 */
@Entity(tableName = "user")
public class UserUpgrade extends OrmObject {
    @PrimaryKey(autoGenerate = true)
    private Integer  userId;
    private String name;
    private int age;
    private String lastName;

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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "UserUpgrade{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
