package com.xinlan.android.basesupport.widget.menu;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xinlan.android.basesupport.R;
import com.xinlan.android.basesupport.entity.HomeMenuEntity;
import com.xinlan.android.basesupport.util.ColorUtils;

import java.util.List;


/**
 * @author: 63239
 * @date: 2021/1/10
 */
public class HomeMenuAdapter extends BaseQuickAdapter<HomeMenuEntity, BaseViewHolder> {
    private int mWidth;
    private int mTitleDistance;
    private int leftPadding;
    private int rightPadding;
    private Context mContext;
    private int textColor;

    public HomeMenuAdapter(Context context,List<HomeMenuEntity> data, int width) {
        super(R.layout.home_menu_item, data);
        mWidth = width;
        mContext = context;
        mTitleDistance = -1;
        textColor = ColorUtils.getColor(context,R.color.black);
    }
    public HomeMenuAdapter(Context context,List<HomeMenuEntity> data, int width,int titleDistance) {
        super(R.layout.home_menu_item, data);
        mWidth = width;
        mContext = context;
        mTitleDistance = titleDistance;
        textColor = ColorUtils.getColor(context,R.color.black);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, HomeMenuEntity entity) {
        TextView title = baseViewHolder.findView(R.id.tv_title);
        ImageView imageView =  baseViewHolder.findView(R.id.iv_image);
        RelativeLayout.LayoutParams layoutParams =  (RelativeLayout.LayoutParams)imageView.getLayoutParams();
        layoutParams.width = mWidth;
        layoutParams.height = mWidth;
        if (leftPadding == 0){
            leftPadding = imageView.getPaddingLeft();
        }
        if (rightPadding == 0){
            rightPadding = imageView.getPaddingRight();
        }
        if(mTitleDistance == -1){
            mTitleDistance = imageView.getPaddingBottom();
        }
        imageView.setPadding(leftPadding,imageView.getPaddingTop(),
                rightPadding,mTitleDistance);

        imageView.setLayoutParams(layoutParams);

        title.setText(entity.getName());
        title.setTextColor(textColor);
        if (null != entity.getImageUrl()){
            Glide.with(mContext).load(entity.getImageUrl()).into(imageView);
        } else if (0 != entity.getLocalImage()){
            imageView.setImageResource(entity.getLocalImage());
        } else {
            Resources resources = getContext().getResources();
            Drawable drawable = resources.getDrawable(R.mipmap.ico_book_air_tickets);
            imageView.setImageDrawable(drawable);
        }
    }

    public void setTextColor(int color){
        textColor = color;
    }

    public int getLeftPadding() {
        return leftPadding;
    }

    public void setLeftPadding(int leftMargin) {
        this.leftPadding = leftMargin;
    }

    public int getRightPadding() {
        return rightPadding;
    }

    public void setRightPadding(int rightMargin) {
        this.rightPadding = rightMargin;
    }
}
