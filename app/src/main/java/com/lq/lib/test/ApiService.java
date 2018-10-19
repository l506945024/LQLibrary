package com.lq.lib.test;

import com.lq.lib.http.request.BaseHttpResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * description:
 *
 * @author:mick
 * @time:2018/10/18
 */
public interface ApiService {
    @GET("")
    Observable<BaseHttpResult<Object>> login(@Query("start") int start);
}
