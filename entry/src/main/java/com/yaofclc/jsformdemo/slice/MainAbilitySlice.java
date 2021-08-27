package com.yaofclc.jsformdemo.slice;

import com.yaofclc.jsformdemo.ResourceTable;
import com.yaofclc.jsformdemo.dao.BookStore;
import com.yaofclc.jsformdemo.dao.User;
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

public class MainAbilitySlice extends AbilitySlice {

    private HiLogLabel TAG = new HiLogLabel(HiLog.DEBUG, 0, "MainAbility");

    private DatabaseHelper helper;
    private OrmContext bookStore;

    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setUIContent(ResourceTable.Layout_ability_main);


        helper = new DatabaseHelper(this);
        bookStore = helper.getOrmContext("BookStore", "BookStore.db", BookStore.class);

        findComponentById(ResourceTable.Id_btn_insert).setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                //插入
                User user = new User();
                user.setName("LiLei");
                user.setAge(12);
                bookStore.insert(user);


                User user1 = new User();
                user1.setName("Lucy");
                user1.setAge(20);
                bookStore.insert(user);
                bookStore.flush();
            }
        });
        findComponentById(ResourceTable.Id_btn_query).setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                //查询
                OrmPredicates query = bookStore.where(User.class);
                List<User> users = bookStore.query(query);
                for (int i = 0; i < users.size(); i++) {
                    HiLog.info(TAG, "user: " + users.get(i));
                }
            }
        });
        findComponentById(ResourceTable.Id_btn_delete).setClickedListener(new Component.ClickedListener() {
            @Override
            public void onClick(Component component) {
                //删除

                OrmPredicates predicates = bookStore.where(User.class).equalTo("age", 12);
                bookStore.delete(predicates);
                bookStore.flush();
            }
        });



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
