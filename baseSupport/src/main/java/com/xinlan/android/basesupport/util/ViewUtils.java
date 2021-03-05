package com.xinlan.android.basesupport.util;

import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.View;

import androidx.annotation.IdRes;

/**
 * @author: 63239
 * @date: 2020/11/9
 * 控件工具类
 */
public class ViewUtils {
    /**
     * 手动测绘获取控件的高度 控件内容的大小和控件设置的高度无关
     * */
    public static int getViewMeasureHeight(View root){
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(100, View.MeasureSpec.EXACTLY);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(100, View.MeasureSpec.EXACTLY);
        root.measure(widthMeasureSpec, heightMeasureSpec);
        return heightMeasureSpec;
    }

    /**
     * 手动测绘获取控件的宽 控件内容的大小和控件设置的宽度无关
     * */
    public static int getViewMeasureWidth(View root){
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(100, View.MeasureSpec.EXACTLY);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(100, View.MeasureSpec.EXACTLY);
        root.measure(widthMeasureSpec, heightMeasureSpec);
        return widthMeasureSpec;
    }
    public static Bitmap generateViewCacheBitmap(View view) {
        view.destroyDrawingCache();
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthMeasureSpec, heightMeasureSpec);
        int width = view.getMeasuredWidth();
        int height = view.getMeasuredHeight();
        view.layout(0, 0, width, height);
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        return Bitmap.createBitmap(view.getDrawingCache());
    }

    public static View findBrotherView(View view, @IdRes int id, int level) {
        int count = 0;
        View temp = view;
        while (count < level) {
            View target = temp.findViewById(id);
            if (target != null) {
                return target;
            }
            count += 1;
            if (temp.getParent() instanceof View) {
                temp = (View) temp.getParent();
            } else {
                break;
            }
        }
        return null;
    }
    /**
     * ViewHolder简洁写法,避免适配器中重复定义ViewHolder,减少代码量 用法:
     *
     * <pre>
     * if (convertView == null)
     * {
     * 	convertView = View.inflate(context, R.layout.ad_demo, null);
     * }
     * TextView tv_demo = ViewHolderUtils.get(convertView, R.id.tv_demo);
     * ImageView iv_demo = ViewHolderUtils.get(convertView, R.id.iv_demo);
     * </pre>
     */
    public static <T extends View> T hold(View view, int id)
    {
        SparseArray<View> viewHolder = (SparseArray<View>) view.getTag();

        if (viewHolder == null)
        {
            viewHolder = new SparseArray<View>();
            view.setTag(viewHolder);
        }

        View childView = viewHolder.get(id);

        if (childView == null)
        {
            childView = view.findViewById(id);
            viewHolder.put(id, childView);
        }

        return (T) childView;
    }

    /**
     * 替代findviewById方法
     */
    public static <T extends View> T find(View view, int id)
    {
        return (T) view.findViewById(id);
    }
}
