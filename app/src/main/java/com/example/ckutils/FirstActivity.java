package com.example.ckutils;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.ohosure.component.componentlib.ServiceFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FirstActivity extends AppCompatActivity {

    @BindView(R.id.bt_common_adapter)
    Button btCommonAdapter;
    @BindView(R.id.bt_bottom_sheet)
    Button btBottomSheet;
    private Intent intent= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_test_huixue,R.id.bt_common_adapter,R.id.bt_bottom_sheet,R.id.bt_custom_dialog})
    public void OnClick(View view){
        switch (view.getId()){
            case R.id.bt_test_huixue:
                ServiceFactory.getInstance().getOpenService().launch(
                        this,
                        "",
                        "");
                break;
            case R.id.bt_common_adapter:
                intent = new Intent(this,MainActivity.class);
                break;
            case R.id.bt_bottom_sheet:
                intent = new Intent(this,BottomSheetActivity.class);
                break;
            case R.id.bt_custom_dialog:
                intent = new Intent(this,CustomDialogActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
