package com.yaofclc.jsformdemo.dao;

import ohos.data.orm.OrmDatabase;
import ohos.data.orm.annotation.Database;

/**
 * @Author: yaofengchao
 * @CreateDate: 2021/8/28 12:32 AM
 * @Description: TODO
 */
@Database(entities = {User.class,Book.class},version = 1)
public abstract class BookStore extends OrmDatabase {

}
