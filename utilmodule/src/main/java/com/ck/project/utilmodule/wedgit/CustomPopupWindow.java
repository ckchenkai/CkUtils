package com.ck.project.utilmodule.wedgit;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ck.project.utilmodule.R;
import com.ck.project.utilmodule.base.recycleradapter.BaseViewHolder;
import com.ck.project.utilmodule.base.recycleradapter.CommonAdapter;
import com.ck.project.utilmodule.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ck on 2018/9/8.
 */

public class CustomPopupWindow extends PopupWindow {
    private Activity mContext;
    private View contentView;
    private int width, height;
    private CommonAdapter adapter;
    private List<CustomPopupWindowBean> list = new ArrayList<>();
    private OnItemClickListener onItemClickListener;
    private RecyclerView recyclerView;

    public CustomPopupWindow(Context context) {
        super(context);
        this.mContext = (Activity) context;
        initPopupWindow();
    }

    private void initPopupWindow() {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.layout_custom_popupwindow, null);
        recyclerView = contentView.findViewById(R.id.recycler_view);
        setContentView(contentView);
        // 设置弹出窗体的宽
        width = (int) (DisplayUtil.getScreenWidth(mContext) * 0.8);
        height = ViewGroup.LayoutParams.WRAP_CONTENT;
        this.setWidth(width);
        // 设置弹出窗体的高
        this.setHeight(height);
        // 设置弹出窗体可点击
        this.setTouchable(true);
        this.setFocusable(true);
        // 设置点击是否消失
        this.setOutsideTouchable(true);
        //设置弹出窗体动画效果
        this.setAnimationStyle(R.style.PopupAnimation);
        // 绘制
        this.mandatoryDraw();
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
                lp.alpha = 1f;
                mContext.getWindow().setAttributes(lp);
            }
        });

        adapter = new CommonAdapter<CustomPopupWindowBean>(mContext, R.layout.list_item_popupwindow, list) {
            @Override
            protected void onBindData(BaseViewHolder holder, final CustomPopupWindowBean data, final int position) {
                if (data == null) {
                    return;
                }
                holder.setText(R.id.item_tv_title, data.getTitle());
                if (data.getTitleColor() != 0) {
                    ((TextView)holder.getView(R.id.item_tv_title)).setTextColor(data.getTitleColor());
                }else{
                    ((TextView)holder.getView(R.id.item_tv_title)).setTextColor(mContext.getResources().getColor(R.color.colorAccent));
                }
                if (position == getItemCount() - 1) {
                    holder.getView(R.id.line).setVisibility(View.GONE);
                } else {
                    holder.getView(R.id.line).setVisibility(View.VISIBLE);
                }
            }
        };
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(manager);
        adapter.setOnItemClickListener(new com.ck.project.utilmodule.base.recycleradapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position < 0 || position > list.size() - 1)
                    return;
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(list.get(position), position);
                }
            }
        });
        recyclerView.setAdapter(adapter);

    }

    /**
     * 强制绘制popupWindowView，并且初始化popupWindowView的尺寸
     */
    private void mandatoryDraw() {
        this.contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        /**
         * 强制刷新后拿到PopupWindow的宽高
         */
        this.width = this.contentView.getMeasuredWidth();
        this.height = this.contentView.getMeasuredHeight();
    }

    public CustomPopupWindow setList(List<CustomPopupWindowBean> list) {
        this.list.clear();
        this.list.addAll(list);
        adapter.notifyDataSetChanged();
        return this;
    }

    public CustomPopupWindow setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        return this;
    }

    public void showCenter() {
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
        lp.alpha = 0.7f;
        mContext.getWindow().setAttributes(lp);
        showAtLocation(mContext.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }

    public void showBottom() {
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
        lp.alpha = 0.7f;
        mContext.getWindow().setAttributes(lp);
        showAtLocation(mContext.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }
//    public static class PopupWindowBuilder {
//        private static CustomPopupWindow popupWindow;
//        public static PopupWindowBuilder ourInstance;
//
//        public static PopupWindowBuilder getInstance(Activity activity) {
//            if (ourInstance == null){
//                ourInstance = new PopupWindowBuilder();
//                popupWindow = new CustomPopupWindow(activity);
//            }
//            return ourInstance;
//        }
//
//        public PopupWindowBuilder setTouchable(boolean touchable) {
//            popupWindow.setTouchable(touchable);
//            return this;
//        }
//
//        public PopupWindowBuilder setAnimationStyle(int animationStyle) {
//            popupWindow.setAnimationStyle(animationStyle);
//            return this;
//        }
//
//        public PopupWindowBuilder setBackgroundDrawable(Drawable background) {
//            popupWindow.setBackgroundDrawable(background);
//            return this;
//        }
//
//        public CustomPopupWindow getPopupWindow() {
//            popupWindow.update();
//            return popupWindow;
//        }
//
//        public PopupWindowBuilder setList(List<CustomPopupWindowBean> list){
//            popupWindow.setList(list);
//            return this;
//        }
//
//        public PopupWindowBuilder setOnItemClickListener(OnItemClickListener onItemClickListener){
//            popupWindow.setOnItemClickListener(onItemClickListener);
//            return this;
//        }
//
//        public void showCenter(){
//            // 设置背景颜色变暗
//            WindowManager.LayoutParams lp = popupWindow.mContext.getWindow().getAttributes();
//            lp.alpha = 0.7f;
//            popupWindow.mContext.getWindow().setAttributes(lp);
//            popupWindow.showAtLocation(popupWindow.mContext.getWindow().getDecorView(), Gravity.CENTER,0,0);
//        }
//
//        public void showBottom(){
//            // 设置背景颜色变暗
//            WindowManager.LayoutParams lp = popupWindow.mContext.getWindow().getAttributes();
//            lp.alpha = 0.7f;
//            popupWindow.mContext.getWindow().setAttributes(lp);
//            popupWindow.showAtLocation(popupWindow.mContext.getWindow().getDecorView(), Gravity.BOTTOM,0,0);
//        }
//    }

    public interface OnItemClickListener {
        void onItemClick(CustomPopupWindowBean bean, int position);
    }
}
