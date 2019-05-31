package com.ck.project.utilmodule.base.recycleradapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Created by CK on 2018/6/11.
 * Email:910663958@qq.com
 * 通用的adapter
 */

public abstract class CommonAdapter<T> extends RecyclerView.Adapter {
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDataList;
    protected OnItemClickListener mOnItemClickListener;
    protected OnItemLongClickListener mOnItemLongClickListener;

    @SuppressWarnings(value={"unchecked", "rawtypes"})
    public CommonAdapter(Context mContext, int mLayoutId, List<T> mDataList) {
        this.mContext = mContext;
        this.mLayoutId = mLayoutId;
        this.mDataList = mDataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return BaseViewHolder.get(mContext,parent,mLayoutId);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(position<0||position>getItemCount()-1){
            return;
        }
        if (holder instanceof BaseViewHolder) {
            if (position > mDataList.size() - 1) {
                onBindData((BaseViewHolder) holder,null,position);
            }else{
                onBindData((BaseViewHolder) holder,mDataList.get(position),position);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(holder.itemView,position);
                    }
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mOnItemLongClickListener != null) {
                        mOnItemLongClickListener.onItemLongClick(holder.itemView,position);
                    }
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDataList==null?0:mDataList.size();
    }

    /**
     * 绑定数据
     * @param holder
     * @param data
     * @param position
     */
    protected abstract void onBindData(BaseViewHolder holder,T data,int position);

    /**
     * 设置item点击事件
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }

    /**
     * 设置item长按事件
     * @param onItemLongClickListener
     */
    public void setmOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    /**
     * 刷新
     * @param list
     */
    public void refresh(List<T> list){
        if (list == null) {
            return;
        }
        int positionStart = mDataList.size()-1;
        mDataList.clear();
        mDataList.addAll(list);
        notifyItemRangeChanged(positionStart,list.size());
    }

    /**
     * 加载更多
     * @param list
     */
    public void loadMore(List<T> list){
        if (list == null) {
            return;
        }
        int positionStart = mDataList.size()-1;
        mDataList.addAll(list);
        notifyItemRangeChanged(positionStart,list.size());
    }
}
