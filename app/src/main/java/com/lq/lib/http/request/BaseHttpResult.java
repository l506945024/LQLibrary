package com.lq.lib.http.request;

/**
 * description:
 *
 * @author:mick
 * @time:2018/10/18
 */
public class BaseHttpResult<T> {
    private String msg;
    private String code;
    private T t;
    private boolean isSuccess;

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
