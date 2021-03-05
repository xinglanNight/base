package com.xinlan.android.basesupport.widget.switchView;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;

import com.xinlan.android.basesupport.R;

/**
 * @author: 63239
 * @date: 2021/2/2
 * 单纯的开关控件
 */
public class SwitchView extends AppCompatImageView {


    private boolean defaultFlag = false;
    private boolean status;
    private OnClickListener mListener;

    public SwitchView(Context context) {
        this(context, null);
        init(context, null);
    }

    public SwitchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context, attrs);
    }

    public SwitchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwitchView);
        status = typedArray.getBoolean(R.styleable.SwitchView_open, defaultFlag);
        typedArray.recycle();

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                status = !status;
                setStatus(status);
                if (null != mListener){
                    mListener.onClick(view);
                }
            }
        });
        setStatus(status);
    }

    public void setStatus(boolean flag){
        status = flag;
        if (status){
            this.setImageResource(R.mipmap.ic_open_btn);
            return;
        }
        this.setImageResource(R.mipmap.ic_off_btn);
    }
    public boolean getStatus(){
        return status;
    }

    public void setClickListener(OnClickListener listener){
        mListener = listener;
    }

}
