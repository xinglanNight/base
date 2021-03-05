package com.xinlan.android.basesupport.util;

import android.content.Context;
import android.widget.Toast;

import com.xinlan.android.basesupport.base.BaseApplication;

/**
 * 提示信息
 */
public class ToastUtils {
    private static Toast mToast;

	public static void showToast(String text) {
		showToast(BaseApplication.getAppContext(), text, Toast.LENGTH_SHORT);
	}

	public static void showToast(Context context, String text) {
        showToast(context, text, Toast.LENGTH_SHORT);
    }

	public static void showLongToast(Context context, String text) {
		showToast(context, text, Toast.LENGTH_LONG);
	}

	private static void showToast(Context context, String text, int duration) {
        if (context == null) {
            return;
        }
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(BaseApplication.getAppContext(), text, duration);
        mToast.show();
    }


}
