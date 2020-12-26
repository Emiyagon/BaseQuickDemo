package com.ahdz.oricalshelves.main.auth.fragment.shop;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.ahdz.oricalshelves.api.BaseResponse;
import com.ahdz.oricalshelves.api.UserApi;
import com.ahdz.oricalshelves.bean.Projects;
import com.ahdz.oricalshelves.databinding.FragmentMallBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class FragmentMallPresent {
    private Context context;
    private FragmentMallBinding binding;
    public MutableLiveData<ArrayList<Projects>> projectsList = new MutableLiveData<>();

    public FragmentMallPresent(Context context, FragmentMallBinding mBindingView) {
        this.context = context;
        this.binding = mBindingView;
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


    }
}
