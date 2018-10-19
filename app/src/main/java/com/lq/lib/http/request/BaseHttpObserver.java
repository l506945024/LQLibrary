package com.lq.lib.http.request;

import android.app.Dialog;

import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

/**
 * description:
 *
 * @author:mick
 * @time:2018/10/18
 */
public abstract class BaseHttpObserver<T extends BaseHttpResult> implements Observer<T>, IBaseHttpResultListener {
    Dialog loadingDialog;


    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        if (t.isSuccess()) {
            onHttpSuccess(t);
        } else {
            onHttpError(t);
        }
    }

    @Override
    public void onError(Throwable e) {
        BaseHttpResult apiResult = new BaseHttpResult();
        apiResult.setSuccess(false);
        if (e instanceof HttpException) {
            apiResult.setMsg("HTTP错误");
        } else if (e instanceof ConnectException
                || e instanceof UnknownHostException) {
            apiResult.setMsg("连接错误");
        } else if (e instanceof InterruptedIOException) {
            apiResult.setMsg("连接超时");
        } else if (e instanceof JsonParseException
                || e instanceof JSONException || e instanceof ParseException) {
            apiResult.setMsg("解析错误");
        } else {
            apiResult.setMsg("未知错误");
        }
        onHttpError(apiResult);
    }

    @Override
    public void onComplete() {

    }

    @Override
    public void onHttpError(BaseHttpResult result) {

    }
}
