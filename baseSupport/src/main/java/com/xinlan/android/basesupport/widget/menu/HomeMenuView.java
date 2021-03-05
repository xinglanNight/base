package com.xinlan.android.basesupport.widget.menu;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.xinlan.android.basesupport.R;
import com.xinlan.android.basesupport.base.BaseApplication;
import com.xinlan.android.basesupport.entity.HomeMenuEntity;
import com.xinlan.android.basesupport.util.PixelUtils;

import java.util.List;

/**
 * @author: 63239
 * @date: 2021/2/1
 * 菜单栏  可设置阴影
 */
public class HomeMenuView extends LinearLayout {

    private final Context mContext;
    private int lineNum;
    private int defaultLineNum = 5;
    private boolean defaultShowShape = false;
    private boolean showShape = true;
    private HomeMenuAdapter mAdapter;
    private int background;

    private RecyclerView recyclerView;
    private CardView cardView;
    private LinearLayout linearLayout;

    private OnItemClickListener mListener;



    public HomeMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initParam(attrs,null);
    }


    public HomeMenuView(Context context, AttributeSet attrs, HomeMenuAdapter adapter) {
        super(context, attrs);
        mContext = context;
        mAdapter = adapter;
        initParam(attrs,adapter);
    }


    private void initParam(AttributeSet attrs,HomeMenuAdapter adapter){
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.HomeMenuView);
        lineNum = typedArray.getInteger(R.styleable.HomeMenuView_line_num, defaultLineNum);
        showShape = typedArray.getBoolean(R.styleable.HomeMenuView_line_num, defaultShowShape);
        background = typedArray.getResourceId(R.styleable.HomeMenuView_mvBackground, 0);
        typedArray.recycle();
        mAdapter = adapter;
        init();
    }

    private void init(){
        LayoutInflater.from(mContext).inflate(R.layout.widget_menu_view, this);
        recyclerView = findViewById(R.id.recycler_view_menu);
        cardView = findViewById(R.id.card_view);
        linearLayout = findViewById(R.id.ll_box);
        if (null != mAdapter){
            lineNum = Math.min(mAdapter.getData().size(), lineNum);
            GridLayoutManager layoutManage = new GridLayoutManager(getContext(), lineNum);
            recyclerView.setLayoutManager(layoutManage);
            recyclerView.setAdapter(mAdapter);
        } else {
            recyclerView.setVisibility(INVISIBLE);
        }


        if (!showShape){
            cardView.setCardElevation(0);
        }
        if (background != 0){
            linearLayout.setBackgroundResource(background);
            cardView.setRadius(0);
            cardView.setBackgroundColor(0x00000000);
        }

    }



    public void setData(List<HomeMenuEntity> entities,OnItemClickListener listener){
        setData(entities,1,-1,listener);
    }
    public void setData(List<HomeMenuEntity> entities,float multiple,int titleDistance,OnItemClickListener listener){
        mListener = listener;
        int imageWidth = (BaseApplication.windowsWidth - PixelUtils.dip2px(mContext,40)) / 5;
        imageWidth = (int)(imageWidth * multiple);
        mAdapter = new HomeMenuAdapter(mContext,entities,imageWidth,titleDistance);
        mAdapter.setOnItemClickListener(mListener);
        lineNum = Math.min(mAdapter.getData().size(), lineNum);
        GridLayoutManager layoutManage = new GridLayoutManager(getContext(), lineNum);
        recyclerView.setLayoutManager(layoutManage);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setVisibility(VISIBLE);

    }




    public void setItemClickListener(OnItemClickListener listener){
        mListener = listener;
        if (null != mAdapter){
            mAdapter.setOnItemClickListener(mListener);
        }
    }

    public void setTextColor(int color){
        if (null == mAdapter){
            return;
        }
        mAdapter.setTextColor(color);
    }

    public void setImagePadding(int leftPadding,int rightPadding){
        if (null == mAdapter){
            return;
        }
        mAdapter.setLeftPadding(leftPadding);
        mAdapter.setRightPadding(rightPadding);
    }
}

