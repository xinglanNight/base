package com.xinlan.android.basesupport.util;

import android.os.Looper;

public class AppUtils {
    // 当前方法是主线程里面运行
    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

}
