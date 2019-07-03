package com.example.ckutils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.ck.project.utilmodule.base.recycleradapter.BaseViewHolder;
import com.ck.project.utilmodule.base.recycleradapter.CommonAdapter;
import com.ck.project.utilmodule.utils.DisplayUtil;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class BottomSheetActivity extends AppCompatActivity {

    private BottomSheetDialog bottomSheetDialog;
    private BottomSheetBehavior bottomSheetBehavior;

    private RecyclerView dialogRecyclerView;
    private CommonAdapter dialogAdapter;
    private List<String> dialogList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet);
        showSheetDialog();
        findViewById(R.id.bt_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomSheetDialog != null) {
                    if (bottomSheetDialog.isShowing()) {
                        bottomSheetDialog.dismiss();
                    }else{
                        bottomSheetDialog.show();
                    }
                }
            }
        });
    }

    private void showSheetDialog(){
        dialogList.clear();
        for(int i=0;i<20;i++){
            dialogList.add("第"+i+"个数据");
        }
        View view = View.inflate(this,R.layout.layout_bottom_sheet,null);
        dialogRecyclerView = view.findViewById(R.id.item_recyclerview);
        dialogAdapter = new CommonAdapter<String>(this,R.layout.item_data,dialogList) {
            @Override
            protected void onBindData(BaseViewHolder holder, String data, int position) {
                holder.setText(R.id.tv_item_data,data+"");
            }
        };
        dialogRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        dialogRecyclerView.setAdapter(dialogAdapter);
        dialogRecyclerView.setItemAnimator(new DefaultItemAnimator());
        bottomSheetDialog = new BottomSheetDialog(this,R.style.MySheetDialog);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.getWindow().setWindowAnimations(R.style.PopupAnimation);
        bottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());
        bottomSheetBehavior.setPeekHeight(DisplayUtil.dp2px(this,150));
        //bottomSheetBehavior.setFitToContents(true);
        bottomSheetBehavior.setHideable(false);
//        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//            @Override
//            public void onStateChanged(@NonNull View view, int i) {
//                if (i == BottomSheetBehavior.STATE_HIDDEN) {
//                    bottomSheetDialog.dismiss();
//                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//                }
//            }
//
//            @Override
//            public void onSlide(@NonNull View view, float v) {
//
//            }
//        });

    }

    private int getWindowHeight(){
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return displayMetrics.heightPixels- DisplayUtil.dp2px(this,50);
    }
}
