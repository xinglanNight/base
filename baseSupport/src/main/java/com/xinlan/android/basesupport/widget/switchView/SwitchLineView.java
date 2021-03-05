package com.xinlan.android.basesupport.widget.switchView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xinlan.android.basesupport.R;
import com.xinlan.android.basesupport.util.PixelUtils;

/**
 * @author: 63239
 * @date: 2021/2/2
 */
public class SwitchLineView extends LinearLayout {

    private String mText;
    private float mTextSize;
    private float defaultTextSize = 15;
    private int mTextColor;
    private int mLeftImage;
    private int mRightImage;
    private int mTextStyle;
    private boolean mOpen;


    private TextView mTextView;
    private ImageView mLeftImageView;
    private ImageView mRightImageView;
    private SwitchView mSwitchView;


    public SwitchLineView(Context context) {
        super(context);
    }

    public SwitchLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getParam(context,attrs);
    }

    public void getParam(Context context, AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwitchLineView);
        mText = typedArray.getString(R.styleable.SwitchLineView_slText);
        mTextSize = typedArray.getDimension(R.styleable.SwitchLineView_slTextSize, PixelUtils.sp2px(context,defaultTextSize));
        mTextColor = typedArray.getColor(R.styleable.SwitchLineView_slTextColor,getResources().getColor(android.R.color.black));
        mTextStyle =  typedArray.getInt(R.styleable.SwitchLineView_slTextBold,0);
        mLeftImage =  typedArray.getResourceId(R.styleable.SwitchLineView_slLeftImage,0);
        mRightImage =  typedArray.getResourceId(R.styleable.SwitchLineView_slRightImage,0);
        mOpen = typedArray.getBoolean(R.styleable.SwitchLineView_slOpen,false);
        typedArray.recycle();
        init(context);
    }
    private void initView(Context context){
        LayoutInflater.from(context).inflate(R.layout.widget_switch_line, this);
        mTextView = findViewById(R.id.tv_text);
        mLeftImageView = findViewById(R.id.iv_left);
        mRightImageView = findViewById(R.id.iv_right);
        mSwitchView = findViewById(R.id.iv_sw_right);

    }
    private void init(Context context){
        initView(context);
        mSwitchView.setVisibility(VISIBLE);
        mRightImageView.setVisibility(GONE);
        mTextView.setText(mText);
        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX,mTextSize);
        mTextView.setTextColor(mTextColor);
        mTextView.setTypeface(Typeface.defaultFromStyle(mTextStyle));
        mLeftImageView.setBackgroundResource(mLeftImage);
        if (mRightImage != 0){
            mSwitchView.setVisibility(GONE);
            mRightImageView.setVisibility(VISIBLE);
            mRightImageView.setBackgroundResource(mRightImage);
        } else {
            mSwitchView.setStatus(mOpen);
        }
    }


    public void setSwitchClickListener(OnClickListener listener){
        mSwitchView.setClickListener(listener);
    }


}
