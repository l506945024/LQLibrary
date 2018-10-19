package com.lq.lib.http;

import android.app.Application;
import android.content.Context;

import com.google.gson.GsonBuilder;
import com.lq.lib.http.https.DefaultHostnameVerifier;
import com.lq.lib.http.https.HttpsUtils;
import com.lq.lib.http.interceptor.HttpNetWorkInterceptor;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * description:
 *
 * @author:mick
 * @time:2018/10/17
 */
public class Api {
    private static Api ourInstance;
    private static Application sContext;
    private static String sBaseUrl = "";
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
    /**
     * Retrofit请求Builder
     */
    private Retrofit.Builder mRetrofitBuilder;

    public static Api getInstance() {
        //检查是否初始化
        testInitialize();
        if (ourInstance == null) {
            synchronized (Api.class) {
                if (ourInstance == null) {
                    ourInstance = new Api();
                }
            }
        }
        return ourInstance;
    }

    private Api() {
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

        mRetrofitBuilder = new Retrofit.Builder();
        mRetrofitBuilder.addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .setLenient()
                        .create()
                ))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(mOkHttpClientBuilder.build())
                .baseUrl(sBaseUrl);
    }

    public void setBaseUrl(String baseUrl) {
        sBaseUrl = baseUrl;
    }

    /**
     * 必须在全局Application先调用，获取context上下文，否则缓存无法使用
     */
    public static void init(Application app,String baseUrl) {
        sContext = app;
        sBaseUrl = baseUrl;
    }

    /**
     * 获取全局上下文
     */
    public static Context getContext() {
        testInitialize();
        return sContext;
    }

    private static void testInitialize() {
        if (sContext == null) {
            throw new ExceptionInInitializerError("请先在全局Application中调用 XHttp.init() 初始化！");
        }
    }

    //==================设置Retrofit的OkHttpClient、ConverterFactory、CallAdapterFactory、CallbackExecutor、CallFactory=====================//
    /**
     * 对外暴露 Retrofit,方便自定义
     */
    public static Retrofit.Builder getRetrofitBuilder() {
        return getInstance().mRetrofitBuilder;
    }

    //==================https规则设置=====================//

    /**
     * https的全局访问规则
     */
    public Api setHostnameVerifier(HostnameVerifier hostnameVerifier) {
        mOkHttpClientBuilder.hostnameVerifier(hostnameVerifier);
        return this;
    }

    /**
     * https的全局自签名证书
     */
    public Api setCertificates(InputStream... certificates) {
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, certificates);
        mOkHttpClientBuilder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
        return this;
    }

    /**
     * https双向认证证书
     */
    public Api setCertificates(InputStream bksFile, String password, InputStream... certificates) {
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(bksFile, password, certificates);
        mOkHttpClientBuilder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
        return this;
    }
}
