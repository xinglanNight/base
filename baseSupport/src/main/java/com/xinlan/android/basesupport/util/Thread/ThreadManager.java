package com.xinlan.android.basesupport.util.Thread;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.xinlan.android.basesupport.constant.BaseConstants;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;


/**
 * @author: 63239
 * @date: 2020/12/14
 * 线程管理者
 */
public class ThreadManager {
    private static final String ERROR_MESSAGE = "线程发生错误";

    /**
     * 执行中线程
     * */
    private static Hashtable<Integer,Thread> executeThreadMap = new Hashtable<>(16);
    /**
     * 缓存待执行线程
     * */
    private static Hashtable<Integer,Runnable> cacheThreadMap = new Hashtable<>(16);


    private static Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            ThreadCallBack callBack = (ThreadCallBack) msg.getData().getSerializable("callback");
            if (null == callBack){
                return;
            }
            int taskId = msg.getData().getInt("taskId");
            //执行完毕移除列表

            switch (msg.arg1) {
                case BaseConstants.ACTION_SUCCESS:
                    callBack.onCallbackFromThread(taskId);
                    break;
                case BaseConstants.ACTION_EXCEPTION:
                    if (msg.obj instanceof Exception) {
                        callBack.onCallbackFromThreadWithFail(taskId, ((Exception) msg.obj));
                    } else {
                        callBack.onCallbackFromThreadWithFail(taskId, new Exception(ERROR_MESSAGE));
                    }
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 执行任务
     *
     * @param callBack   回调方法
     * @param taskId     任务ID
     */
    public static void exeTask(final String tag, final ThreadCallBack callBack, final int taskId, final ThreadRun threadRun) {
        ThreadPoolManager threadPoolManager = ThreadPoolManager.getInstance();
        Log.e("提示","开始执行");
        Runnable runnable = new Runnable(){
            @Override
            public void run() {
                Thread.currentThread().setName(tag);
                executeThreadMap.put(taskId,Thread.currentThread());
                cacheThreadMap.remove(taskId);
                Log.e("提示","正在执行");
                Message msg = new Message();
                try {
                    threadRun.onThreadRun(taskId);
                    msg.arg1 = BaseConstants.ACTION_SUCCESS;
                } catch (Exception e) {
                    msg.obj = e;
                    msg.arg1 = BaseConstants.ACTION_EXCEPTION;
                }
                msg.getData().putInt("taskId", taskId);
                msg.getData().putSerializable("callback", callBack);
                handler.sendMessage(msg);
                executeThreadMap.remove(taskId);
            }
        };
        cacheThreadMap.put(taskId,runnable);
        threadPoolManager.execute(runnable);
    }
    /**
     * 销毁线程回调
     * */
    public static void onDestroy(String tag){
        Log.e("提示","开始销毁");
        handler.removeCallbacksAndMessages(null);
        ThreadPoolManager threadPoolManager = ThreadPoolManager.getInstance();
        Set<Integer> cacheKeys = new HashSet<>(cacheThreadMap.keySet());
        for (int key : cacheKeys){
            threadPoolManager.remove(cacheThreadMap.get(key));
        }
        Set<Integer> executeKeys = new HashSet<>(executeThreadMap.keySet());
        for (int key : executeKeys) {
            Thread thread = executeThreadMap.get(key);
            if (null != thread && tag.equals(thread.getName())) {
                thread.interrupt();
            }
        }
    }

}
