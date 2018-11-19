package com.lq.lib.util;

import android.content.Context;

import com.lq.lib.R;
import com.lq.lib.widgets.dialog.AkDialog;

/**
 * description:
 *
 * @author:mick
 * @time:2018/11/19
 */
public class AkDialogUtils {
    static AkDialogUtils instance = null;

    public static AkDialogUtils getInstance() {
        if (instance == null) {
            synchronized (AkDialogUtils.class){
                if (instance == null) {
                    instance = new AkDialogUtils();
                }
            }
        }
        return instance;
    }

    private AkDialogUtils(){}

    public AkDialog showLoadingDialog(Context context,String msg){
        return new AkDialog.Builder(context,R.layout.dialog_loading)
                .setStyle(R.style.loading_dialog_no_shadow)
                .setCancelTouchOutSide(false)
                .setAutoShow(true)
                .setMessage(R.id.tv_tips_loading,msg)
                .build();
    }
}
