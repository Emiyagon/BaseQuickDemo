package com.ahdz.oricalshelves.main.auth.activity.setting;

import android.content.Context;

import com.ahdz.oricalshelves.base.BasePresenter;
import com.ahdz.oricalshelves.databinding.ActivitySettingBinding;

public class SettingPresent extends BasePresenter {
    private Context context;
    ActivitySettingBinding binding;

    public SettingPresent(Context context, ActivitySettingBinding binding) {
        this.context = context;
        this.binding = binding;
    }
}
