package com.lq.lib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lq.lib.base.BaseActivity;
import com.lq.lib.http.ApiProxy;
import com.lq.lib.http.request.BaseHttpResult;
import com.lq.lib.http.request.BaseHttpObserver;
import com.lq.lib.test.ApiService;

public class Test extends BaseActivity {

    @Override
    public int getLayoutResId(Bundle savedInstanceState) {
        return R.layout.activity_test;
    }

    @Override
    public void initTitleBar(Bundle savedInstanceState) {

    }

    @Override
    public void initView(Bundle savedInstanceState) {

    }

    @Override
    public void initData(Bundle savedInstanceState) {
        ApiProxy.getInstance().call(ApiProxy.getService(ApiService.class).login(4))
                .subscribeWith(new BaseHttpObserver<BaseHttpResult<Object>>() {
                    @Override
                    public void onHttpSuccess(BaseHttpResult result) {

                    }
                });
    }
}
