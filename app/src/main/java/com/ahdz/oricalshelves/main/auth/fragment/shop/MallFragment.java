package com.ahdz.oricalshelves.main.auth.fragment.shop;

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
import com.ahdz.oricalshelves.bean.Projects;
import com.ahdz.oricalshelves.databinding.FragmentMallBinding;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

public class MallFragment extends Fragment {

    private MallAdapter adapter;
    private FragmentMallBinding mBindingView;
    private FragmentMallPresent present;

    public static MallFragment newInstance() {
        Bundle args = new Bundle();
        MallFragment fragment = new MallFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {

        mBindingView = DataBindingUtil.inflate(inflater, R.layout.fragment_mall, parent, false);
        present = new FragmentMallPresent(getContext(), mBindingView);
        mBindingView.setViewModule(present);
        mBindingView.setLifecycleOwner(this);

        initView();
        return mBindingView.getRoot();
    }


    protected void initView() {
        adapter = new MallAdapter(getContext());
        mBindingView.rlShops.setAdapter(adapter);
        present.OnHttp();

        mBindingView.srf.setEnableLoadMore(false);

        mBindingView.srf.setOnRefreshListener(refreshLayout -> {
            present.OnHttp();
        });

        present.projectsList.observe(this, projects -> {
            adapter.setList(projects);
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        present.OnHttp();
    }
}
