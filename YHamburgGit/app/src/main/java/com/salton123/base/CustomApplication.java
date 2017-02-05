package com.salton123.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Process;

import com.beardedhen.androidbootstrap.TypefaceProvider;
import com.salton123.common.util.FrescoImageLoader;
import com.salton123.mengmei.model.api.BmobString;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.xutils.x;

import java.util.List;

import cn.bmob.v3.Bmob;


/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2016/8/22 21:45
 * Time: 21:45
 * Description:
 */
public class CustomApplication extends Application {
    private long mTerminalType = 0x20001; //android terminal type 0x20001

    private static CustomApplication mInstance;

    public static CustomApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        Bmob.initialize(this, BmobString.APPLICATION_ID);  //初始化Bmob
        x.Ext.init(this);   //初始化Xutils
        CrashReport.initCrashReport(getApplicationContext(), "9ec43cf084", false);
        TypefaceProvider.registerDefaultIconSets();
        FrescoImageLoader.Init(this);
        RegToWx();
    }

    public boolean isPkgMainProc() {
        ActivityManager am = ((ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = this.getPackageName();
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }

    public static IWXAPI mIwxapi;

    //注册到微信服务
    private void RegToWx() {
        mIwxapi = WXAPIFactory.createWXAPI(getApplicationContext(), GlobalParams.WXAPP_ID);
        mIwxapi.registerApp(GlobalParams.WXAPP_ID);
    }

}
