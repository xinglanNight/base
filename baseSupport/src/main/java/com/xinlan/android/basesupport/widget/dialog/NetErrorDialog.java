package com.xinlan.android.basesupport.widget.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.xinlan.android.basesupport.R;
import com.xinlan.android.basesupport.base.BaseDialogFragment;


/**
 * @author Wuyson
 * 错误弹窗
 */
public class NetErrorDialog extends BaseDialogFragment {
    private static NetErrorDialog dialog = null;
    private View mView;
    private Window window;
    public static final String ARGS_CONTENT = "args_content";
    private ImageView ivBack;
    private TextView tvError;
    public static String error;
    public static long delayTime = 30000;
    private static boolean isShow = false;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Holo_Light);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null){
            mView = inflater.inflate(R.layout.dialog_net_error,container,false);
        }
        ivBack = mView.findViewById(R.id.iv_bar_start);
        tvError = mView.findViewById(R.id.tv_error);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeErrorDialog();
            }
        });
        tvError.setText(error);

        initView();
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getDialog() != null) {
            try {
                getDialog().setOnShowListener(null);
                getDialog().setOnDismissListener(null);
                getDialog().setOnCancelListener(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void initView() {
        Dialog dialog = getDialog();
        if (dialog != null){
             window = dialog.getWindow();
        }
//        if (window != null) {
//            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        }

        if (dialog != null){
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
        }

        setCancelable(false);

    }

    @Override
    public void onStart() {
        super.onStart();
//        Window window = getDialog().getWindow();
//        if (window != null) {
//            WindowManager.LayoutParams windowParams = window.getAttributes();
//            windowParams.dimAmount = 0.5f;
//            window.setAttributes(windowParams);
//        }

        //设置宽度顶满屏幕,无左右留白
//        DisplayMetrics dm = new DisplayMetrics();
//        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
//
//        if (getDialog().getWindow() != null) {
//            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        }
    }

    @Override
    public void show(FragmentManager fragmentManager, String tag) {
        try {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            //在每个add事务前增加一个remove事务，防止连续的add
            fragmentTransaction.remove(this).commit();
            fragmentTransaction.add(this, tag).commit();
            // 这里把原来的commit()方法换成了commitAllowingStateLoss()
            // 解决Can not perform this action after onSaveInstanceState with DialogFragment
//            fragmentTransaction.commitAllowingStateLoss();
            //解决java.lang.IllegalStateException: Fragment already added
            fragmentManager.executePendingTransactions();
        } catch (Exception e) {
            //同一实例使用不同的tag会异常,这里捕获一下
            e.printStackTrace();
        }

    }

    public static void showErrorDialog(FragmentManager fragmentManager,String message){
        if (isShow || fragmentManager == null){
            return;
        }
        isShow = true;
        NetErrorDialog.error = message;
        dialog = new NetErrorDialog();
        dialog.show(fragmentManager, "dialog_error");
    }
    public static void closeErrorDialog(){
        if (dialog == null){
            return;
        }
        dialog.dismissAllowingStateLoss();
        dialog.ivBack.postDelayed(new Runnable() {
            @Override
            public void run() {
                isShow = false;
            }
        },delayTime);
        dialog = null;

    }
}
