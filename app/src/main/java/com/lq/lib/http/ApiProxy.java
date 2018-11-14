package com.lq.lib.http;

import android.app.Application;

import com.lq.lib.http.request.BaseHttpResult;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * description:
 * 提供给外部使用的类
 *
 * @author:mick
 * @time:2018/10/18
 */
public class ApiProxy {
    private static ApiProxy instance = null;

    private ApiProxy() {
    }

    public static ApiProxy getInstance() {
        if (instance == null) {
            synchronized (ApiProxy.class) {
                if (instance == null) {
                    instance = new ApiProxy();
                }
            }
        }
        return instance;
    }

    /**
     * 初始化
     *
     * @param app
     * @param baseUrl
     */
    public static void init(Application app, String baseUrl) {
        Api.init(app, baseUrl);
    }

    public static <T> T getService(Class<T> cls) {
        return Api.getRetrofitBuilder().build().create(cls);
    }

    public  Observable<BaseHttpResult> call(Observable<BaseHttpResult> observable) {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
