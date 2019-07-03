package com.example.ckutils;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.ck.project.utilmodule.utils.DisplayUtil;
import com.ck.project.utilmodule.utils.ToastUtils;
import com.ck.project.utilmodule.wedgit.CustomDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomDialogActivity extends AppCompatActivity {

    @BindView(R.id.rb_hide)
    RadioButton rbHide;
    @BindView(R.id.rb_show)
    RadioButton rbShow;
    @BindView(R.id.rg_hide_cancle)
    RadioGroup rgHideCancle;
    @BindView(R.id.rb_outside_dismiss)
    RadioButton rbOutsideDismiss;
    @BindView(R.id.rb_outside_show)
    RadioButton rbOutsideShow;
    @BindView(R.id.rg_click_outside)
    RadioGroup rgClickOutside;
    @BindView(R.id.rb_keback_no)
    RadioButton rbKebackNo;
    @BindView(R.id.rb_keyback_yes)
    RadioButton rbKeybackYes;
    @BindView(R.id.rg_keyback)
    RadioGroup rgKeyback;
    @BindView(R.id.rb_width_no)
    RadioButton rbWidthNo;
    @BindView(R.id.rb_width_yes)
    RadioButton rbWidthYes;
    @BindView(R.id.rg_width)
    RadioGroup rgWidth;
    @BindView(R.id.bt_show)
    Button btShow;


    private boolean isCancleable = true;
    private boolean isHideCancleBtn;
    private boolean isSetKeyback;
    private boolean isSetWidthRadio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_dialog);
        ButterKnife.bind(this);
        onRadioClick(rgHideCancle);
        onRadioClick(rgClickOutside);
        onRadioClick(rgKeyback);
        onRadioClick(rgWidth);
    }

    @OnClick({ R.id.bt_show})
    public void onClick(View view) {
        if (view.getId() == R.id.bt_show) {
            CustomDialog dialog = new CustomDialog.Builder(this)
                    .setCancleable(isCancleable)
                    .setHideCancleBtn(isHideCancleBtn)
                    .setContent("这是内容")
                    .setOnKeyBackListener(isSetKeyback ? onKeyListener() : null)
                    .setWidthRadio(isSetWidthRadio ? 0.5 : 0)
                    .setCommitListener(()->ToastUtils.getInstance().showToast(this,"确定"))
                    .setCancleListener(()->ToastUtils.getInstance().showToast(this,"取消"))
                    .create();
            dialog.show();

            new Handler().postDelayed(() -> dialog.setContent("内容变了"),3000);
        }
    }

    private CustomDialog.OnKeyBackListener onKeyListener() {
        return (dialog) -> {
            ToastUtils.getInstance().showToast(CustomDialogActivity.this, "返回键监听");
            dialog.dismiss();
        };
    }

    private void onRadioClick(RadioGroup radioGroup) {
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (group.getId()) {
                case R.id.rg_hide_cancle:
                    isHideCancleBtn = group.getCheckedRadioButtonId() == R.id.rb_hide ? true : false;
                    break;
                case R.id.rg_click_outside:
                    isCancleable = group.getCheckedRadioButtonId() == R.id.rb_outside_dismiss ? true : false;
                    break;
                case R.id.rg_keyback:
                    isSetKeyback = group.getCheckedRadioButtonId() == R.id.rb_keback_no ? false : true;
                    break;
                case R.id.rg_width:
                    isSetWidthRadio = group.getCheckedRadioButtonId() == R.id.rb_width_no ? false : true;
                    break;
            }
        });
    }
}
