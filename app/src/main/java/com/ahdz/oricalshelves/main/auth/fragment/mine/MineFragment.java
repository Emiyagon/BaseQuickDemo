package com.ahdz.oricalshelves.main.auth.fragment.mine;

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
import com.ahdz.oricalshelves.bean.UserInfoData;
import com.ahdz.oricalshelves.databinding.FragmentMineBinding;

import java.util.ArrayList;

public class MineFragment extends Fragment {
   private FragmentMineBinding mBindingView;
    private MineAdapter adapter;
    private FragmentMinePresent present;

    public static MineFragment newInstance() {
        Bundle args = new Bundle();
        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBindingView = DataBindingUtil.inflate(inflater, R.layout.fragment_mine, container, false);
        present = new FragmentMinePresent(getContext(), mBindingView);
        mBindingView.setViewModule(present);
        mBindingView.setLifecycleOwner(this);
        initView();
        return mBindingView.getRoot();
    }


    protected void initView() {
        adapter = new MineAdapter(getContext());
        mBindingView.rec.setAdapter(adapter);

        present.OnHttp();
        present.MineHttp();

        present.userInfo.observe(this, userInfoData -> {
            adapter.setTelePhone(getPt(userInfoData.getPhone()));
        });
        present.projectsList.observe(this, projects -> {
            adapter.setList(projects);
        });
    }
    private String getPt(String str){
        String stars = "****";
        StringBuilder sb = new StringBuilder(str);
        sb.replace(3, 7, stars);

        return sb.toString();
    }
    @Override
    public void onResume() {
        super.onResume();
        present.OnHttp();
        present.MineHttp();
    }
}
