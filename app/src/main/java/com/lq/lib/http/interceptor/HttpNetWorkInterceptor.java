package com.lq.lib.http.interceptor;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.lq.lib.http.Api;

import java.io.IOException;


import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author David
 *         网络监听器
 */

public class HttpNetWorkInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder();
//        //所有的请求都必须加上key
//        String signKey = System.currentTimeMillis() + "";
//        builder.header("sign_key", signKey);
//        if (!NetUtils.checkNetWork(Api.getContext())) {
//            request = builder
//                    //无网络尝试用缓存数据
//                    .header("Authorization", UserInfoManager.getInstance().getUserToken())
//                    .cacheControl(CacheControl.FORCE_CACHE)
//                    .build();
//            Logger.e("no net work!");
//        } else {
//            //有网络必须用网络数据
//            builder.cacheControl(CacheControl.FORCE_NETWORK);
//            if (TextUtils.isEmpty(UserInfoManager.getInstance().getUserToken())) {
//                request = builder.build();
//            } else {
//                request = builder.header("Authorization", UserInfoManager.getInstance().getUserToken()).build();
//            }
//        }
        Response originalResponse = chain.proceed(request);
//        if (NetUtils.checkNetWork(BaseApplication.getInstance())) {
            //有网的时候读接口上的@Headers里的配置，可以在这里进行统一的设置
            String cacheControl = request.cacheControl().toString();
            return originalResponse.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .removeHeader("Pragma")
                    .build();
//        } else {
//            return originalResponse.newBuilder()
//                    .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
//                    .removeHeader("Pragma")
//                    .build();
//        }
    }
}
