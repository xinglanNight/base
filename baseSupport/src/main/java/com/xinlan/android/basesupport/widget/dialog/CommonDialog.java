package com.xinlan.android.basesupport.widget.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;


import com.xinlan.android.basesupport.R;
import com.xinlan.android.basesupport.base.BaseDialogFragment;

import java.util.Objects;

/**
 * @author Wuyson
 */
public class CommonDialog extends BaseDialogFragment implements View.OnClickListener {
    private String mContent;
    private View mView;
    private TextView tvContent;
    private TextView tvTitle;
    private Button btnStart,btnEnd;
    private CommonDialogButtonClick mListener;
    private Window window;
    private String mStartText, mEndText;
    private String mTitle;

    public static final String ARGS_TITLE = "args_title";
    public static final String ARGS_CONTENT = "args_content";
    public static final String ARGS_START_TEXT = "args_start_text";
    public static final String ARGS_END_TEXT = "args_end_text";
    private static final String START_TEXT = "确定";
    private static final String END_TEXT = "取消";

    public static CommonDialog newInstance(String content) {
        return newInstance(content,START_TEXT,END_TEXT);
    }

    public static CommonDialog newInstance(String content, String startText, String endText) {
        return newInstance(content,START_TEXT,END_TEXT,"");
    }

    public static CommonDialog newInstance(String content, String startText, String endText,String title) {
        Bundle args = new Bundle();
        args.putString(ARGS_CONTENT, content);
        args.putString(ARGS_START_TEXT, startText);
        args.putString(ARGS_END_TEXT, endText);
        args.putString(ARGS_TITLE, title);
        CommonDialog fragment = new CommonDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mContent = args.getString(ARGS_CONTENT);
            mStartText = args.getString(ARGS_START_TEXT);
            mEndText = args.getString(ARGS_END_TEXT);
            mTitle = args.getString(ARGS_TITLE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null){
            mView = inflater.inflate(R.layout.dialog_common,container,false);
        }
        tvContent = mView.findViewById(R.id.tv_content);
        btnStart = mView.findViewById(R.id.btn_start);
        btnEnd = mView.findViewById(R.id.btn_end);
        tvTitle = mView.findViewById(R.id.tv_title);
        btnStart.setOnClickListener(this);
        btnEnd.setOnClickListener(this);
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
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            window.setBackgroundDrawable(ContextCompat.getDrawable(Objects.requireNonNull(getContext()),
                    R.drawable.rounded_rectangle_background_white_10));
        }

        if (dialog != null){
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
        }

        setCancelable(false);

        tvContent.setText(mContent);
        btnStart.setText(mStartText);
        btnEnd.setText(mEndText);
        tvTitle.setText(mTitle);
        if ("".equals(mTitle)){
            tvTitle.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_start) {
            if (mListener != null) {
                mListener.leftOnClick();
            }
            dismissAllowingStateLoss();
        } else if (id == R.id.btn_end) {
            if (mListener != null) {
                mListener.rightOnClick();
            }
            dismissAllowingStateLoss();
        }
    }

    public void setOnDialogClickListener(CommonDialogButtonClick listener){
        mListener = listener;
    }

}
