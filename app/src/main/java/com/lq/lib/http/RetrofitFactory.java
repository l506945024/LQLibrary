package com.lq.lib.http;

import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * description:
 *
 * @author:mick
 * @time:2018/11/6
 */
public class RetrofitFactory {
    private static RetrofitFactory sRetrofitFactory = null;
    /**
     * Retrofit请求Builder
     */
    private Retrofit.Builder mRetrofitBuilder;


    private RetrofitFactory() {
        mRetrofitBuilder = new Retrofit.Builder();
        mRetrofitBuilder.addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setLenient()
                        .create()
                ))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());
    }

    public static RetrofitFactory getInstance() {
        if (sRetrofitFactory == null) {
            synchronized (RetrofitFactory.class) {
                if (sRetrofitFactory == null) {
                    sRetrofitFactory = new RetrofitFactory();
                }
            }
        }
        return sRetrofitFactory;
    }

    public Retrofit.Builder getRetrofitBuilder() {
        return mRetrofitBuilder;
    }

}
