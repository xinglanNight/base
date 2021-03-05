package com.xinlan.android.basesupport.net.api;



import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface RxRetrofitService {

    @POST
    Observable<Response<ResponseBody>> postUrl(@Url String api, @Body RequestBody body);

    @GET
    Observable<Response<ResponseBody>> get(@Url String api, @QueryMap Map<String, Integer> params);

    @GET
    Observable<Response<ResponseBody>> getWithString(@Url String api, @QueryMap Map<String, String> params);

    @GET
    Observable<Response<ResponseBody>> get(@Url String api, @QueryMap Map<String, Integer> params, @Query("keywords") String keywords);

    @GET
    Observable<Response<ResponseBody>> getSearchKey(@Url String api, @QueryMap Map<String, Integer> params, @Query("searchKey") String keywords);

    @GET
    Observable<Response<ResponseBody>> download(@Url String api);
}
