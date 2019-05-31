package com.ck.project.utilmodule.base.recycleradapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Created by ck on 2018/6/12.
 * 通用多布局adapter
 */

public abstract class MultiTypeAdapter<T> extends CommonAdapter<T> {
    private MultiTypeInterface typeInterface;
    public MultiTypeAdapter(Context mContext, List<T> mDataList, MultiTypeInterface typeInterface) {
        super(mContext, 0, mDataList);
        this.typeInterface = typeInterface;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         return BaseViewHolder.get(mContext,parent,typeInterface.onItemLayoutId(viewType));
    }

    @Override
    public int getItemViewType(int position) {
        return typeInterface.onItemLayoutType(position);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull final RecyclerView recyclerView) {
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (typeInterface.onItemFullSpan(position)) {
                        return ((GridLayoutManager)layoutManager).getSpanCount();
                    } else {
                        return spanSizeLookup.getSpanSize(position);
                    }
                }
            });
        }
    }

}
