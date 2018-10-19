package com.lq.lib.http;

import com.lq.lib.http.request.BaseHttpResult;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * description:
 *
 * @author:mick
 * @time:2018/10/18
 */
public class ApiProxy {
    private static ApiProxy instance = null;

    private ApiProxy(){}

    public static ApiProxy getInstance() {
        if (instance == null) {
            synchronized (ApiProxy.class){
                if (instance ==null) {
                    instance = new ApiProxy();
                }
            }
        }
        return instance;
    }

    public static <T> T getService(Class<T> cls) {
        return Api.getRetrofitBuilder().build().create(cls);
    }

    public <T> Observable<BaseHttpResult<T>> call(Observable<BaseHttpResult<T>> observable) {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
