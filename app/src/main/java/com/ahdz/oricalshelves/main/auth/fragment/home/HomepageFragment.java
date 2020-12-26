package com.ahdz.oricalshelves.main.auth.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;

public class HomepageFragment extends Fragment {

    private FragmentHomePresent present;
    private HomepageAdapter adapter;
    private FragmentHomeInsBinding mBindingView;

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

        present.projectsList.observe(this, projects -> {
            adapter.setList(projects);
        });
        present.baihui.observe(this, baiHuiData -> {
            adapter.setMuch(present.displayWithComma(baiHuiData.getQuota()+"") +".00");
        });
        present.gbList.observe(this, strings -> {
            adapter.setStrings(strings);
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
