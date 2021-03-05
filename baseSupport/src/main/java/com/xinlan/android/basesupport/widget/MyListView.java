package com.xinlan.android.basesupport.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * ScrollView 嵌套 ListView
 * 只显示了一行，有一种动态重设ListView高度的方法，但还会遮住一部分，
 * 在这里采用一个自定义的组件继承于原来的类
 * ，重写onMeasure方法重绘组件，然后替换布局文件中ListView布局即可。
 * 
 * @author Wuyson
 */
public class MyListView extends ListView {

	public MyListView(Context context) {
		super(context);
	}

	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
