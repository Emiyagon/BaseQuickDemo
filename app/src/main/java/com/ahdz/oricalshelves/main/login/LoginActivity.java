package com.ahdz.oricalshelves.main.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;

import com.ahdz.oricalshelves.MyApplication;
import com.ahdz.oricalshelves.R;
import com.ahdz.oricalshelves.base.BaseActivity;
import com.ahdz.oricalshelves.databinding.ActivityLoginBinding;
import com.ahdz.oricalshelves.main.detail.MyWebviewActivity;
import com.ahdz.oricalshelves.view.ImageCode;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.gpu.PixelationFilterTransformation;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> implements TextWatcher {


    private LoginPresent mPresenter;


    public  String ysUrl = "https://download-api.diaoeng.cn/mayiyingji.html";

    @Override
    protected int setLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        // 每个设置的都不一样,所以这都是推荐单独设置的
        mPresenter = new LoginPresent(this,mBindingView);
        mBindingView.setViewModule(mPresenter);
        mBindingView.etInputCode.addTextChangedListener(this);
        mBindingView.etPhone.addTextChangedListener(this);

        switch (MyApplication.APP_LOCAL_CHANNELCODE) {
            default:
                break;
            case "JRHxVyfhdpCbTuhu"://360蚂蚁应急贷款分期
                mBindingView.icLogo.setImageResource(R.mipmap.ic_splash);
                ysUrl = "https://download-api.diaoeng.cn/mayiyingji.html";
                break;
            case "8B5c1ql309nuKS5u"://蚂蚁借呗大额信用
                mBindingView.icLogo.setImageResource(R.mipmap.ic_myjb);
                ysUrl = "https://download-api.diaoeng.cn/mayijiebei.html";
                break;
            case "W38Jqgl8dDXQ1Ifb"://马上应急贷款花
                mBindingView.icLogo.setImageResource(R.mipmap.ic_msyj);
                ysUrl = "https://download-api.diaoeng.cn/mashangyingji.html";
                break;
            case "gsyM3zkhAtHtKJgS"://信用借钱贷款分期
                mBindingView.icLogo.setImageResource(R.mipmap.ic_xyjq);
                ysUrl = "https://download-api.diaoeng.cn/xinyong.html";

                break;
            case "NzlFuqzXn0IhYSQP"://花呗借呗还呗贷款
                mBindingView.icLogo.setImageResource(R.mipmap.ic_hbjb);
                ysUrl = "https://download-api.diaoeng.cn/huabei.html";

                break;
            case "VF6Pj2h6jcwHAf4J"://有钱花应急借钱贷款
                mBindingView.icLogo.setImageResource(R.mipmap.ic_yqh);
                ysUrl = "https://download-api.diaoeng.cn/youqianhua.html";

                break;



        }


        mPresenter.vertifyCode.setValue(mPresenter.imageCode.setBmpCode(mBindingView.imgVertifycode)); //  开局设置一次

        //
        mBindingView.text2.setOnClickListener(v -> {
            MyWebviewActivity.GoToService(LoginActivity.this, ysUrl,1,"用户协议");
        });

        mBindingView.stvLogin.setOnClickListener(v -> {
            mPresenter.login();
        });

        mBindingView.ivDelete.setOnClickListener(v -> {
            mBindingView.etPhone.setText("");
        });
        mBindingView.text1.setOnClickListener(v -> {
            mBindingView.cbSelectPl.setChecked(!mBindingView.cbSelectPl.isChecked());
        });

        mBindingView.toOnekey.setOnClickListener(v -> {

            // 一键登录

        });

    }




    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (TextUtils.isEmpty(mBindingView.etPhone.getText().toString())) {
            mBindingView.ivDelete.setVisibility(View.GONE);
            mBindingView.stvLogin.setSolid(getResources().getColor(R.color.gray_login));
            mBindingView.stvLogin.setClickable(false);
        } else {
            mBindingView.ivDelete.setVisibility(View.VISIBLE);
            mBindingView.stvLogin.setSolid(getResources().getColor(R.color.orange_login));
            mBindingView.stvLogin.setClickable(true);
        }

        /*if (TextUtils.isEmpty(mBindingView.etPhone.getText().toString()) || TextUtils.isEmpty(mBindingView.etInputCode.getText().toString())){
            mBindingView.stvLogin.setSolid(getResources().getColor(R.color.gray_login));
            mBindingView.stvLogin.setClickable(false);
        }else {
            mBindingView.stvLogin.setSolid(getResources().getColor(R.color.orange_login));
            mBindingView.stvLogin.setClickable(true);
        }*/


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.unDisposable();
            mPresenter.onDestroy();
        }
    }

    private long time;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            if (System.currentTimeMillis() - time > 2000) {
                time = System.currentTimeMillis();
                showToast("再点击一次退出程序");
            } else {
                Intent intent = new Intent(BASE_ADDRESS);
                intent.putExtra(CLOSE_ALL, 1);
                //发送广播
                sendBroadcast(intent);
            }
        }
        return true;
    }
}
