package com.xinlan.android.basesupport.util;

import timber.log.Timber;

public class LogUtils {

    public static void d(String message){
        Timber.d(message);
    }

    public static void e(String message){
        Timber.tag("ZLKJ").e(message);
    }

    public static void e(String tag,String message){
        Timber.tag(tag).e(message);
    }

    public static void outLog(String message){
        Timber.tag("ZLKJ").e(message);
    }
}
