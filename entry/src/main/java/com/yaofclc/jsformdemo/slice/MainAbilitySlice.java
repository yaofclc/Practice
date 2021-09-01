package com.yaofclc.jsformdemo.slice;

import com.yaofclc.jsformdemo.MyApplication;
import com.yaofclc.jsformdemo.ResourceTable;
import com.yaofclc.jsformdemo.dao.*;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.content.Intent;
import ohos.agp.components.Component;
import ohos.data.DatabaseHelper;
import ohos.data.orm.OrmContext;
import ohos.data.orm.OrmObject;
import ohos.data.orm.OrmPredicates;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

import java.util.List;
import java.util.Random;

public class MainAbilitySlice extends AbilitySlice {

    private HiLogLabel TAG = new HiLogLabel(HiLog.DEBUG, 0, "Ethan");

    private DatabaseHelper helper;
    private OrmContext bookStore;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);


        helper = new DatabaseHelper(this);
        findComponentById(ResourceTable.Id_btn_insert).setClickedListener(this::insert);
        findComponentById(ResourceTable.Id_btn_query).setClickedListener(this::query);
        findComponentById(ResourceTable.Id_btn_delete).setClickedListener(this::delete);

        findComponentById(ResourceTable.Id_btn_upgrade).setClickedListener(this::upgrede);



    }

    private void delete(Component component) {
        //删除
        bookStore = helper.getOrmContext("BookStore", "BookStore.db", BookStore.class);
        OrmPredicates predicates = bookStore.where(User.class).equalTo("age", 12);
        bookStore.delete(predicates);
        bookStore.flush();
        bookStore.close();
    }

    private void query(Component component) {
        bookStore = helper.getOrmContext("BookStore", "BookStore.db", BookStore.class);
        //查询
        OrmPredicates query = bookStore.where(User.class);
        List<User> users = bookStore.query(query);
        for (int i = 0; i < users.size(); i++) {
            HiLog.info(TAG, "user: " + users.get(i));
        }
        bookStore.flush();
        bookStore.close();

    }

    private void insert(Component component) {
        bookStore = helper.getOrmContext("BookStore", "BookStore.db", BookStore.class);
        User user = new User();
        user.setAge(12);
        user.setName("LiLei");
        bookStore.insert(user);
        bookStore.flush();
        bookStore.close();
    }

    private void upgrede(Component component) {
        helper = new DatabaseHelper(this);
        OrmContext bookStore = helper.getOrmContext("BookStore", "BookStore.db", BookStoreUpgrade.class, new OrmMigration12(1, 2));
        UserUpgrade userUpgrade = new UserUpgrade();
        userUpgrade.setLastName("Tom");
        bookStore.insert(userUpgrade);
        bookStore.flush();

        OrmPredicates ormPredicates = bookStore.where(UserUpgrade.class).equalTo("lastName", "Tom");
        List<UserUpgrade> query = bookStore.query(ormPredicates);
        for (int i = 0; i < query.size(); i++) {
            HiLog.debug(TAG, "---> "+query.get(i));
        }

    }


    @Override
    public void onActive() {
        super.onActive();
    }

    @Override
    public void onForeground(Intent intent) {
        super.onForeground(intent);
    }
}
