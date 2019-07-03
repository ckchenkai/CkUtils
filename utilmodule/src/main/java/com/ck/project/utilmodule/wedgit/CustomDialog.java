package com.ck.project.utilmodule.wedgit;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ck.project.utilmodule.R;
import com.ck.project.utilmodule.utils.DisplayUtil;

/**
 * Created by CK on 2018/6/4.
 * Email:910663958@qq.com
 * 自定义的通用dialog
 */

public class CustomDialog extends Dialog {
    private RelativeLayout container;
    private TextView dialogContent;
    private Button dialogCommit, dialogCancle;

    public CustomDialog(@NonNull Context context) {
        super(context);
    }

    public CustomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CustomDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public CustomDialog setContent(String content) {
        if (dialogContent != null) {
            dialogContent.setText(content);
        }
        return this;
    }

    public static class Builder {
        private Context mContext;
        private String mContent;
        private String mTextCommit;
        private String mTextCancle;
        private OnCommitClickListener mOnCommitClickListener;
        private OnCancleClickListener mOnCancleClickListener;
        private OnKeyBackListener mOnKeyBackListener;
        private boolean isCancleable = true;
        private boolean isHideCancleBtn;
        private double mWidthRadio;

        public Builder(Context mContext) {
            this.mContext = mContext;
        }

        /**
         * 设置内容
         *
         * @param content
         * @return
         */
        public Builder setContent(String content) {
            mContent = content;
            return this;
        }

        /**
         * 设置确定按钮监听
         *
         * @param mOnCommitClickListener
         * @return
         */
        public Builder setCommitListener(OnCommitClickListener mOnCommitClickListener) {
            this.mOnCommitClickListener = mOnCommitClickListener;
            return this;
        }

        /**
         * 设置取消按钮监听
         *
         * @param mOnCancleClickListener
         * @return
         */
        public Builder setCancleListener(OnCancleClickListener mOnCancleClickListener) {
            this.mOnCancleClickListener = mOnCancleClickListener;
            return this;
        }

        /**
         * 设置确定按钮监听+文字
         *
         * @param mTextCommit
         * @param mOnCommitClickListener
         * @return
         */
        public Builder setCommitListener(String mTextCommit, OnCommitClickListener mOnCommitClickListener) {
            this.mTextCommit = mTextCommit;
            this.mOnCommitClickListener = mOnCommitClickListener;
            return this;
        }

        /**
         * 设置取消按钮监听+文字
         *
         * @param mOnCancleClickListener
         * @return
         */
        public Builder setCancleListener(String mTextCancle, OnCancleClickListener mOnCancleClickListener) {
            this.mTextCancle = mTextCancle;
            this.mOnCancleClickListener = mOnCancleClickListener;
            return this;
        }

        /**
         * 设置返回键监听
         *
         * @param mOnKeyBackListener
         * @return
         */
        public Builder setOnKeyBackListener(OnKeyBackListener mOnKeyBackListener) {
            this.mOnKeyBackListener = mOnKeyBackListener;
            return this;
        }

        /**
         * 设置点击外部是否可消失
         *
         * @param isCancleable
         * @return
         */
        public Builder setCancleable(boolean isCancleable) {
            this.isCancleable = isCancleable;
            return this;
        }

        /**
         * 设置点击是否隐藏取消按钮
         *
         * @param isHideCancleBtn
         * @return
         */
        public Builder setHideCancleBtn(boolean isHideCancleBtn) {
            this.isHideCancleBtn = isHideCancleBtn;
            return this;
        }

        public Builder setWidthRadio(double mWidthRadio){
            this.mWidthRadio = mWidthRadio;
            return this;
        }

        public CustomDialog create() {
            CustomDialog dialog = new CustomDialog(mContext, R.style.custom_dialog);
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View view = inflater.inflate(R.layout.dialog_custom, null);
            dialog.container = view.findViewById(R.id.container);
            dialog.dialogContent = view.findViewById(R.id.dialog_content);
            dialog.dialogCommit = view.findViewById(R.id.dialog_commit);
            dialog.dialogCancle = view.findViewById(R.id.dialog_cancle);
            dialog.dialogContent.setText(mContent);
            if (mTextCommit != null) {
                dialog.setContent(mTextCommit);
            }
            if (mTextCancle != null) {
                dialog.dialogCancle.setText(mTextCancle);
            }
            dialog.dialogCommit.setOnClickListener(v -> {
                dialog.dismiss();
                if (mOnCommitClickListener != null) {
                    mOnCommitClickListener.onCommit();
                }
            });
            dialog.dialogCancle.setOnClickListener(v -> {
                dialog.dismiss();
                if (mOnCancleClickListener != null) {
                    mOnCancleClickListener.onCancle();
                }
            });
            dialog.setContentView(view);
            dialog.setCancelable(isCancleable);
            dialog.setCanceledOnTouchOutside(isCancleable);
            dialog.dialogCancle.setVisibility(isHideCancleBtn ? View.INVISIBLE : View.VISIBLE);
            if (mOnKeyBackListener != null) {
                dialog.setOnKeyListener((dialog1, keyCode, event) -> {
                    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                        mOnKeyBackListener.onKeyBack(dialog);
                    }
                    return false;
                });
            }
            if(mWidthRadio>0&&mWidthRadio<1){
                WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
                params.width = (int) (DisplayUtil.getScreenWidth(mContext)*mWidthRadio);
                dialog.getWindow().setAttributes(params);
            }
            return dialog;
        }
    }

    public interface OnCommitClickListener {
        void onCommit();
    }

    public interface OnCancleClickListener {
        void onCancle();
    }

    public interface OnKeyBackListener {
        void onKeyBack(CustomDialog view);
    }
}
