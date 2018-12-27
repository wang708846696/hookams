/*
 * Copyright (C) 2017 Beijing Didi Infinity Technology and Development Co.,Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.life.com.myapplication.proxy;

import android.app.ActivityManagerNative;
import android.app.IActivityManager;
import android.content.Context;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import cn.life.com.myapplication.manager.PluginManager;

public class ActivityManagerProxy implements InvocationHandler {
    private static final String TAG = "MyActivityManagerProxy";

    public static IActivityManager newInstance(PluginManager pluginManager, IActivityManager activityManager) {
        return (IActivityManager) Proxy.newProxyInstance(activityManager.getClass().getClassLoader(),
                new Class[]{IActivityManager.class}, new ActivityManagerProxy(pluginManager, activityManager));
    }

    private PluginManager mPluginManager;
    private IActivityManager mActivityManager;

    public ActivityManagerProxy(PluginManager pluginManager, IActivityManager activityManager) {
        this.mPluginManager = pluginManager;
        this.mActivityManager = activityManager;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            Log.i(TAG, method.getName() + "\n\n");
            return method.invoke(this.mActivityManager, args);
        } catch (Throwable th) {
            Throwable c = th.getCause();
            if (c != null && c instanceof DeadObjectException) {
                // retry connect to system binder
                IBinder ams = ServiceManager.getService(Context.ACTIVITY_SERVICE);
                if (ams != null) {
                    IActivityManager am = ActivityManagerNative.asInterface(ams);
                    mActivityManager = am;
                }
            }
            Throwable cause = th;
            do {
                if (cause instanceof RemoteException) {
                    throw cause;
                }
            } while ((cause = cause.getCause()) != null);
            throw c != null ? c : th;
        }
    }

}
