package com.lq.lib;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Button;

import com.lq.lib.base.BaseActivity;
import com.lq.lib.http.Api;
import com.lq.lib.http.ApiProxy;
import com.lq.lib.http.request.BaseHttpObserver;
import com.lq.lib.http.request.BaseHttpResult;
import com.lq.lib.http.update.AppUpdateManager;
import com.lq.lib.test.ApiService;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class Test extends BaseActivity {

    @BindView(R.id.btn_start)
    Button btnStart;
    AppUpdateManager mUpdateManager;
    long downId;

    String downUrl = "http://192.168.88.199:8000/smartcenter/temp/201810/5c8c8b42-78cd-41d7-ad5d-0243ef4dcfda.apk";

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
                                .subscribeWith(new BaseHttpObserver<BaseHttpResult>(this,
                                        false,"") {
                                    @Override
                                    public void onHttpSuccess(BaseHttpResult result) {

                                    }
                                });
    }

    @OnClick(R.id.btn_start)
    public void onViewClicked() {
        showToastShort("这是社么东西");
        TestPermissionsDispatcher.startDownApkWithPermissionCheck(this);
    }

    @NeedsPermission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE})
    void startDownApk() {
        if (mUpdateManager == null) {
            mUpdateManager = new AppUpdateManager(this);
        }
        if (downId != 0) {
            mUpdateManager.clearCurrentTask(downId);
        }
        downId = mUpdateManager.updateApkWifi(downUrl, "靓才", "更新中...");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        TestPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
