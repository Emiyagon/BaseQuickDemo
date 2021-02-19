package com.ahdz.oricalshelves.api;


import com.ahdz.oricalshelves.bean.BaiHuiData;
import com.ahdz.oricalshelves.bean.BannerModel;
import com.ahdz.oricalshelves.bean.DeviceIdModel;
import com.ahdz.oricalshelves.bean.DeviceIdRequest;
import com.ahdz.oricalshelves.bean.LoginModel;
import com.ahdz.oricalshelves.bean.NewRegiterDevice;
import com.ahdz.oricalshelves.bean.Projects;
import com.ahdz.oricalshelves.bean.RegiterDeviceRequest;
import com.ahdz.oricalshelves.bean.SecretData;
import com.ahdz.oricalshelves.bean.UrlData;
import com.ahdz.oricalshelves.bean.UserInfoData;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;


/**
 * Created by zeng on 2019/4/9.
 */

public class UserApi extends BaseNetWork {
    //.getShared().getBoolean(Constants.SP.showStoreUI,true)

    protected static final NetService service = getRetrofit().create(NetService.class);
    private static Gson gson = new Gson();

    public interface NetService {


        @GET("/user/api/auth/user-auth/yes-or-no")
        Observable<BaseResponse<Boolean>> getMegCertificationB();

        @POST("/user/api/order/pick")
        Observable<BaseResponse<String>> toPickBtn(@Body RequestBody requestBody);

        /*用户登录*/
        @POST("/user/api/user/login/sms/shangjia")
        Observable<BaseResponse<LoginModel>> userLogin(@Body RequestBody requestBody);


        /*产品列表*/
        @POST("/foundation/api/sys/param/productions")
        Observable<BaseResponse<ArrayList<Projects>>> projectsList(@Body RequestBody requestBody);

        /**
         * 首页
         */
        @POST("/foundation/api/sys/param/baihui")
        Observable<BaseResponse<BaiHuiData>> getBaiHui();


        /**
         * bannerCash
         */
        @POST("/foundation/api/sys/param/bannerCache")
        Observable<BaseResponse<String>> getBannerCache(@Body RequestBody body);
        /**
         * 首页跳转
         */
        @POST("/user/api/rights/apply")
//        @POST("/user/api/rights/applyNew")
        Observable<BaseResponse<UrlData>> toApply(@Body RequestBody requestBody);
        /**
         *   广播
         * @param requestBody
         * @return
         */
        @POST("/foundation/api/notice/pull")
        Observable<BaseResponse<ArrayList<String>>> getNoticePull(@Body RequestBody requestBody);

        /**
         * Mine页获取用户信息
         */
        @POST("/user/api/user/mine")
        Observable<BaseResponse<UserInfoData>> getUserInfo(@Body RequestBody requestBody);


        /**
         *
         *  我的 显示贷款记录
         */
        @POST("/user/api/user/findUserLoanState")
        Observable<BaseResponse<String>>  getShowDkjl (@Body RequestBody requestBody);

        //登出
        @POST("/user/api/user/logout")
        Observable<BaseResponse<String>> toLogout(@Body RequestBody requestBody);


        //用户隐私协议
        @POST("/foundation/api/protocol/secret")
        Observable<BaseResponse<SecretData>> getSecretProtocol(@Body RequestBody requestBody);

        /**
         * 生成设备id
         */
        @POST("/foundation/api/reg/gid")
        Observable<BaseResponse<DeviceIdModel>> getDeviceId(@Body DeviceIdRequest requestBody);
        /**
         * 注册设备信息
         */
        @POST("/foundation/api/reg/devices")
        Observable<BaseResponse<String>> registerDevice(@Body RegiterDeviceRequest request);
        /**
         * 注册设备信息2
         */
        @POST("/user/api/user/installed")
        Observable<BaseResponse<String>> registerDevice2(@Body NewRegiterDevice request);


        // 产品跳转
        @POST("/foundation/api/sys/param/apply")
        Observable<BaseResponse<UrlData>> jumpProduction(@Body RequestBody requestBody);


