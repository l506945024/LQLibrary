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
        mOkHttpClientBuilder = OkHttpFactory.getInstance().getOkHttpClientBuilder();
        mRetrofitBuilder = RetrofitFactory.getInstance().getRetrofitBuilder();
        mRetrofitBuilder.client(mOkHttpClientBuilder.build())
                        .baseUrl(sBaseUrl);
    }

    /**
     * 必须在全局Application先调用，获取context上下文，否则缓存无法使用
     */
    protected static void init(Application app, String baseUrl) {
        sContext = app;
        sBaseUrl = baseUrl;
    }

    /**
     * 获取全局上下文
     */
    public  Context getContext() {
        testInitialize();
        return sContext;
    }

    private  static void testInitialize() {
        if (sContext == null) {
            throw new ExceptionInInitializerError("请先在全局Application中调用 ApiProxy.init() 初始化！");
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
