package com.xinlan.android.basesupport.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.xinlan.android.basesupport.options.EventBusManager;
import com.xinlan.android.basesupport.util.BaseCompatUtils;
import com.xinlan.android.basesupport.util.ReflexUtils;
import com.xinlan.android.basesupport.util.RefreshUtils;
import com.xinlan.android.basesupport.util.ResumeRefreshUtils;
import com.xinlan.android.basesupport.util.Thread.ThreadCallBack;
import com.xinlan.android.basesupport.util.Thread.ThreadRun;
import com.xinlan.android.basesupport.util.ToastUtils;
import com.xinlan.android.basesupport.util.interfaces.ActivityCallbackInterface;
import com.xinlan.android.basesupport.widget.dialog.MyProgressDialog;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Fragment基类
 */
public abstract class BaseFragment<T extends ViewBinding> extends Fragment implements ThreadRun, ThreadCallBack {
    private final String TAG = this.getClass().getSimpleName();
    protected Context mContext;
    protected Activity mActivity;
    protected View mView;
//    private Unbinder unBinder;
    protected T binding;


    protected boolean isViewCreated;

    protected boolean isViewVisible;

    protected boolean hasLoadInitData;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = getContext();
        mActivity = getActivity();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null){
            Type superclass = getClass().getGenericSuperclass();
            if (superclass instanceof ParameterizedType) {
                Class<?> aClass = (Class<?>) ((ParameterizedType) superclass).getActualTypeArguments()[0];
                try {
                    Method method = aClass.getDeclaredMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class);
                    binding = (T) method.invoke(null, getLayoutInflater(), container, false);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
                mView = binding.getRoot();
            }

        }
//4-1        mView = inflater.inflate(getLayoutViewId(),container,false);
//4-2        unBinder = ButterKnife.bind(this, mView);
        return mView;
    }
    private void addClickEvent(){
        if (useNotDoubleClickListener()){
            ReflexUtils.addAllViewNotDoubleClickListener(binding);
        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ResumeRefreshUtils.clearPageRefresh(getClass());
        if (useEventBus()) {
            EventBusManager.getInstance().register(this);
        }
        init();
        addClickEvent();
    }


    protected abstract void init();

    protected void showToast(String message) {
        ToastUtils.showToast(message);
    }

    public boolean useEventBus() {
        return false;
    }

    /**
     * 是否添加点击事件防止双击代理
     * */
    protected boolean useNotDoubleClickListener() {
        return true;
    }


    protected void showLoading(final String message) {
        if (BaseCompatUtils.isMainThread()) {
            MyProgressDialog.showLoadDialog(mContext, false, message);
        } else {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    MyProgressDialog.showLoadDialog(mContext, false, message);
                }
            });
        }
    }

    public void closeLoadDialog() {
        if (BaseCompatUtils.isMainThread()) {
            MyProgressDialog.dismissDialog();
        } else {
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    MyProgressDialog.dismissDialog();
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("提示",this.getClass().getSimpleName());
        // 这个页面有刷新的情况下，调用刷新方法
        if (ResumeRefreshUtils.getIsPageRefresh(getClass())) {
            refreshMet();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {

        super.onHiddenChanged(hidden);
        if(hidden){
            onVisible();
        }else{
            onInvisble();
        }
    }
    protected void onVisible() {
        isViewVisible = true;
        lazyLoad(needLazyLoad());
    }
    private void onInvisble(){
    }
    protected abstract boolean needLazyLoad();

    protected abstract void loadData();


    private void lazyLoad(boolean needLazyLoad) {
        if(needLazyLoad) {
            if (isViewCreated && isViewVisible && !hasLoadInitData) {
                loadData();
                hasLoadInitData = true;
            }
        }else if(!hasLoadInitData && isViewCreated){
            loadData();
            hasLoadInitData = true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//4-4        unBinder.unbind();
        binding = null;
        if (useEventBus()) {
            EventBusManager.getInstance().unregister(this);
        }
        //线程归属页面 当页面退出时，线程终止并清空
//        ThreadManager.onDestroy(TAG);
    }

    /*******************************************
     *****        页面刷新   *******************
     ******************************************/
    protected static void setPageRefresh(Class<?> pClass) {
        ResumeRefreshUtils.setPageRefresh(pClass);
    }

    private void refreshMet() {

    }

    protected void setActivityInterface(Class<?> callActivity,
                                        ActivityCallbackInterface callbackInterface, boolean isManuallyCall) {
        RefreshUtils.setActivityInterface(mContext, callActivity, callbackInterface, isManuallyCall);
    }

    /**
     * 线程处理方法
     * */
    @Override
    public void onThreadRun(int taskId){

    }
    /**
     * 线程回调成功
     * */
    @Override
    public void onCallbackFromThread(int taskId){

    }
    /**
     * 线程回调失败
     * */
    @Override
    public void onCallbackFromThreadWithFail(int taskId,Exception exception){

    }

}
