package com.xinlan.android.basesupport.net.http;


import com.xinlan.android.basesupport.base.BaseApplication;
import com.xinlan.android.basesupport.net.ApiConstants;
import com.xinlan.android.basesupport.util.LogUtils;
import com.xinlan.android.basesupport.util.SPUtils;

import org.jetbrains.annotations.NotNull;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;


public class OkHttpHalo {
    private static OkHttpClient sInstance;

    public static OkHttpClient getInstance() {
        if (sInstance == null) {
            synchronized (OkHttpHalo.class) {
                if (sInstance == null) {
                    sInstance = createClient(null);
                }
            }
        }
        return sInstance;
    }

    public static OkHttpClient getInstance(Interceptor interceptor) {
        if (sInstance == null) {
            synchronized (OkHttpHalo.class) {
                if (sInstance == null) {
                    sInstance = createClient(interceptor);
                }
            }
        }
        return sInstance;
    }

    public static OkHttpClient createClient(Interceptor interceptor) {
        if (null == interceptor){
            interceptor = chain -> {
                Request original = chain.request();
                Request.Builder requestBuilder = original.newBuilder()
                        .header("token", SPUtils.getString(BaseApplication.getAppContext(),"token"));
                Request request = requestBuilder.build();
                return chain.proceed(request);
            };
        }
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addNetworkInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(@NotNull String s) {
                        LogUtils.d(s);
                    }
                }).setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(ApiConstants.TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(ApiConstants.TIME_OUT, TimeUnit.SECONDS)
                .build();
    }
}
