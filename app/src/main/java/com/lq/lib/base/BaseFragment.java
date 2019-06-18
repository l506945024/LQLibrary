package com.lq.lib.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * description:
 * <p>
 * 基类Fragment
 *
 * @author:mick
 * @time:2018/10/18
 */
public abstract class BaseFragment extends Fragment {
    private static final String SKIP_FLAG = "bundle";

    public View mContentView;
    public Context mContext;
    private Unbinder mUnbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (getLayoutResId() != 0) {
            if (mContentView == null) {
                mContentView = inflater.inflate(getLayoutResId(), null);
                mUnbinder = ButterKnife.bind(this, mContentView);
                initView(mContentView, savedInstanceState);
            }
            return mContentView;
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        mUnbinder.unbind();//解除绑定
        super.onDestroy();
    }
    //==============初始化===================//

    /**
     * 布局资源id
     *
     * @return
     */
    @LayoutRes
    protected abstract int getLayoutResId();

    /**
     * 初始化控件
     *
     * @param contentView
     * @param savedInstanceState
     */
    protected abstract void initView(View contentView, Bundle savedInstanceState);

    /**
     * 初始化数据
     *
     * @param savedInstanceState
     */
    @SuppressWarnings("unused")
    protected void initData(Bundle savedInstanceState) {
    }

    //===============初始化结束====================//

    /**
     * 使用前必须作空值判断
     *
     * @return
     */
    @Deprecated
    public BaseActivity safeGetActivity() {
        if (mContext instanceof BaseActivity) {
            BaseActivity baseActivity = (BaseActivity) mContext;
            if (baseActivity != null && !baseActivity.isFinishing() && !baseActivity.isDestroyed()) {
                return baseActivity;
            } else {
                return null;
            }
        }
        return null;
    }

    //===============跳转页面====================//

    /**
     * 简单跳转
     *
     * @param cls
     */
    protected void skipToPage(Class<?> cls) {
        mContext.startActivity(new Intent(mContext, cls));
    }

    /**
     * 传参数的跳转
     *
     * @param cls
     * @param bundle
     */
    protected void skipToPageWithBundle(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(mContext, cls);
        intent.putExtra(SKIP_FLAG, bundle);
        mContext.startActivity(intent);
    }
}
