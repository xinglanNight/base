package com.xinlan.android.basesupport.util;

import android.os.Looper;

public class BaseCompatUtils {
    /**
     * 当前方法是否在主线程里面运行
     */
    public static boolean isMainThread() {
        // 当前方法是主线程里面运行
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
