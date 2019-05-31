package com.ck.project.utilmodule.wedgit;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
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
    private TextView dialogContent;
    private Button dialogCommit,dialogCancle;
    public CustomDialog(@NonNull Context context) {
        super(context);
    }

    public CustomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CustomDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_custom);
        dialogContent = findViewById(R.id.dialog_content);
        dialogCommit = findViewById(R.id.dialog_commit);
        dialogCancle = findViewById(R.id.dialog_cancle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public void setDialogContent(String content){
        if (content != null) {
            dialogContent.setText(content);
        }
    }

    public void setOnCommitClickListener(OnCommitClickListener commitClickListener){
        setOnCommitClickListener(null,null,commitClickListener,null);
    }

    public void setOnCommitClickListener(String commit,String cancle,OnCommitClickListener commitClickListener){
        setOnCommitClickListener(commit,cancle,commitClickListener,null);
    }

    public void setOnCommitClickListener(String commit, String cancle, final OnCommitClickListener commitClickListener, final OnCancleClickListener cancleClickListener){
        if(!TextUtils.isEmpty(commit)){
            dialogCommit.setText(commit);
        }
        if(!TextUtils.isEmpty(cancle)){
            dialogCancle.setText(cancle);
        }
        dialogCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (commitClickListener != null) {
                    commitClickListener.onCommitClick();
                }
            }
        });
        dialogCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cancleClickListener != null) {
                    cancleClickListener.onCancleClick();
                }
                dismiss();
            }
        });
    }

    @Override
    public void show() {
        super.show();
        //设置大小
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = (int) ((int) DisplayUtil.getScreenParams().first*0.7);
        getWindow().setAttributes(params);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dismiss();
    }

    public interface OnCommitClickListener{
        void onCommitClick();
    }

    public interface OnCancleClickListener{
        void onCancleClick();
    }
}
