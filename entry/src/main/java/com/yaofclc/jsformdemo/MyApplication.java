package com.yaofclc.jsformdemo;

import ohos.aafwk.ability.AbilityPackage;
import ohos.app.Context;

public class MyApplication extends AbilityPackage {
    public static Context mContext;
    @Override
    public void onInitialize() {
        super.onInitialize();
        mContext = getContext();

    }
}
