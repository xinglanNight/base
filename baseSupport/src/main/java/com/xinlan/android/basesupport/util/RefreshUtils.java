package com.xinlan.android.basesupport.util;

import android.app.Activity;
import android.content.Context;

import com.xinlan.android.basesupport.util.entity.ActivityCallbackInterfaceEntity;
import com.xinlan.android.basesupport.util.interfaces.ActivityCallbackInterface;

import java.util.ArrayList;
import java.util.List;

public class RefreshUtils {
    /**
     * 页面需要执行的回调方法
     */
    private static List<ActivityCallbackInterfaceEntity> mActivityCallbackInterfaceEntity;
    /**
     * 设置页面回调触发的回调接口
     */
    public static void setActivityInterface(Context context, Class<?> callActivity,
                                            ActivityCallbackInterface callback, boolean isManuallyCall) {
        synchronized (RefreshUtils.class) {
            if (context == null || callActivity == null || callback == null) {
                return;
            }
            if (mActivityCallbackInterfaceEntity == null) {
                mActivityCallbackInterfaceEntity = new ArrayList<>();
            }
            mActivityCallbackInterfaceEntity
                    .add(new ActivityCallbackInterfaceEntity(context,
                            callActivity, callback, isManuallyCall));
        }
    }

    /**
     * 触发页面回调,必须主线程执行
     */
    public static void callActivityInterface(Context context, boolean isManuallyCall, Object obj) {
        if (context == null || !AppUtils.isMainThread()) {
            return;
        }
        synchronized (RefreshUtils.class) {
            if (mActivityCallbackInterfaceEntity == null) {
                return;
            }
            String currClassName = context.getClass().getName();
            List<ActivityCallbackInterfaceEntity> delCallbackInterface = null;
            try {
                for (ActivityCallbackInterfaceEntity m : mActivityCallbackInterfaceEntity) {
                    if (!currClassName.equals(m.callActivityName)) {
                        continue;
                    }

                    // 触发场景不等于 设置的时候，直接回收
                    if (isManuallyCall && !m.isManuallyCall) {
                        continue;
                    }
                    if (!isManuallyCall && m.isManuallyCall) {
                        continue;
                    }


                    if (delCallbackInterface == null) {
                        delCallbackInterface = new ArrayList<>();
                    }

                    delCallbackInterface.add(m);

                    if (!(m.mContext instanceof Activity)) {
                        continue;
                    }
                    if (((Activity) m.mContext).isFinishing()) {
                        continue;
                    }
                    m.callback.callback(obj);
                }

                if (delCallbackInterface != null) {
                    for (ActivityCallbackInterfaceEntity m : delCallbackInterface) {
                        mActivityCallbackInterfaceEntity.remove(m);
                    }
                    delCallbackInterface.clear();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
