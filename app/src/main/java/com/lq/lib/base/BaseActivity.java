package com.lq.lib.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.lq.lib.util.ActivityUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * description:
 * <p>
 * 基类Activity
 *
 * @author:mick
 * @time:2018/10/18
 */
public abstract class BaseActivity extends AppCompatActivity {
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBeforeSetContentView(savedInstanceState);
        setContentView(getLayoutResId(savedInstanceState));
        //ButterKnife初始化
        mUnbinder = ButterKnife.bind(this);
        ActivityUtil.getActivityUtil().addActivity(this);
        initView(savedInstanceState);
        initTitleBar(savedInstanceState);
        initData(savedInstanceState);
    }

    //=========================初始化=========================//

    /**
     * setContentView 前的一些初始化操作
     *
     * @param savedInstanceState
     */
    public void initBeforeSetContentView(Bundle savedInstanceState) {
    }

    /**
     * 页面加载的布局id
     *
     * @param savedInstanceState
     * @return
     */
    public abstract int getLayoutResId(Bundle savedInstanceState);

    /**
     * 初始化标题栏
     */
    public abstract void initTitleBar(Bundle savedInstanceState);

    /**
     * 控件初始化
     *
     * @param savedInstanceState
     */
    public abstract void initView(Bundle savedInstanceState);

    /**
     * 数据初始化
     *
     * @param savedInstanceState
     */
    public abstract void initData(Bundle savedInstanceState);

    //=========================初始化结束=========================//

    @Override
    protected void onDestroy() {
        //解除绑定，官方文档只对fragment做了解绑
        mUnbinder.unbind();
        ActivityUtil.getActivityUtil().finishActivity(this);

        super.onDestroy();
    }

    /**
     * 关闭当前页面
     */
    public void finishActivity() {
        ActivityUtil.getActivityUtil().finishActivity(this);
        finish();
    }

    /**
     * 通过类关闭指定页面
     *
     * @param cls
     */
    public void finishActivity(Class<?> cls) {
        ActivityUtil.getActivityUtil().finishActivity(cls);
    }

    //============MainActivity双击退出页面代码开始========//
    //    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN
//                && event.getRepeatCount() == 0) {
//            if ((System.currentTimeMillis() - exitTime) > 2000) {
//                ToastUtilsBase.showToast("再按一次退出程序");
//                exitTime = System.currentTimeMillis();
//            } else {
//                AppManager.getAppManager().finishAllActivity();
//                exitSystem();
//            }
//            return false;
//        } else {
//            return super.dispatchKeyEvent(event);
//        }
//    }
//    /**
//     * 退出系统
//     *
//     */
//    protected void exitSystem() {
//        Intent startMain = new Intent(Intent.ACTION_MAIN);
//        startMain.addCategory(Intent.CATEGORY_HOME);
//        startMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(startMain);
//        System.exit(0);
//    }

    //============MainActivity双击退出页面代码结束========//
}
