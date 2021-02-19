package com.ahdz.oricalshelves.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;

import com.ahdz.oricalshelves.R;
import com.ahdz.oricalshelves.api.BaseResponse;
import com.ahdz.oricalshelves.api.UserApi;
import com.ahdz.oricalshelves.base.BaseActivity;
import com.ahdz.oricalshelves.bean.DeviceIdModel;
import com.ahdz.oricalshelves.bean.DeviceIdRequest;
import com.ahdz.oricalshelves.bean.RegiterDeviceRequest;
import com.ahdz.oricalshelves.databinding.ActivityMainBinding;
import com.ahdz.oricalshelves.main.auth.activity.HomePageActivity;
import com.ahdz.oricalshelves.main.login.LoginActivity;
import com.ahdz.oricalshelves.util.AppUtils;
import com.ahdz.oricalshelves.util.PhoneUtil;
import com.ahdz.oricalshelves.util.RxTimerUtil;
import com.ahdz.oricalshelves.util.SPUtil;

import java.util.Date;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 *   闪屏页面
 */
public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private Context context;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {

        context = this;
        RxTimerUtil.timer(2500, number -> {
            Intent intent = new Intent();
            if (TextUtils.isEmpty(SPUtil.getUserToken(MainActivity.this))){
                intent.setClass(MainActivity.this, LoginActivity.class);
            }else {
                intent.setClass(MainActivity.this, HomePageActivity.class);
            }
//            intent.setClass(MainActivity.this, HomePageActivity.class);
            startActivity(intent);
            finish();
        });

        getDeviceID();
    }

    private void getDeviceID() {
        if (!SPUtil.getFirstInfo(MainActivity.this)){
            return;
        }

        Date date = new Date();
        DeviceIdRequest deviceIdRequest = new DeviceIdRequest();
        deviceIdRequest.setHeight(PhoneUtil.getHeight(MainActivity.this));
        deviceIdRequest.setWidth(PhoneUtil.getWidth(MainActivity.this));
        deviceIdRequest.setIp(PhoneUtil.getLocalIpAddress(MainActivity.this));
        deviceIdRequest.setTimestamp(date.getTime());
//        String android_id = Settings.System.getString(MainActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);
        deviceIdRequest.setImei(Settings.System.getString(MainActivity.this. getContentResolver(), Settings.Secure.ANDROID_ID));

        //*******
        UserApi.getDeviceId(deviceIdRequest, new Observer<BaseResponse<DeviceIdModel>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(BaseResponse<DeviceIdModel> deviceIdModelBaseResponse) {
                if (deviceIdModelBaseResponse != null) {
                    if (deviceIdModelBaseResponse.getCode() == 200) {
                        SPUtil.setDeviceId(deviceIdModelBaseResponse.getData().getId());
                        toRegiterDeviceId();
                    }
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

    private void toRegiterDeviceId() {
        RegiterDeviceRequest regiterDeviceRequest = new RegiterDeviceRequest()
                .setBundleId(AppUtils.getPackageName(context))
                .setBundleVersion(AppUtils.getVersionName(context))
                .setDeviceId(SPUtil.getDeviceId())
                .setDeviceName(PhoneUtil.getDeviceBrand())
                .setHeight(PhoneUtil.getHeight(context))
                .setWidth(PhoneUtil.getWidth(context))

                .setTimestamp(new Date().getTime())
                .setDevceType(1)
                .setChannelCode("fups");
        UserApi.registerDevice(regiterDeviceRequest, new io.reactivex.Observer<BaseResponse<String>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(BaseResponse<String> stringBaseResponse) {
                if (stringBaseResponse != null) {
                    if (stringBaseResponse.getCode() == 200) {
//                        BuryEvent.buryEventClient("install");
                        SPUtil.putFirstInfo(MainActivity.this);
                    }
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


}
