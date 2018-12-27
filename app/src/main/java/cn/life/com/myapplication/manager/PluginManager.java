package cn.life.com.myapplication.manager;

import android.app.ActivityManager;
import android.app.ActivityManagerNative;
import android.app.Application;
import android.app.IActivityManager;
import android.content.Context;
import android.os.Build;
import android.util.Singleton;

import cn.life.com.myapplication.proxy.ActivityManagerProxy;
import cn.life.com.myapplication.utils.ReflectUtil;

/**
 * @author wangwei
 */

public class PluginManager {
    private static volatile PluginManager sInstance = null;

    // Context of host app
    private Context mContext;
    private IActivityManager mActivityManager; // Hooked IActivityManager binder

    private PluginManager(Context context) {
        Context app = context.getApplicationContext();
        if (app == null) {
            this.mContext = context;
        } else {
            this.mContext = ((Application) app).getBaseContext();
        }
        prepare();
    }

    public static PluginManager getInstance(Context base) {
        if (sInstance == null) {
            synchronized (PluginManager.class) {
                if (sInstance == null)
                    sInstance = new PluginManager(base);
            }
        }
        return sInstance;
    }

    private void prepare() {
        this.hookSystemServices();
    }

    private void hookSystemServices() {
        try {
            Singleton<IActivityManager> defaultSingleton;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                defaultSingleton = (Singleton<IActivityManager>) ReflectUtil.getField(ActivityManager.class, null, "IActivityManagerSingleton");
            } else {
                defaultSingleton = (Singleton<IActivityManager>) ReflectUtil.getField(ActivityManagerNative.class, null, "gDefault");
            }

            IActivityManager activityManagerProxy = ActivityManagerProxy.newInstance(this, defaultSingleton.get());
            // Hook IActivityManager from ActivityManagerNative
            ReflectUtil.setField(defaultSingleton.getClass().getSuperclass(), defaultSingleton, "mInstance", activityManagerProxy);

            if (defaultSingleton.get() == activityManagerProxy) {
                this.mActivityManager = activityManagerProxy;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void init() {


    }
}
