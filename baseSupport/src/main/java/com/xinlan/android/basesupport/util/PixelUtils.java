package com.xinlan.android.basesupport.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.xinlan.android.basesupport.base.BaseApplication;

/**
 * @author: 63239
 * @date: 2021/2/1
 * 像素处理
 */
public class PixelUtils {

    private String getScreenParams() {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) BaseApplication.getAppContext().getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        int heightPixels = dm.heightPixels;//高的像素
        int widthPixels = dm.widthPixels;//宽的像素
        int densityDpi = dm.densityDpi;//dpi
        float xdpi = dm.xdpi;//xdpi
        float ydpi = dm.ydpi;//ydpi
        float density = dm.density;//density=dpi/160,密度比
        float scaledDensity = dm.scaledDensity;//scaledDensity=dpi/160 字体缩放密度比
        float heightDP = heightPixels / density;//高度的dp
        float widthDP = widthPixels / density;//宽度的dp
        String str = "heightPixels: " + heightPixels + "px";
        str += "\nwidthPixels: " + widthPixels + "px";
        str += "\ndensityDpi: " + densityDpi + "dpi";
        str += "\nxdpi: " + xdpi + "dpi";
        str += "\nydpi: " + ydpi + "dpi";
        str += "\ndensity: " + density;
        str += "\nscaledDensity: " + scaledDensity;
        str += "\nheightDP: " + heightDP + "dp";
        str += "\nwidthDP: " + widthDP + "dp";

        return str;
    }


    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    /**
     * 根据手机的分辨率从 px 的单位 转成为 sp(像素)
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
    /**
     * 根据手机的分辨率从 sp 的单位 转成为 px(像素)
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
