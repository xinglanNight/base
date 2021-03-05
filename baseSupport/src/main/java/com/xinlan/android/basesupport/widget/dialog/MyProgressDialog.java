package com.xinlan.android.basesupport.widget.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xinlan.android.basesupport.R;
import com.xinlan.android.basesupport.widget.met.OnProgressDialogCallback;

/**
 * 弹出进度条
 */
public class MyProgressDialog {
    public static ProgressDialog loadBar = null;

    static AnimationDrawable mAnimation;

    /**
     * 显示加载进度条
     */
    public static TextView showLoadDialog(Context context, boolean cancelable, String txtContent) {
        try {
            dismissDialog();
            if (loadBar == null) {
                // 加载进度条
                loadBar = new ProgressDialog(context, R.style.MyProgressDialog);
                loadBar.show();
                loadBar.setContentView(R.layout.widget_my_progress_dialog);
                loadBar.setCancelable(cancelable);

                TextView tvContent = (TextView) loadBar.findViewById(R.id.tv_content);
                tvContent.setText(txtContent);
                Glide.with(context)
                        .load(R.drawable.common_ic_loading)
                        .into(((ImageView) loadBar.findViewById(R.id.iv_loading)));
//                Animation anim = AnimationUtils.loadAnimation(context, R.anim.round_loading);
//                loadBar.findViewById(R.id.iv_loading).startAnimation(anim);
                return tvContent;
            }
        } catch (Exception e) {
            if (loadBar != null) {
                try {
                    loadBar.dismiss();
                } catch (Exception e2) {
                }
            }
            loadBar = null;
        }
        return null;
    }

    /**
     * 显示加载中弹层
     */
    public static void showLoadDialog(final Context mContext, final String txtContent) {
        showLoadDialog(mContext, false, txtContent);
    }

    public static void showAutoDismissSuccessDialog(final Context context,
                                                    final boolean cancelable, final String txtContent) {
        showAutoDismissSuccessDialog(context, cancelable, txtContent, null);
    }

    public static void showAutoDismissSuccessDialog(final Context context, final boolean cancelable,
                                                    final String txtContent, final OnProgressDialogCallback callback) {
        showAutoDismissSuccessDialog(context, cancelable, txtContent, callback, 2000);
    }

    public static void showAutoDismissSuccessDialog(final Context context, final boolean cancelable,
                                                    final String txtContent, final OnProgressDialogCallback callback,
                                                    final long delayMillis) {
        showLoadDialog(context, cancelable, txtContent);

        if (loadBar != null) {
            loadBar.findViewById(R.id.iv_loading).setVisibility(View.GONE);
            loadBar.findViewById(R.id.img_sucess).setVisibility(View.VISIBLE);
        }

        loadBar.findViewById(R.id.iv_loading)
                .postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dismissDialog();
                        if (callback != null) {
                            callback.callback();
                        }
                    }
                }, delayMillis);
    }

    /**
     * 销毁加载进度条
     */
    public static void dismissDialog() {
        // 当前方法在主线程里面运行的话
        dismiss();
    }

    /**
     * 销毁加载框
     */
    private static void dismiss() {
        if (loadBar != null) {
            try {
                loadBar.dismiss();
            } catch (Exception e) {
            }
        }
        loadBar = null;
    }
}
