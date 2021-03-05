package com.xinlan.android.basesupport.util.Thread;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.List;
import java.util.Locale;

/**
 * @author: 63239
 * @date: 2020/12/28
 */
public class PackageUtils {


    public static boolean isInstallApp(Context context,String packName) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName.toLowerCase(Locale.ENGLISH);
                if (pn.equals(packName)) {
                    return true;
                }
            }
        }
        return false;
    }
}