        /**
         * 首页banner
         */
        @POST("/foundation/api/sys/param/homeBanner")
        Observable<BaseResponse<List<BannerModel>>> getBannerList();
    }

  /*
  public static void insertMyAddressObtain(RequestBody body,Observer<BaseResponse<String>>observer){
        setSubscribe(service.insertMyAddressObtain(body),observer);
    }*/

    public static void getBannerCache(Map<String,Object> map,Observer<BaseResponse<String>> observer){
        setSubscribe(service.getBannerCache(RetrofitUtils.getRequestBody(gson.toJson(map))),observer);
    }
    public static void toApply(RequestBody request, Observer<BaseResponse<UrlData>> observer) {
        setSubscribe(service.toApply(request), observer);
    }

    // 产品跳转
    public static void jumpProduction(String id, Observer<BaseResponse<UrlData>> observer) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        setSubscribe(service.jumpProduction(RetrofitUtils.getRequestBody(gson.toJson(map))), observer);
    }


    /**
     * 注册设备信息2
     * @param request
     * @param observer
     */
    public static void registerDevice2(NewRegiterDevice request, Observer<BaseResponse<String>> observer) {
        setSubscribe(service.registerDevice2(request), observer);
    }


    /**
     *  注册设备信息
     * @param request
     * @param observer
     */
    public static void registerDevice(RegiterDeviceRequest request, Observer<BaseResponse<String>> observer) {
        setSubscribe(service.registerDevice(request), observer);
    }

    public static void getDeviceId(DeviceIdRequest request, Observer<BaseResponse<DeviceIdModel>> observer) {
        setSubscribe(service.getDeviceId(request), observer);
    }


    // 用户登录
    public static void userLogin(Map<String, Object> map, Observer<BaseResponse<LoginModel>> observer) {
        setSubscribe(service.userLogin(RetrofitUtils.getRequestBody(gson.toJson(map))), observer);
    }


    /**
     *   首页
     * @param observer
     */
    public static void getBaiHui(Observer<BaseResponse<BaiHuiData>> observer) {
        setSubscribe(service.getBaiHui(), observer);
    }




    /**
     *   产品列表
     * @param map
     * @param observer
     */
    public static void projectsList(Map<String, Object> map, Observer<BaseResponse<ArrayList<Projects>>> observer) {
        setSubscribe(service.projectsList(RetrofitUtils.getRequestBody(gson.toJson(map))), observer);
    }


    /**
     *    首页banner
     * @param observer
     */
    public static void getBannerList(Observer<BaseResponse<List<BannerModel>>> observer){
        setSubscribe(service.getBannerList(),observer);
    }


    /**
     *   用户协议
     * @param map
     * @param observer
     */
    public static void getSecretProtocol(Map<String, Object> map, Observer<BaseResponse<SecretData>> observer) {
        setSubscribe(service.getSecretProtocol(RetrofitUtils.getRequestBody(gson.toJson(map))), observer);
    }



    public static void getShowDkjl(Observer<BaseResponse<String>> observer) {
        setSubscribe(service.getShowDkjl(RetrofitUtils.getRequestBody(RetrofitUtils.getTimestamp())), observer);
    }


    /**
     *   个人信息

     * @param observer
     */
    public static void getUserInfo(Observer<BaseResponse<UserInfoData>> observer) {
        setSubscribe(service.getUserInfo(RetrofitUtils.getRequestBody(RetrofitUtils.getTimestamp())), observer);
    }



    /**
     *   退出登录

     * @param observer
     */
    public static void toLogout(Observer<BaseResponse<String>> observer) {
        setSubscribe(service.toLogout(RetrofitUtils.getRequestBody(RetrofitUtils.getTimestamp())), observer);
    }



    /**
     *   广播
     * @param observer
     */
    public static void getNoticePull(Observer<BaseResponse<ArrayList<String>>> observer) {
        setSubscribe(service.getNoticePull(RetrofitUtils.getRequestBody(RetrofitUtils.getTimestamp())), observer);
    }



}

