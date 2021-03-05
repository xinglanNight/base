package com.xinlan.android.basesupport.util;

import android.util.Log;
import android.view.View;

import androidx.viewbinding.ViewBinding;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Calendar;

/**
 * @author: 63239
 * @date: 2020/12/25
 * 反射工具类
 */
public class ReflexUtils {
    /**
     * 反射添加控件的防止双击实现
     * 该方法需要添加在所有控件添加点击事件之后执行
     * viewBinding中保存该页面所有的控件信息
     * */
    public static void addAllViewNotDoubleClickListener(ViewBinding viewBinding){
        if (null == viewBinding){
            return;
        }
        //获取所有视图控件实例类
        Class<?> aClass = viewBinding.getClass();
        //获取所有控件信息
        Field[] fields = aClass.getDeclaredFields();
        //以此循环操作
        for (Field field : fields){
            //设置私有成员可访问
            field.setAccessible(true);
            //获取成员变量类型
            Class<?> fieldType = field.getType();
            //判断成员变量是否继承自View
            if (View.class.isAssignableFrom(fieldType)){
                try {
                    //获取成员变量实例
                    View view = (View)field.get(viewBinding);
                    //判断时候有点击事件
                    boolean hasOnClickListener = view.hasOnClickListeners();
                    //有点击事件执行添加操作
                    if (hasOnClickListener){
                        //后续修改为直接获取View.class
                        Class<?> viewClass = Class.forName("android.view.View");
                        //获取监听类
                        Class<?> listenerInfo = Class.forName("android.view.View$ListenerInfo");
                        //获取得到ListenerInfo对象的方法
                        Method getListenerInfo = viewClass.getDeclaredMethod("getListenerInfo");
                        //获取点击事件对象
                        Field innerClassField = listenerInfo.getDeclaredField("mOnClickListener");
                        //设置访问权限
                        innerClassField.setAccessible(true);
                        getListenerInfo.setAccessible(true);
                        //重新设置点击事件 添加双击检测
                        View.OnClickListener listener = (View.OnClickListener)innerClassField.get(getListenerInfo.invoke(view));
                        addClickListen(view,listener);
                    }
                } catch (Exception e) {
                    Log.e("提示","失败添加点击事件");
                    e.printStackTrace();
                }

            }
        }
    }
    /**
     * 添加点击事件
     * */
    private static void addClickListen(View view,View.OnClickListener oldClickListener){
        view.setOnClickListener(new View.OnClickListener() {
            private long mLastClickTime;
            private  static final int MIN_CLICK_DELAY_TIME = 500;
            @Override
            public void onClick(View view) {
                long currentTime = Calendar.getInstance().getTimeInMillis();
                if (currentTime - mLastClickTime > MIN_CLICK_DELAY_TIME) {
                    mLastClickTime = currentTime;
                    oldClickListener.onClick(view);
                }
            }
        });
    }


    /**
     * 反射获取所有方法
     * */
    public static Method[] getMethod(Object obj){
        return obj.getClass().getDeclaredMethods();
    }
}
