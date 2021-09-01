package com.yaofclc.jsformdemo.dao;

import ohos.data.orm.OrmDatabase;
import ohos.data.orm.annotation.Database;

/**
 * @Author: yaofengchao
 * @CreateDate: 2021/9/1 1:07 AM
 * @Description: TODO
 */
@Database(entities = {UserUpgrade.class,Book.class},version = 2)
public abstract class BookStoreUpgrade extends OrmDatabase {
}
