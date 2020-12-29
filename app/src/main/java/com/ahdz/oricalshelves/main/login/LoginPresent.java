package com.ahdz.oricalshelves.main.login;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

import androidx.lifecycle.MutableLiveData;

import com.ahdz.oricalshelves.MyApplication;
import com.ahdz.oricalshelves.api.BaseResponse;
import com.ahdz.oricalshelves.api.UserApi;
import com.ahdz.oricalshelves.base.BasePresenter;
import com.ahdz.oricalshelves.bean.LoginModel;
import com.ahdz.oricalshelves.bean.SecretData;
import com.ahdz.oricalshelves.databinding.ActivityLoginBinding;
import com.ahdz.oricalshelves.main.auth.activity.HomePageActivity;
import com.ahdz.oricalshelves.main.detail.MyWebviewActivity;
import com.ahdz.oricalshelves.util.PhoneUtil;
import com.ahdz.oricalshelves.util.SPUtil;
import com.ahdz.oricalshelves.view.ImageCode;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class LoginPresent extends BasePresenter  {
    private Context context;
    private ActivityLoginBinding mView;

    public MutableLiveData<String> telePhone = new MutableLiveData<String>();// 手机号
    public MutableLiveData<String> vertifyCode = new MutableLiveData<String>();//验证码
    public MutableLiveData<Boolean> isChecked = new MutableLiveData<Boolean>();//是否选中

    public ImageCode imageCode = ImageCode.getInstance();


    public LoginPresent(Context context, ActivityLoginBinding mView) {
        this.context = context;
        mContext = context;
        this.mView = mView;
    }

    public void changeCode(View view) {
        vertifyCode.setValue(imageCode.setBmpCode(mView.imgVertifycode));
    }


    public void login() {
        if (!mView.cbSelectPl.isChecked()){
            showToast("请先勾选服务协议");
            mView.linearCheck.startAnimation(shakeAnimation(3));
            return;
        }

        if (!PhoneUtil.isPhone(mView.etPhone.getText().toString())) {
            return;
        }
      /*  if (!mView.etInputCode.getText().toString().equals(imageCode.getCode())) {
            showToast("验证码不正确!");
            vertifyCode.setValue(imageCode.setBmpCode(mView.imgVertifycode));//刷新一次
            return;
        }*/

        showDialog("登录中...");
        Map<String, Object> map = new HashMap<>();
        map.put("phone", mView.etPhone.getText().toString());
        map.put("sign", "");
        map.put("verifyCode", "9999");
        map.put("channelCode", MyApplication.APP_LOCAL_CHANNELCODE);
        UserApi.userLogin(map, new Observer<BaseResponse<LoginModel>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(BaseResponse<LoginModel> bean) {
                dismissDialog();
                if (bean.getCode()==200) {
                    showToast("登录成功!");
                    SPUtil.saveToken(bean.getData().getToken());
                    context.startActivity(new Intent(context, HomePageActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                }else {
                    showToast("登录失败!"+bean.getMsg());
                    vertifyCode.setValue(imageCode.setBmpCode(mView.imgVertifycode));//刷新一次
                    SPUtil.saveToken("");
                }
            }

            @Override
            public void onError(Throwable e) {
                dismissDialog();
                showToast("登录失败!");
                vertifyCode.setValue(imageCode.setBmpCode(mView.imgVertifycode));//刷新一次
                SPUtil.saveToken("");
            }

            @Override
            public void onComplete() {

            }
        });

    }

    public void OnProtrul() {
        Map<String, Object> json = new HashMap<>();
        json.put("compress", 1);
        UserApi.getSecretProtocol(json, new Observer<BaseResponse<SecretData>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(BaseResponse<SecretData> data) {
                mView.tvLog.setText(data.getMsg()+"");
                if (data.getCode() == 200) {
                    MyWebviewActivity.GoToService(context,data.getData().getContent(),1,"用户协议");
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
    public Animation shakeAnimation(int CycleTimes) {
        Animation translateAnimation = new TranslateAnimation(0, 30, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(CycleTimes));
        translateAnimation.setDuration(500);
        return translateAnimation;
    }
}
