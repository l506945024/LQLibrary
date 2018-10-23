package com.lq.lib.util;

import android.content.Context;
import android.widget.Toast;

/**
 * description:
 *
 * @author:mick
 * @time:2018/10/19
 */
public enum ToastUtil {
    /**
     * 单例
     */
    INSTANCE;

    private static Toast mToast;

    private static void showToast(Context context, String msg, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, duration);
        } else {
            mToast.setText(msg);
        }


        mToast.show();
    }

    /**
     * 短暂的显示吐司
     *
     * @param context
     * @param msg
     */
    public  void showToastShort(Context context, String msg) {
        showToast(context, msg, Toast.LENGTH_SHORT);
    }

    /**
     * 稍长时间的显示吐司
     *
     * @param context
     * @param msg
     */
    public  void showToastLong(Context context, String msg) {
        showToast(context, msg, Toast.LENGTH_LONG);
    }

}
