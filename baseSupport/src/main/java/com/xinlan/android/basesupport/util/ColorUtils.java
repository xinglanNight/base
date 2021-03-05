package com.xinlan.android.basesupport.util;

import android.content.Context;
import android.os.Build;

import androidx.annotation.ColorRes;

/**
 * @author: 63239
 * @date: 2021/2/2
 * 总是忘记获取颜色的方法 所以直接写了个工具类
 * 默认先写了两个颜色 后续可以自己加
 */
public class ColorUtils {


    public static int getColor(Context context,@ColorRes int id){
        if (Build.VERSION.SDK_INT >= 23) {
            return context.getColor(id);
        } else {
            return context.getResources().getColor(id);
        }
    }


    public static int getBlack(Context context){
        return  getColor(context,android.R.color.black);
    }

    public static int getWhite(Context context){
        return  getColor(context,android.R.color.white);
    }
}
