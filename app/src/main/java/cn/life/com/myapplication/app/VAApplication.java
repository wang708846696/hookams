package cn.life.com.myapplication.app;

import android.app.Application;
import android.content.Context;

import cn.life.com.myapplication.manager.PluginManager;

/**
 * Created by renyugang on 16/8/10.
 */
public class VAApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        PluginManager.getInstance(base).init();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
