package com.xinlan.android.basesupport.util.interfaces;

/**
 * @author: 63239
 * @date: 2020/11/23
 * 权限申请回调
 */
public interface PermissionCallBackInterface {
    /**
     * 权限申请成功回调
     * */
    void success();

    /**
     * 权限申请失败回调
     * */
    void fail();
}
