package com.lq.lib.http;

import com.lq.lib.http.https.DefaultHostnameVerifier;
import com.lq.lib.http.interceptor.CacheInterceptor;
import com.lq.lib.http.interceptor.HttpLoggingInterceptor;

import java.io.File;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;

/**
 * description:
 *
 * @author:mick
 * @time:2018/11/6
 */
public class OkHttpFactory {
    /**
     * 默认tag
     */
    public final static String DEFAULT_LOG_TAG = "[KHttp]";

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

    //缓存大小 200Mb
    private static final long CACHE_SIZE = 1024 * 1024 * 200;           //缓存大小 200Mb
    public static String HTTP_CACHE_PATH = "httpCache";//http缓存

    private OkHttpFactory() {

        mOkHttpClientBuilder = new OkHttpClient.Builder();

        //缓存
        File cacheFile = new File(Api.getContext().getCacheDir(), HTTP_CACHE_PATH);
        //创建缓存目录
        createFile(cacheFile);
        Cache cache = new Cache(cacheFile, CACHE_SIZE);

        mOkHttpClientBuilder.hostnameVerifier(new DefaultHostnameVerifier())
                .connectTimeout(DEFAULT_TIMEOUT_MILLISECONDS, TimeUnit.SECONDS)
                //读取超时
                .readTimeout(DEFAULT_TIMEOUT_MILLISECONDS, TimeUnit.SECONDS)
                //写入超时
                .writeTimeout(DEFAULT_TIMEOUT_MILLISECONDS, TimeUnit.SECONDS)
                //有网络时的拦截器
                .addNetworkInterceptor(CacheInterceptor.REWRITE_RESPONSE_INTERCEPTOR)
                //没网络时的拦截器
                .addInterceptor(CacheInterceptor.REWRITE_RESPONSE_INTERCEPTOR_OFFLINE)
                //日志拦截器
                .addInterceptor(new HttpLoggingInterceptor(OkHttpFactory.DEFAULT_LOG_TAG).setLevel(HttpLoggingInterceptor.Level.BODY))
//                //统一添加请求头
//                .addInterceptor(InterceptorUtils.getResponseHeader())
                //缓存目录和大小
                .cache(cache)
                //允许http重定向
                .followRedirects(true)
                //禁止使用代理
                .proxy(Proxy.NO_PROXY)
                // 连接失败后是否重新连接
                .retryOnConnectionFailure(true);
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

    /**
     * 强行创建文件夹
     *
     * @param file
     */
    public void createFile(File file) {
        if (!file.exists())
            file.mkdirs();
        else if (!file.isDirectory() && file.canWrite()) {
            file.delete();
            file.mkdirs();
        }
    }
}
