package com.ck.project.utilmodule.base.recycleradapter;

/**
 * Created by ck on 2018/6/12.
 * 通用多布局adapter接口
 */

public interface MultiTypeInterface {
    int onItemLayoutType(int position);
    int onItemLayoutId(int viewType);
    boolean onItemFullSpan(int position);
}
