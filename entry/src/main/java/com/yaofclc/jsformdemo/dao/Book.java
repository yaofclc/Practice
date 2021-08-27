package com.yaofclc.jsformdemo.dao;

import ohos.data.orm.OrmObject;
import ohos.data.orm.annotation.Entity;
import ohos.data.orm.annotation.PrimaryKey;

/**
 * @Author: yaofengchao
 * @CreateDate: 2021/8/28 12:35 AM
 * @Description: TODO
 */
@Entity(tableName = "book")
public class Book extends OrmObject {
    @PrimaryKey(autoGenerate = true)
    private Integer bookId;
    private String bookName;

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}
