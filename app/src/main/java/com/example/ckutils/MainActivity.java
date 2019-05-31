package com.example.ckutils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ck.project.utilmodule.base.recycleradapter.BaseViewHolder;
import com.ck.project.utilmodule.base.recycleradapter.CommonAdapter;
import com.ck.project.utilmodule.base.recycleradapter.MultiHeaderFooterAdapter;
import com.ck.project.utilmodule.base.recycleradapter.MultiTypeInterface;
import com.ck.project.utilmodule.base.recycleradapter.OnItemClickListener;
import com.ck.project.utilmodule.utils.ToastUtils;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MultiTypeInterface, OnItemClickListener, OnRefreshLoadMoreListener {

    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefreshLayout;
    private MultiHeaderFooterAdapter adapter;
    private List<String> list = new ArrayList<>();
    private View headerView;
    private Banner banner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRefreshLayout = findViewById(R.id.refresh_layout);
        mRecyclerView = findViewById(R.id.recycler_view);
        initMultiAdapter();
    }

    private void initData(){
        list.clear();
        for(int i=0;i<20;i++){
            list.add("数据"+i);
        }
    }
    private void initHeader() {
        if (headerView == null) {
            headerView = LayoutInflater.from(this).inflate(R.layout.item_bannner, null);
            banner = headerView.findViewById(R.id.banner);
            //banner
            banner.setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    imageView.setImageResource(TextUtils.equals((String) path, "0") ? R.drawable.banner_1 : R.drawable.banner_2);
                }
            });
            List<String> list = new ArrayList<>();
            list.add("0");
            list.add("1");
            banner.setImages(list);
            banner.setDelayTime(5000);
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            banner.setIndicatorGravity(BannerConfig.RIGHT);
            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {

                }
            });
            banner.start();
        } else {
            List<String> list = new ArrayList<>();
            list.add("0");
            list.add("1");
            banner.update(list);
        }
    }

    private void initMultiAdapter(){
        adapter = new MultiHeaderFooterAdapter<String>(this,list,this) {
            @Override
            protected void onBindData(BaseViewHolder holder, String data, int position) {
                holder.setText(R.id.tv_item_data,data+"");
            }
        };
        initHeader();
        adapter.addHeaderView(headerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        mRefreshLayout.setRefreshHeader(new MaterialHeader(this));
        mRefreshLayout.setRefreshFooter(new BallPulseFooter(this));
        mRefreshLayout.setOnRefreshLoadMoreListener(this);
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.autoRefresh(200);
    }

    @Override
    public int onItemLayoutType(int position) {
        return position%2;
    }

    @Override
    public int onItemLayoutId(int viewType) {
        return viewType==0?R.layout.item_data:R.layout.item_data_2;
    }

    @Override
    public boolean onItemFullSpan(int position) {
        return false;
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {

    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        initData();
        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
                mRefreshLayout.finishRefresh();
            }
        },1500);
    }

    @Override
    public void onItemClick(View view, int position) {
        ToastUtils.getInstance().showToast(this,"点了第"+position+"个item");
    }
}
