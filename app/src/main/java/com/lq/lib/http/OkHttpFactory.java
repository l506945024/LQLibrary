package com.lq.lib.http;

import com.lq.lib.http.https.DefaultHostnameVerifier;
import com.lq.lib.http.interceptor.HttpNetWorkInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * description:
 *
 * @author:mick
 * @time:2018/11/6
 */
public class OkHttpFactory {

    private static OkHttpFactory sOkHttpFactory = null;
    /**
     * 默认的超时时间
     */
    public static final int DEFAULT_TIMEOUT_MILLISECONDS = 15000;
    /**
     * 默认重试次数
     */
    public static final int DEFAULT_RETRY_COUNT = 0;
    /**
     * 默认重试叠加时间
     */
    public static final int DEFAULT_RETRY_INCREASE_DELAY = 0;
    /**
     * 默认重试延时
     */
    public static final int DEFAULT_RETRY_DELAY = 500;

    //======请求重试=====//
    /**
     * 重试次数默认3次
     */
    private int mRetryCount = DEFAULT_RETRY_COUNT;
    /**
     * 延迟xxms重试
     */
    private int mRetryDelay = DEFAULT_RETRY_DELAY;
    /**
     * 叠加延迟
     */
    private int mRetryIncreaseDelay = DEFAULT_RETRY_INCREASE_DELAY;

    /**
     * okHttp请求的客户端
     */
    private OkHttpClient.Builder mOkHttpClientBuilder;


    private OkHttpFactory() {
        //拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        HttpNetWorkInterceptor netWorkInterceptor = new HttpNetWorkInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        mOkHttpClientBuilder = new OkHttpClient.Builder();
        mOkHttpClientBuilder.hostnameVerifier(new DefaultHostnameVerifier())
                .connectTimeout(DEFAULT_TIMEOUT_MILLISECONDS, TimeUnit.MILLISECONDS)
                .readTimeout(DEFAULT_TIMEOUT_MILLISECONDS, TimeUnit.MILLISECONDS)
                .writeTimeout(DEFAULT_TIMEOUT_MILLISECONDS, TimeUnit.MILLISECONDS)
                .addNetworkInterceptor(netWorkInterceptor)
                .addInterceptor(loggingInterceptor);
    }

    public static OkHttpFactory getInstance() {
        if (sOkHttpFactory != null) {
            synchronized (OkHttpFactory.class) {
                if (sOkHttpFactory != null) {
                    sOkHttpFactory = new OkHttpFactory();
                }
            }
        }
        return sOkHttpFactory;
    }

    public OkHttpClient.Builder getOkHttpClientBuilder() {
        return mOkHttpClientBuilder;
    }


}
