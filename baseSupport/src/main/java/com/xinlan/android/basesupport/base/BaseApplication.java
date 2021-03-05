package com.xinlan.android.basesupport.base;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.webkit.WebView;

import androidx.annotation.RequiresApi;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import java.util.List;

/**
 * Application基类
 */
abstract public class BaseApplication extends MultiDexApplication {
    private static BaseApplication baseApplication;
    /**
     * 线程名
     * */
    private String mProcessName;
    /**
     * 包名
     * */
    private String mPackageName;

    private boolean mIsMainProcess;
    /**
     * 主线程的handler
     * */
    private static Handler handler;
    /**
     * 屏幕宽度
     * */
    public static int windowsWidth;
    /**
     * 屏幕长度
     * */
    public static int windowsHeight;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
        mProcessName = getCurProcessName(base);
        mPackageName = getPackageName();
        mIsMainProcess = !TextUtils.isEmpty(mPackageName) && TextUtils.equals(mPackageName, mProcessName);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            webViewSetPath(base);
        }
    }
    public void initHandler(){
        if (null == handler){
            handler = new Handler(Looper.getMainLooper());
        }
    }
    /**
     * 在此处初始化网络请求的url
     * */
    abstract void initApiUrl();

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
        initHandler();
        getWindowsSize();
        initApiUrl();
    }

    public static BaseApplication getAppContext() {
        return baseApplication;
    }

    public static Handler getHandler(){
        return handler;
    }


    private String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> infos = mActivityManager.getRunningAppProcesses();
        if (infos == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo appProcess : infos) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }


    private void getWindowsSize(){
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager mWindowManager  = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        mWindowManager.getDefaultDisplay().getMetrics(metric);
        windowsWidth = metric.widthPixels; // 屏幕宽度（像素）
        windowsHeight = metric.heightPixels; // 屏幕宽度（像素）
    }
    /**
     * Android P 以及之后版本不支持同时从多个进程使用具有相同数据目录的WebView
     * 为其它进程webView设置目录
     */
    @RequiresApi(api = 28)
    public void webViewSetPath(Context context) {
        //判断不等于默认进程名称
        if (!mIsMainProcess) {
            WebView.setDataDirectorySuffix(mProcessName);
        }
    }
    public String getmPackageName() {
        return mPackageName;
    }

    public void setmPackageName(String mPackageName) {
        this.mPackageName = mPackageName;
    }
}
