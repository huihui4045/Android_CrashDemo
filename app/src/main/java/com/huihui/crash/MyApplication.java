package com.huihui.crash;

import com.mob.MobApplication;

/**
 * Created by gavin
 * Time 2017/7/19  16:15
 * Email:molu_clown@163.com
 */

public class MyApplication extends MobApplication {


    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        CrashHandler crashHandler = CrashHandler.getInstance();

        crashHandler.init(this);
    }


    public static MyApplication getInstance() {

        return instance;
    }
}
