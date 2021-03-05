package com.xinlan.android.basesupport.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Lifecycle;
import androidx.viewbinding.ViewBinding;


import com.xinlan.android.basesupport.R;
import com.xinlan.android.basesupport.compat.AppManager;
import com.xinlan.android.basesupport.options.EventBusManager;
import com.xinlan.android.basesupport.util.BaseCompatUtils;
import com.xinlan.android.basesupport.util.ReflexUtils;
import com.xinlan.android.basesupport.util.RefreshUtils;
import com.xinlan.android.basesupport.util.ResumeRefreshUtils;
import com.xinlan.android.basesupport.util.StatusBarUtil;
import com.xinlan.android.basesupport.util.Thread.ThreadCallBack;
import com.xinlan.android.basesupport.util.Thread.ThreadRun;
import com.xinlan.android.basesupport.util.ToastUtils;
import com.xinlan.android.basesupport.util.interfaces.ActivityCallbackInterface;
import com.xinlan.android.basesupport.widget.dialog.MyProgressDialog;
import com.xinlan.android.basesupport.widget.dialog.NetErrorDialog;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import autodispose2.AutoDispose;
import autodispose2.AutoDisposeConverter;
import autodispose2.androidx.lifecycle.AndroidLifecycleScopeProvider;
import butterknife.Unbinder;


/**
 * 权限申请,progressDialog
 * 1.setContentView(getContentViewId())  +  getContentViewId（） ; 正常集成
 * 2.doContentView();       ViewBinding集成
 */
public abstract class BaseActivity<T extends ViewBinding> extends AppCompatActivity implements ThreadRun, ThreadCallBack {
    private final String TAG = this.getClass().getSimpleName();
    protected Context mContext;
    protected Unbinder unbinder;
    public T binding;
    private int a = 1;
    private int b = 2;
    private boolean isFirstShow;


    public void launch(Class<?> clazz) {
        this.startActivity(new Intent(this, clazz));
    }
    public void launch(Class<?> clazz, boolean flag){
        Intent intent = new Intent(this, clazz);
        intent.putExtra("flag",flag);
        this.startActivity(intent);
    }
    public void launch(Class<?> clazz, int id,String keyName){
        Intent intent = new Intent(this,clazz);
        intent.putExtra(keyName, id);
        this.startActivity(intent);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        isFirstShow = true;
        if (a == b){
            setContentView(new View(mContext));
            LayoutInflater.from(mContext).inflate(R.layout.activity_matisse,null);
            getLayoutInflater();
        }
        //跳转数据获取放在before
        doBefore();
        getParam();
        doContentView();
        doAfter();
        getIntNetData();
        init(savedInstanceState);
        setEvent();
        addClickEvent();
    }


    /**
     * 获取传递的参数
     * */
    protected void getParam(){

    }

    /**
     * 获取网络数据
     * */
    protected void getIntNetData(){

    }



    private void addClickEvent(){
        if (useNotDoubleClickListener()){
            ReflexUtils.addAllViewNotDoubleClickListener(binding);
        }
    }

    /**
     * ViewBinding
     * 执行绑定操作
     */
    protected void doContentView() {
        Type superclass = getClass().getGenericSuperclass();
        if (superclass instanceof ParameterizedType) {
            try {
                Class<?> aClass = (Class<?>) ((ParameterizedType) superclass).getActualTypeArguments()[0];

                Method method = aClass.getDeclaredMethod("inflate", LayoutInflater.class);
                binding = (T) method.invoke(null, getLayoutInflater());
            } catch (Exception e) {
                e.printStackTrace();
            }
            setContentView(binding.getRoot());
        }
    }

    protected void doBefore() {
        AppManager.getAppManager().addActivity(this);
    }


    protected void doAfter() {
        setStatusBar();
        if (useEventBus()) {
            EventBusManager.getInstance().register(this);
        }
        ResumeRefreshUtils.clearPageRefresh(getClass());
    }

    protected void setStatusBar() {
        StatusBarUtil.setLightMode(this);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, android.R.color.white), 0);
    }

    protected boolean useEventBus() {
        return false;
    }
    /**
     * 是否添加点击事件防止双击代理
     * */
    protected boolean useNotDoubleClickListener() {
        return true;
    }



    /**
     * 初始化数据等
     * */
    protected abstract void init(@Nullable Bundle savedInstanceState);

    /**
     * 添加事件响应
     * */
    protected abstract void setEvent();


    protected void showToast(String message) {
        WeakReference<Context> weakReference = new WeakReference<Context>(this);
        Context contextW = weakReference.get();
        ToastUtils.showToast(contextW, message);
    }
    public void showErrorDialog(final String message) {
        if (BaseCompatUtils.isMainThread()) {
            NetErrorDialog.showErrorDialog(getSupportFragmentManager(),message);
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    NetErrorDialog.showErrorDialog(getSupportFragmentManager(),message);
                }
            });
        }
    }

    public void closeErrorDialog() {
        if (BaseCompatUtils.isMainThread()) {
            NetErrorDialog.closeErrorDialog();
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    NetErrorDialog.closeErrorDialog();
                }
            });
        }
    }
    protected void showLoading(final String message) {
        if (BaseCompatUtils.isMainThread()) {
            MyProgressDialog.showLoadDialog(mContext, false, message);
        } else {
            runOnUiThread(new Runnable() {
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
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    MyProgressDialog.dismissDialog();
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 这个页面有刷新的情况下，调用刷新方法
        if (ResumeRefreshUtils.getIsPageRefresh(getClass())) {
            refreshMet();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        if (isFirstShow){
            isFirstShow = false;
            canGetViewInfo();
        }
    }


    /**
     * 在这里可以获取控件的信息
     * */
    protected void canGetViewInfo(){

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().removeActivity(this);
        if (useEventBus()) {
            EventBusManager.getInstance().unregister(this);
        }
        RefreshUtils.callActivityInterface(mContext, false, null);
        //线程归属页面 当页面退出时，线程终止并清空
//        ThreadManager.onDestroy(TAG);
    }

    protected <T> AutoDisposeConverter<T> bindAutoDispose() {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider
                .from(this, Lifecycle.Event.ON_DESTROY));
    }

    /***************************************
     ******     页面刷新方法   **************
     **************************************/
    protected static void setPageRefresh(Class<?> pClass) {
        ResumeRefreshUtils.setPageRefresh(pClass);
    }

    private void refreshMet() {

    }

    protected void setActivityInterface(Class<?> callActivity,
                                        ActivityCallbackInterface callback, boolean isManuallyCall) {
        RefreshUtils.setActivityInterface(mContext, callActivity, callback, isManuallyCall);
    }

    protected void callActivityInterface() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RefreshUtils.callActivityInterface(mContext, true, null);
            }
        });
    }

    protected void callActivityInterface(final Object obj) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                RefreshUtils.callActivityInterface(mContext, true, obj);
            }
        });
    }
    /**
     * 获取handler
     * */
    public Handler getHandler(){
        return BaseApplication.getHandler();
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
