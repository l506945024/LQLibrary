package com.lq.lib.http.transformer;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * description:
 * 避免重复的写一般情况下的线程调度
 *
 * @author mick
 * @date 2019/6/12
 */
public class HttpSchedulersTransformer<T> implements ObservableTransformer<T,T> {

    @Override
    public ObservableSource<T> apply(Observable<T> upstream) {
        return upstream.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
    }
}
