package com.ahdz.oricalshelves.main.auth.fragment.home;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.ahdz.oricalshelves.api.BaseResponse;
import com.ahdz.oricalshelves.api.UserApi;
import com.ahdz.oricalshelves.base.BasePresenter;
import com.ahdz.oricalshelves.bean.BaiHuiData;
import com.ahdz.oricalshelves.bean.Projects;
import com.ahdz.oricalshelves.databinding.FragmentHomeInsBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class FragmentHomePresent extends BasePresenter {

    private Context context;
    private FragmentHomeInsBinding binding;

    public MutableLiveData<ArrayList<Projects>> projectsList = new MutableLiveData<>();
    public MutableLiveData<ArrayList<String>> gbList = new MutableLiveData<>();
    public MutableLiveData<BaiHuiData> baihui = new MutableLiveData<>();

    public FragmentHomePresent(Context context, FragmentHomeInsBinding binding) {
        this.context = context;
        this.binding = binding;
    }

    public static void main(String[] args) {
        System.out.println(displayWithComma(85800+""));
    }

    public static String displayWithComma(String str)  {
        String  str1  =new  StringBuffer(str).reverse().toString(); // 先将字符串颠倒顺序
        String str2 = "";
        // 每三位取一长度
        int size =  str.length() % 3 == 0 ?  str.length() / 3: str.length() / 3 + 1 ;

        /*
         * 比如把一段字符串分成n段,第n段可能不是三个数,有可能是一个或者两个,
         * 现将字符串分成两部分.一部分为前n-1段,第二部分为第n段.前n-1段，每一段加一",".而第n段直接取出即可
         */
        for (int i = 0 ;i < size - 1;i++) { // 前n-1段
            str2 += str1.substring(i * 3, i * 3 + 3) + ",";
        }
        for ( int i = size - 1 ;i< size;i++) { // 第n段
            str2 += str1.substring(i * 3, str.length());
        }
        str2 = new StringBuffer(str2).reverse().toString();
        return str2;
    }


    //  下方列表
    public void OnHttp() {


        Map<String, Object> map = new HashMap<>();
        map.put("current", 1);
        map.put("size", 50);
        // projectsList
        UserApi.projectsList(map, new Observer<BaseResponse<ArrayList<Projects>>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(BaseResponse<ArrayList<Projects>> bean) {
                if (bean.getCode() == 200) {
                    projectsList.setValue(bean.getData());
                    binding.srf.finishLoadMore();
                    binding.srf.finishRefresh();
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        TopHttp();

    }

    //  上方数字
    public void TopHttp() {
        UserApi.getBaiHui(new Observer<BaseResponse<BaiHuiData>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(BaseResponse<BaiHuiData> data) {
                if (data.getCode() == 200) {
                    baihui.setValue(data.getData());
                    binding.srf.finishLoadMore();
                    binding.srf.finishRefresh();
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


    /**
     *   广播
     */
    public void GbHttp() {
        UserApi.getNoticePull(new Observer<BaseResponse<ArrayList<String>>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(BaseResponse<ArrayList<String>> listBase) {
                if (listBase.getCode() == 200) {
                    gbList.setValue(listBase.getData());
                    binding.srf.finishLoadMore();
                    binding.srf.finishRefresh();
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