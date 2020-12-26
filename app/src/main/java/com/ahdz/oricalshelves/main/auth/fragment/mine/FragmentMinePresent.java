package com.ahdz.oricalshelves.main.auth.fragment.mine;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.ahdz.oricalshelves.api.BaseResponse;
import com.ahdz.oricalshelves.api.UserApi;
import com.ahdz.oricalshelves.base.BasePresenter;
import com.ahdz.oricalshelves.bean.Projects;
import com.ahdz.oricalshelves.bean.UserInfoData;
import com.ahdz.oricalshelves.databinding.FragmentMineBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class FragmentMinePresent extends BasePresenter {
    private Context context;
    private FragmentMineBinding binding;
    public MutableLiveData<ArrayList<Projects>> projectsList = new MutableLiveData<>();
    public MutableLiveData<UserInfoData> userInfo = new MutableLiveData<>();

    public FragmentMinePresent(Context context, FragmentMineBinding binding) {
        this.context = context;
        this.binding = binding;
    }


    // 个人信息
    public void MineHttp() {
        UserApi.getUserInfo(new Observer<BaseResponse<UserInfoData>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(BaseResponse<UserInfoData> date) {
                if (date.getCode() == 200) {
                    userInfo.setValue(date.getData());
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
