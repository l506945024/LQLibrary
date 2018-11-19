package com.lq.lib.widgets.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * description:
 *
 * @author:mick
 * @time:2018/11/19
 */
public class AkDialog extends Dialog {
    Builder mBuilder;

    /**
     * Creates a dialog window that uses the default dialog theme.
     * <p>
     * The supplied {@code context} is used to obtain the window manager and
     * base theme used to present the dialog.
     *
     * @param builder the builder in which the dialog should run
     * @see //android.R.styleable#Theme_dialogTheme
     */
    private AkDialog(@NonNull Builder builder) {
        super(builder.context);
        this.mBuilder = builder;
    }

    /**
     * Creates a dialog window that uses a custom dialog style.
     * <p>
     * The supplied {@code context} is used to obtain the window manager and
     * base theme used to present the dialog.
     * <p>
     * The supplied {@code theme} is applied on top of the context's theme. See
     * <a href="{@docRoot}guide/topics/resources/available-resources.html#stylesandthemes">
     * Style and Theme Resources</a> for more information about defining and
     * using styles.
     *
     * @param builder    the builder in which the dialog should run
     * @param themeResId a style resource describing the theme to use for the
     *                   window, or {@code 0} to use the default dialog theme
     */
    private AkDialog(@NonNull Builder builder, int themeResId) {
        super(builder.context, builder.styleResId);
        this.mBuilder = builder;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mBuilder == null) {
            return;
        }
        setContentView(mBuilder.view);
        setCanceledOnTouchOutside(mBuilder.cancelTouchOutSide);
    }


    public static class Builder {
        private Context context;
        private boolean cancelTouchOutSide;
        private View view;
        private int styleResId;
        private String title;
        private String message;
        private AkDialog akDialog;
        private boolean autoShow;

        public Builder(Context context, @LayoutRes int layoutResId) {
            this.context = context;
            this.view = LayoutInflater.from(context).inflate(layoutResId, null, false);
            cancelTouchOutSide = true;
        }

        public Builder setStyle(@StyleRes int styleId) {
            this.styleResId = styleId;
            return this;
        }


        public Builder setAutoShow(boolean autoShow) {
            this.autoShow = autoShow;
            return this;
        }

        public Builder setCancelTouchOutSide(boolean cancelTouchOutSide) {
            this.cancelTouchOutSide = cancelTouchOutSide;
            return this;
        }

        public Builder setTitle(@IdRes int resId, String title) {
            this.title = title;

            return setViewText(resId, title);
        }

        public Builder setMessage(@IdRes int resId, String message) {
            this.message = message;
            return setViewText(resId, message);
        }

        public Builder setViewText(@IdRes int resId, String text) {
            return setViewText(resId, text, null);
        }

        private Builder setViewText(@IdRes int resId, String text, View.OnClickListener onClickListener) {
            View textView = view.findViewById(resId);
            if (onClickListener != null) {
                textView.setOnClickListener(onClickListener);
            }
            if (textView instanceof TextView) {
                if (!TextUtils.isEmpty(text)) {
                    ((TextView) textView).setText(text);
                }
            }
            return this;
        }

        public Builder addBtnOnClickListener(@IdRes int resId, String btnText, View.OnClickListener onClickListener) {
            return setViewText(resId, btnText, onClickListener);
        }

        public AkDialog build() {
            akDialog = new AkDialog(this, styleResId);
            return akDialog;
        }

    }
}
