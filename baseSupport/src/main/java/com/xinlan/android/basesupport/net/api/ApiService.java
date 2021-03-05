package com.xinlan.android.basesupport.net.api;

import com.xinlan.android.basesupport.net.http.RetrofitHalo;

import okhttp3.Interceptor;

public class ApiService {
    //Rx
    private static RxRetrofitService sRxInstance;
    //单纯retrofit
    private static RxRetrofitService sFileInstance;
    //okhttp 过滤器
    private static Interceptor interceptor;


    public static RxRetrofitService getRxFileService(){
        if (sFileInstance == null){
            synchronized (ApiService.class){
                if (sFileInstance == null){
                    sFileInstance = RetrofitHalo.getRxFileInstance(interceptor).create(RxRetrofitService.class);
                }
            }
        }
        return sFileInstance;
    }

    public static RxRetrofitService getRxService(){
        if (sRxInstance == null){
            synchronized (ApiService.class){
                if (sRxInstance == null){
                    sRxInstance = RetrofitHalo.getRxInstance(interceptor).create(RxRetrofitService.class);
                }
            }
        }
        return sRxInstance;
    }


    public static Interceptor getInterceptor() {
        return interceptor;
    }
    /**
     * 可以自定义设置okhttp 过滤器 但是只有第一次使用okhttp时设置有效
     * 在执行网络请求后设置无效
     * */
    public static void setInterceptor(Interceptor interceptor) {
        ApiService.interceptor = interceptor;
    }
}
