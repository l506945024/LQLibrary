package com.lq.lib.http.request;

/**
 * description:
 *
 * @author:mick
 * @time:2018/10/18
 */
public interface IBaseHttpResultListener {
    /**
     * 请求成功
     */
    public void onHttpSuccess(BaseHttpResult result);

    /**
     * 请求失败
     */
    public void onHttpError(BaseHttpResult result);
}
