package com.badlogic.Jetpack;
/// com.badlogic.Jetpack.MyApplication

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.badlogic.others.SDCardListener;
import com.badlogic.utils.ALog;
import com.badlogic.utils.Tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MyApplication extends Application {


    public static String getPackageName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    SDCardListener  listener;

    @Override
    public void onCreate() {
        Tools.setApplication(this);
        Tools.isApkDebugable(this);
        ALog.mark = Tools.isApkDebugable(this);
        ///----------
        String app_id = getPackageName(this);
        Log.d("wjw02", "20200921b-MyApplication-onCreate"
                + "-app_id->" + app_id
        );
        ///----------
        super.onCreate();
        registerActivity();
        //-----

        /*
        todo 需要在长生命周期，引用,被系统回收就接受不到事件了
         */
        listener = new SDCardListener("/sdcard");
        //开始监听
        listener.startWatching();
        /*
         * 在这里做一些操作，比如创建目录什么的
         */
        //最后停止监听
        listener.stopWatching();
    }

    private List<Activity> ActivityList = new ArrayList<>();

    private void registerActivity() {
        registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                ActivityList.add(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                ActivityList.remove(activity);
            }
        });
    }


}
