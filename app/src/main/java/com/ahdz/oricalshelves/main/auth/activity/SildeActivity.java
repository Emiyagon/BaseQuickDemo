package com.ahdz.oricalshelves.main.auth.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.ahdz.oricalshelves.R;
import com.ahdz.oricalshelves.base.BaseActivity;
import com.ahdz.oricalshelves.databinding.ActivitySildeBinding;

public class SildeActivity extends BaseActivity<ActivitySildeBinding> {


    @Override
    protected int setLayoutId() {
        return R.layout.activity_silde;
    }

    @Override
    protected void initData() {
        mBindingView.title.llBack.setOnClickListener(v -> finish());
        mBindingView.title.tvTitle.setText("贷款记录");

        mBindingView.srf.setEnableAutoLoadMore(false);



    }
}