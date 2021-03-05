package com.xinlan.android.basesupport.util.Thread;

import java.io.Serializable;

/**
 * @author: 63239
 * @date: 2020/12/14
 * 线程执行回调接口
 */
public interface ThreadCallBack extends Serializable {
    /**
     * 请求成功回调函数
     *
     * @param taskId
     */
    public void onCallbackFromThread(int taskId);

    /**
     * 请求失败回调函数
     *
     * @param taskId
     * @param exception
     */
    public void onCallbackFromThreadWithFail(int taskId, Exception exception);
}
