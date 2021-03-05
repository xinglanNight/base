package com.xinlan.android.basesupport.util.entity;

import android.content.Context;

import com.xinlan.android.basesupport.util.interfaces.ActivityCallbackInterface;

/**
 * XX页面触发上个页面的回调
 *
 * @author Administrator
 */
public class ActivityCallbackInterfaceEntity {

    public Object object;
    /**
     * 触发的页面
     */
    public String callActivityName;
    /**
     * 是否需要手动触发
     */
    public boolean isManuallyCall;
    /**
     * 上个页面Activity
     */
    public Context mContext;
    /**
     * 回调接口
     */
    public ActivityCallbackInterface callback;

    public ActivityCallbackInterfaceEntity(Context context, Class<?> clazz,
                                           ActivityCallbackInterface callback, boolean isManuallyCall) {
        this.mContext = context;
        this.callback= callback;
        this.isManuallyCall = isManuallyCall;
        this.callActivityName = clazz.getName();
    }
}
