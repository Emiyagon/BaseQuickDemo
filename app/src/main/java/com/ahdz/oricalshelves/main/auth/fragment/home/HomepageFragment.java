package com.ahdz.oricalshelves.main.auth.fragment.home;

import android.content.Intent;
import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.ahdz.oricalshelves.R;
import com.ahdz.oricalshelves.base.BaseFragment;
import com.ahdz.oricalshelves.bean.BaiHuiData;
import com.ahdz.oricalshelves.bean.Projects;
import com.ahdz.oricalshelves.databinding.FragmentHomeInsBinding;
import com.ahdz.oricalshelves.main.detail.MyWebviewActivity;
import com.ahdz.oricalshelves.main.other.TimeActivity;
import com.ahdz.oricalshelves.util.GlideImageLoader;
import com.ahdz.oricalshelves.util.GlideUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

public class HomepageFragment extends Fragment {

    private FragmentHomePresent present;
    private HomepageAdapter adapter;
    private FragmentHomeInsBinding mBindingView;
    private List<String> mBannerList;

    public static HomepageFragment newInstance() {
        Bundle args = new Bundle();
        HomepageFragment fragment = new HomepageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        // mBindingView  R.layout.fragment_home_ins
        mBindingView = DataBindingUtil.inflate(inflater, R.layout.fragment_home_ins, parent, false);
        present = new FragmentHomePresent(getContext(), mBindingView);
        mBindingView.setViewModule(present);
        mBindingView.setLifecycleOwner(this);
        OnRead();
        return mBindingView.getRoot();
    }

    private void OnRead() {

        adapter = new HomepageAdapter(getContext());
        present.OnHttp();
        present.GbHttp();
        present.getBannerList();

        present.bannerList.observe(getViewLifecycleOwner(), bannerList -> {
//            adapter.setBanner(strings);
            mBannerList = new ArrayList<>();
            if (bannerList!=null && bannerList.size() > 0) {
                mBindingView.title.banner.setVisibility(View.VISIBLE);
                mBannerList.clear();
                for (int i = 0; i < bannerList.size(); i++) {
                    mBannerList.add(bannerList.get(i).getPictureUrl());
                }
                GlideUtil.putBannerImg(bannerList.get(bannerList.size()-1),mBindingView.title.imgLoader);
            }else {
                mBindingView.title.banner.setVisibility(View.GONE);
            }

            mBindingView.title.banner.setImages(mBannerList);
            mBindingView.title.banner.setDelayTime(3500);
            mBindingView.title.banner.setImageLoader(new GlideImageLoader());
            mBindingView.title.banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mBindingView.title.banner.setOutlineProvider(new ViewOutlineProvider() {
                    @Override
                    public void getOutline(View view, Outline outline) {
                        outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 16f);
                    }
                });
                mBindingView.title.banner.setClipToOutline(true);
            }

            mBindingView.title.banner.setOnBannerListener(posT -> {
                present.OnClickTo(bannerList.get(posT).getAppId()+"");
            });

            mBindingView.title.banner.startAutoPlay();

        });


        present.projectsList.observe(getViewLifecycleOwner(), projects -> {
            adapter.setList(projects);
        });
        present.baihui.observe(getViewLifecycleOwner(), baiHuiData -> {
//            adapter.setBaiHui(baiHuiData);
            mBindingView.title.tvMuch.setText(present.displayWithComma(baiHuiData.getQuota()+"")+".00");
            mBindingView.title.stvL.setOnClickListener(v -> {
              /*  if (TextUtils.isEmpty(baiHuiData.getUrl())){
//                    startActivity(new Intent(getContext(), TimeActivity.class));
                    return;
                }*/
//                MyWebviewActivity.GoToService(getContext(),baiHuiData.getUrl(),0,"");
                present.toApply();
            });
        });
        present.gbList.observe(getViewLifecycleOwner(), strings -> {
            adapter.setStrings(strings);
            if ( strings.size() > 0) {
                mBindingView.title.homeScrollText.setDataSource(strings);
                mBindingView.title.homeScrollText.startPlay();
            }
        });


        mBindingView.rlShops.setAdapter(adapter);
        mBindingView.srf.setEnableLoadMore(false);
        mBindingView.srf.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                present.OnHttp();
                present.GbHttp();
            }
        });

        mBindingView.clicl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                present.showToast("s");
            }
        });

    }




    @Override
    public void onResume() {
        super.onResume();
        present.OnHttp();
    }
}
