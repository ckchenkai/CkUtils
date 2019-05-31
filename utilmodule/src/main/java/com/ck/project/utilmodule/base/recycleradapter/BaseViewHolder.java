package com.ck.project.utilmodule.base.recycleradapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.collection.SparseArrayCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by CK on 2018/6/11.
 * Email:910663958@qq.com
 */

public class BaseViewHolder extends RecyclerView.ViewHolder{
    private SparseArrayCompat<View> mViews;
    private View mConvertView;
    private Context mContext;
    public BaseViewHolder(Context context,View itemView) {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
        mViews = new SparseArrayCompat<>();
    }

    /**
     * 获取ViewHolder
     * @param context
     * @param parent
     * @param layoutId
     * @return
     */
    public static BaseViewHolder get(Context context, ViewGroup parent, int layoutId){
        View itemView = LayoutInflater.from(context).inflate(layoutId,parent,false);
        BaseViewHolder viewHolder = new BaseViewHolder(context,itemView);
        return viewHolder;
    }

    public static BaseViewHolder get(Context context,View view){
        BaseViewHolder viewHolder = new BaseViewHolder(context,view);
        return viewHolder;
    }

    /**
     * 获取View
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId){
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T) view;
    }

    /**
     * 获取View
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends TextView> T getTextView(int viewId){
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T) view;
    }

    /**
     * 设置图片
     * @param view
     * @param picPath
     */
//    public void setImage(View view,String picPath){
//        if(view !=null&&view instanceof ImageView){
//            if(TextUtils.isEmpty(picPath)){
//                view.setVisibility(View.GONE);
//            }else{
//                view.setVisibility(View.VISIBLE);
//                GlideHelper.getInstance().load(mContext,picPath, (ImageView) view);
//            }
//        }
//    }

    /**
     * 设置文字
     * @param viewId
     * @return
     */
    public void setText(int viewId,String text){
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        if(view instanceof TextView){
            ((TextView)view).setText(text+"");
        }
    }

    /**
     * 设置图片
     * @param viewId
     * @param picPath
     */
//    public void setImage(int viewId,String picPath){
//        View view = mViews.get(viewId);
//        if (view == null) {
//            view = mConvertView.findViewById(viewId);
//            mViews.put(viewId,view);
//        }
//        if(view instanceof ImageView){
//            if(TextUtils.isEmpty(picPath)){
//                view.setVisibility(View.GONE);
//            }else{
//                view.setVisibility(View.VISIBLE);
//                GlideHelper.getInstance().load(mContext,picPath, (ImageView) view);
//            }
//        }
//    }
//    public void setRoundImage(int viewId,String picPath,int radius){
//        View view = mViews.get(viewId);
//        if (view == null) {
//            view = mConvertView.findViewById(viewId);
//            mViews.put(viewId,view);
//        }
//        if(view instanceof ImageView){
//            if(TextUtils.isEmpty(picPath)){
//                view.setVisibility(View.GONE);
//            }else{
//                view.setVisibility(View.VISIBLE);
//                GlideHelper.getInstance().loadRound(mContext,picPath, radius,(ImageView) view);
//            }
//        }
//    }
}
