package com.xinlan.android.basesupport.util;

import android.Manifest;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.permissionx.guolindev.PermissionX;
import com.xinlan.android.basesupport.R;
import com.xinlan.android.basesupport.util.interfaces.PermissionCallBackInterface;

/**
 * @author: 63239
 * @date: 2020/11/23
 * 权限申请工具类
 */
public class PermissionUtils {

    /**
     * 申请权限
     * @param activity 上下文
     * @param permissions 申请的权限
     * @param callBack 回调方法
     * */
    public static void permission(FragmentActivity activity, String[] permissions, PermissionCallBackInterface callBack){
        permission(activity,permissions,"权限申请","授权",callBack);
    }
    /**
     * 申请权限
     * @param fragment 上下文
     * @param permissions 申请的权限
     * @param callBack 回调方法
     * */
    public static void permission(Fragment fragment, String[] permissions, PermissionCallBackInterface callBack){
        permission(fragment,permissions,"权限申请","授权",callBack);
    }
    /**
     * 申请权限
     * */
    public static void permission(FragmentActivity activity, String[] permissions,String message,String positiveText, PermissionCallBackInterface callBack){
        PermissionX.init(activity)
                .permissions(permissions)
                .onExplainRequestReason((scope, deniedList) -> scope.showRequestReasonDialog(deniedList, message, positiveText))
                .setDialogTintColor(ContextCompat.getColor(activity, R.color.blue_text), ContextCompat.getColor(activity, R.color.saddlebrown))
                .request((allGranted, grantedList, deniedList) -> {
                    if (allGranted) {
                        callBack.success();
                    } else {
                        callBack.fail();
                    }
                });
    }
    /**
     * 申请权限
     * */
    public static void permission(Fragment fragment, String[] permissions, String message, String positiveText, PermissionCallBackInterface callBack){
        PermissionX.init(fragment)
                .permissions(permissions)
                .onExplainRequestReason((scope, deniedList) -> scope.showRequestReasonDialog(deniedList, message, positiveText))
                .setDialogTintColor(ContextCompat.getColor(fragment.getContext(), R.color.blue_text), ContextCompat.getColor(fragment.getContext(), R.color.saddlebrown))
                .request((allGranted, grantedList, deniedList) -> {
                    if (allGranted) {
                        callBack.success();
                    } else {
                        callBack.fail();
                    }
                });
    }
    /**
     * 申请存储空间权限
     * */
    public static void permissionExternalStorage(FragmentActivity activity,PermissionCallBackInterface callBack){
        permissionExternalStorage(activity,"权限申请","授权",callBack);
    }
    /**
     * 申请存储空间权限
     * */
    public static void permissionExternalStorage(FragmentActivity activity,String message,String positiveText,PermissionCallBackInterface callBack){
        String[] permission =new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
        permission(activity,permission,message,positiveText,callBack);
    }
    /**
     * 申请存储空间权限
     * */
    public static void permissionExternalStorage(Fragment fragment,PermissionCallBackInterface callBack){
        permissionExternalStorage(fragment,"权限申请","授权",callBack);
    }
    /**
     * 申请存储空间权限
     * */
    public static void permissionExternalStorage(Fragment fragment,String message,String positiveText,PermissionCallBackInterface callBack){
        String[] permission =new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
        permission(fragment,permission,message,positiveText,callBack);
    }
    /**
     *
     * 申请相机权限
     * */
    public static void permissionCamera(FragmentActivity activity,PermissionCallBackInterface callBack){
        permissionCamera(activity,"权限申请","授权",callBack);
    }
    /**
     * 申请相机权限
     * */
    public static void permissionCamera(FragmentActivity activity,String message,String positiveText,PermissionCallBackInterface callBack){
        String[] permission =new String[] {Manifest.permission.CAMERA};
        permission(activity,permission,message,positiveText,callBack);
    }
    /**
     *
     * 申请相机权限
     * */
    public static void permissionCamera(Fragment fragment,PermissionCallBackInterface callBack){
        permissionCamera(fragment,"权限申请","授权",callBack);
    }
    /**
     * 申请相机权限
     * */
    public static void permissionCamera(Fragment fragment,String message,String positiveText,PermissionCallBackInterface callBack){
        String[] permission =new String[] {Manifest.permission.CAMERA};
        permission(fragment,permission,message,positiveText,callBack);
    }
    /**
     * 申请定位权限
     * */
    public static void permissionLocation(FragmentActivity activity,PermissionCallBackInterface callBack){
        permissionLocation(activity,"权限申请","授权",callBack);
    }
    /**
     * 申请定位权限
     * */
    public static void permissionLocation(FragmentActivity activity,String message,String positiveText,PermissionCallBackInterface callBack){
        String[] permission =new String[] {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};
        permission(activity,permission,message,positiveText,callBack);
    }
    /**
     * 申请定位权限
     * */
    public static void permissionLocation(Fragment fragment,PermissionCallBackInterface callBack){
        permissionLocation(fragment,"权限申请","授权",callBack);
    }
    /**
     * 申请定位权限
     * */
    public static void permissionLocation(Fragment fragment,String message,String positiveText,PermissionCallBackInterface callBack){
        String[] permission =new String[] {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION};
        permission(fragment,permission,message,positiveText,callBack);
    }
    /**
     * 申请录音权限
     * */
    public static void permissionRecordAudio(FragmentActivity activity, PermissionCallBackInterface callBack){
        permissionRecordAudio(activity,"权限申请","授权",callBack);
    }
    /**
     * 申请录音权限
     * */
    public static void permissionRecordAudio(FragmentActivity activity,String message,String positiveText,PermissionCallBackInterface callBack){
        String[] permission =new String[] {Manifest.permission.RECORD_AUDIO};
        permission(activity,permission,message,positiveText,callBack);
    }
    /**
     * 申请录音权限
     * */
    public static void permissionRecordAudio(Fragment fragment, PermissionCallBackInterface callBack){
        permissionRecordAudio(fragment,"权限申请","授权",callBack);
    }
    /**
     * 申请录音权限
     * */
    public static void permissionRecordAudio(Fragment fragment,String message,String positiveText,PermissionCallBackInterface callBack){
        String[] permission =new String[] {Manifest.permission.RECORD_AUDIO};
        permission(fragment,permission,message,positiveText,callBack);
    }
    /**
     * 申请拨打电话权限
     * */
    public static void permissionCallPhone(FragmentActivity activity,PermissionCallBackInterface callBack){
        permissionCallPhone(activity,"权限申请","授权",callBack);
    }
    /**
     * 申请拨打电话权限
     * */
    public static void permissionCallPhone(FragmentActivity activity,String message,String positiveText,PermissionCallBackInterface callBack){
        String[] permission =new String[] {Manifest.permission.CALL_PHONE};
        permission(activity,permission,message,positiveText,callBack);
    }
    /**
     * 申请拨打电话权限
     * */
    public static void permissionCallPhone(Fragment fragment,PermissionCallBackInterface callBack){
        permissionCallPhone(fragment,"权限申请","授权",callBack);
    }
    /**
     * 申请拨打电话权限
     * */
    public static void permissionCallPhone(Fragment fragment,String message,String positiveText,PermissionCallBackInterface callBack){
        String[] permission =new String[] {Manifest.permission.CALL_PHONE};
        permission(fragment,permission,message,positiveText,callBack);
    }
}
