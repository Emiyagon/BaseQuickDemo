package com.ahdz.oricalshelves.main.auth.activity;

import android.content.Context;
import android.provider.Settings;

import com.ahdz.oricalshelves.BuildConfig;
import com.ahdz.oricalshelves.api.BaseResponse;
import com.ahdz.oricalshelves.api.UserApi;
import com.ahdz.oricalshelves.base.BasePresenter;
import com.ahdz.oricalshelves.bean.NewRegiterDevice;
import com.ahdz.oricalshelves.bean.RegiterDeviceRequest;
import com.ahdz.oricalshelves.databinding.ActivityHomePageBinding;
import com.ahdz.oricalshelves.util.AppUtils;
import com.ahdz.oricalshelves.util.PhoneUtil;
import com.ahdz.oricalshelves.util.SPUtil;

import java.util.Date;

import io.reactivex.disposables.Disposable;

public class HomePagePresent extends BasePresenter {
    private Context context;
    private ActivityHomePageBinding mView;



    public HomePagePresent(Context context, ActivityHomePageBinding mView) {
        this.context = context;
        mContext = context;
        this.mView = mView;
    }


    public void onOther() {

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

        NewRegiterDevice services = new NewRegiterDevice()
                .setDevices(AppUtils.getDeviceName(context))// 设备名
                .setGenerateId(SPUtil.getDeviceId())//唯一id,缓存的
                .setImei(Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID)) //手机唯一码
                .setUserId(Long.parseLong(SPUtil.getUserid()))//用户id
                .setSign("")
                ;
        UserApi.registerDevice2(services, new io.reactivex.Observer<BaseResponse<String>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(BaseResponse<String> stringBaseResponse) {

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
