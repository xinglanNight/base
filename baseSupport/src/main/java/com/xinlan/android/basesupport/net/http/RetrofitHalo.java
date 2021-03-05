package com.xinlan.android.basesupport.net.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xinlan.android.basesupport.net.ApiConstants;

import okhttp3.Interceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHalo {
    private static Retrofit sInstance;

    private static Retrofit sFileInstance;

    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").serializeNulls().create();

    public static Retrofit getRxInstance(Interceptor interceptor) {
        if (sInstance == null){
            synchronized (RetrofitHalo.class){
                if (sInstance == null){
                    sInstance = createRxRetrofit(interceptor);
                }
            }
        }
        return sInstance;
    }

    public static Retrofit getRxFileInstance(Interceptor interceptor) {
        if (sFileInstance == null){
            synchronized (RetrofitHalo.class){
                if (sFileInstance == null){
                    sFileInstance = createRxFileRetrofit(interceptor);
                }
            }
        }
        return sFileInstance;
    }


    private static Retrofit createRxRetrofit(Interceptor interceptor){
        return new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .client(OkHttpHalo.getInstance())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }

    private static Retrofit createRxFileRetrofit(Interceptor interceptor){
        return new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_FILE_URL)
                .client(OkHttpHalo.getInstance(interceptor))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }
    public static Retrofit getInstance(Interceptor interceptor) {
        if (sInstance == null){
            synchronized (RetrofitHalo.class){
                if (sInstance == null){
                    sInstance = createRetrofit(interceptor);
                }
            }
        }
        return sInstance;
    }

    private static Retrofit createRetrofit(Interceptor interceptor){
        return new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .client(OkHttpHalo.getInstance(interceptor))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

}
