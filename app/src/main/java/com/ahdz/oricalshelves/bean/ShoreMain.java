package com.ahdz.oricalshelves.bean;

import android.content.Context;
import android.text.TextUtils;

import com.ahdz.oricalshelves.api.BaseResponse;
import com.ahdz.oricalshelves.api.UserApi;
import com.ahdz.oricalshelves.main.detail.MyWebviewActivity;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ShoreMain {


    /**
     *      MyWebviewActivity.GoToService(context,"");
     */
    public static void showShopDetail(Context context, String id,String title) {
        UserApi.jumpProduction(id, new Observer<BaseResponse<UrlData>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(BaseResponse<UrlData> url ) {
                if ( url.getCode()==2002 && !TextUtils.isEmpty(url.getData().getUrl())) {
                    MyWebviewActivity.GoToService(context,url.getData().getUrl(),0,title);
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
