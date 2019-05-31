package com.ck.project.utilmodule.base.recycleradapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.collection.SparseArrayCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.List;

/**
 * Created by ck on 2018/6/12.
 * 可添加header和footer的通用多类型adapter
 */

public abstract class MultiHeaderFooterAdapter<T> extends CommonAdapter<T> {
    private static final int ITEM_TYPE_HEADER = 100000;
    private static final int ITEM_TYPE_FOOTER = 200000;
    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFooterViews = new SparseArrayCompat<>();
    private MultiTypeInterface mMultiTypeInterface;

    public MultiHeaderFooterAdapter(Context mContext, List<T> mDataList, MultiTypeInterface multiTypeInterface) {
        super(mContext, 0, mDataList);
        this.mMultiTypeInterface = multiTypeInterface;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderViews.get(viewType) != null) {
            return BaseViewHolder.get(mContext, mHeaderViews.get(viewType));
        } else if (mFooterViews.get(viewType) != null) {
            return BaseViewHolder.get(mContext, mFooterViews.get(viewType));
        }
        return BaseViewHolder.get(mContext, parent, mMultiTypeInterface.onItemLayoutId(viewType));
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderViewPos(position)) {
            return mHeaderViews.keyAt(position);
        } else if (isFooterViewPos(position)) {
            return mFooterViews.keyAt(position - getHeadersCount() - getRealDataCount());
        }
        return mMultiTypeInterface.onItemLayoutType(position - getHeadersCount());
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (isHeaderViewPos(position)) {
            return;
        }
        if (isFooterViewPos(position)) {
            return;
        }
        final int realPosition = position - getHeadersCount();
        if (realPosition < 0 || realPosition > mDataList.size() - 1) {
            return;
        }
        if (holder instanceof BaseViewHolder) {
            onBindData((BaseViewHolder) holder, mDataList.get(realPosition), realPosition);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(holder.itemView, realPosition);
                    }
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (mOnItemLongClickListener != null) {
                        mOnItemLongClickListener.onItemLongClick(holder.itemView, realPosition);
                    }
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return getHeadersCount() + getRealDataCount() + getFootersCount();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        attachedToRecyclerView(recyclerView, new SpanSizeCallback() {
            @Override
            public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position) {
                int viewType = getItemViewType(position);
                if (mHeaderViews.get(viewType) != null) {
                    return layoutManager.getSpanCount();
                } else if (mFooterViews.get(viewType) != null) {
                    return layoutManager.getSpanCount();
                }
                if (oldLookup != null) {
                    if (mMultiTypeInterface.onItemFullSpan(position - getHeadersCount())) {
                        return layoutManager.getSpanCount();
                    } else {
                        return oldLookup.getSpanSize(position);
                    }
                }
                return 1;
            }
        });
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        if (isHeaderViewPos(position) || isFooterViewPos(position)) {
            setFullSpan(holder);
        }
    }

    private interface SpanSizeCallback {
        int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position);
    }

    public void attachedToRecyclerView(RecyclerView recyclerView, final SpanSizeCallback callback) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return callback.getSpanSize(gridLayoutManager, spanSizeLookup, position);
                }
            });
        }
    }

    /**
     * 获取header数量
     *
     * @return
     */
    public int getHeadersCount() {
        return mHeaderViews.size();
    }

    /**
     * 获取footer数量
     *
     * @return
     */
    public int getFootersCount() {
        return mFooterViews.size();
    }

    /**
     * 获取去除header和footer的数据数量
     *
     * @return
     */
    public int getRealDataCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    public boolean isHeaderViewPos(int position) {
        return position < getHeadersCount();
    }

    public boolean isFooterViewPos(int position) {
        return position >= getHeadersCount() + getRealDataCount();
    }

    /**
     * 添加头部
     *
     * @param view
     */
    public void addHeaderView(View view) {
        mHeaderViews.put(mHeaderViews.size() + ITEM_TYPE_HEADER, view);
    }

    /**
     * 去除头部
     */
    public void clearHeaderViews() {
        mHeaderViews.clear();
    }

    /**
     * 添加尾部
     *
     * @param view
     */
    public void addFooterView(View view) {
        mFooterViews.put(mFooterViews.size() + ITEM_TYPE_FOOTER, view);
    }

    /**
     * 去除尾部
     *
     * @param view
     */
    public void clearFooterViews(View view) {
        mFooterViews.clear();
    }

    private void setFullSpan(RecyclerView.ViewHolder holder) {
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(true);
        }
    }

}
