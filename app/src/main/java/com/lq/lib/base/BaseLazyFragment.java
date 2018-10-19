package com.lq.lib.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * description:
 * 基类懒加载的fragment
 *
 * @author:mick
 * @time:2018/10/19
 */
public abstract class BaseLazyFragment extends BaseFragment {
    /**
     * 是否初始化过布局
     */
    protected boolean isViewInit;
    /**
     * 当前界面是否可见
     */
    protected boolean isVisibleToUser;
    /**
     * 是否加载过数据
     */
    protected boolean isDataInit;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mContentView != null) {
            isViewInit = true;
            prepareFetchData();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (isVisibleToUser) {
            prepareFetchData();
        }
    }

    /**
     * 懒加载
     */
    public abstract void fetchData();

    /**
     * 懒加载控制
     * 重写此方法改变方法里的参数配置是否懒加载
     * <p>
     * prepareFetchData(false);每次都加载(不进行懒加载)
     * prepareFetchData(true);只加载一次(懒加载)
     */
    public void prepareFetchData() {
        prepareFetchData(false);
    }

    /**
     * 判断懒加载条件
     *
     * @param forceUpdate 强制更新，好像没什么用？
     *                    true 每次都加载
     *                    false 只加载一次
     */
    public void prepareFetchData(boolean forceUpdate) {
        boolean canFetchData = isVisibleToUser && isViewInit && (!isDataInit || forceUpdate);
        if (canFetchData) {
            fetchData();
            isDataInit = true;
        }
    }
}
