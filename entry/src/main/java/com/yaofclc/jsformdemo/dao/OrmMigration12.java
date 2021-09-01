package com.yaofclc.jsformdemo.dao;

import ohos.data.orm.OrmMigration;
import ohos.data.rdb.RdbStore;
import ohos.hiviewdfx.HiLog;
import ohos.hiviewdfx.HiLogLabel;

/**
 * @Author: yaofengchao
 * @CreateDate: 2021/9/1 1:10 AM
 * @Description: TODO
 */
public class OrmMigration12 extends OrmMigration {
    private HiLogLabel LABEL_LOG = new HiLogLabel(HiLog.DEBUG, 0, "Ethan");
    public OrmMigration12(int beginVersion, int endVersion) {
        super(beginVersion, endVersion);
    }

    @Override
    public void onMigrate(RdbStore rdbStore) {
        HiLog.debug(LABEL_LOG, "DataBase Version 1->2 onMigrate called");

        rdbStore.executeSql("ALERT TABLE `user` ADD COLUMN `lastName` String");
    }
}
