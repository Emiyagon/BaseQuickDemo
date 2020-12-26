package com.ahdz.oricalshelves.api;


import android.Manifest;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import android.webkit.WebSettings;
import androidx.annotation.RequiresPermission;

import com.ahdz.oricalshelves.MyApplication;
import com.ahdz.oricalshelves.util.AppUtils;
import com.ahdz.oricalshelves.util.SPUtil;
import com.readystatesoftware.chuck.ChuckInterceptor;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.CacheControl;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zeng on 2019/4/9.
 */

public class OkHttp3Utils {
    private OkHttpClient mOkHttpClient;
    /**
     * 获取OkHttpClient对象
     *
     * @return
     */
    public OkHttpClient getOkHttpClient() {

        if (null == mOkHttpClient) {

            //同样okhttp3后也使用build设计模式
            mOkHttpClient = new OkHttpClient.Builder()
                    //添加拦截器
                    .addInterceptor(new MyIntercepter())
                    //设置一个自动管理cookies的管理器
                    .cookieJar(new CookiesManager())
                    //添加网络连接器
                    //.addNetworkInterceptor(new CookiesInterceptor(BaseApplication.getInstance().getApplicationContext()))
                    //设置请求读写的超时时间
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
//                    .cache(cache)//设置缓存
                    .addInterceptor(new ChuckInterceptor(MyApplication.getInstance()))//chuck
                    .retryOnConnectionFailure(true)//自动重试(TODO 要是想重新请求上一个接口，这里设置成true)
                    .build();
        }
        return mOkHttpClient;
    }

    /**
     * 拦截器
     */
    private class MyIntercepter implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            if (!isNetworkReachable(MyApplication.getInstance())) {
          //      updateHandler.sendEmptyMessage(300);
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)//无网络时只从缓存中读取
                        .build();
            }

            Request.Builder RequestBuilder = request.newBuilder();
            Request build;
            /*String token = "";
            String phoneIMEI="";
            int permissionGranted = PackageManager.PERMISSION_GRANTED;
            if (activity.getPackageManager().checkPermission(Manifest.permission.READ_PHONE_STATE, activity.getPackageName()) == permissionGranted) {
                phoneIMEI = SystemToolUtils.getPhoneIMEI(AppManager.topActivity());//设备唯一识别标识
            }*/
        /*    if (Build.VERSION.SDK != null && Build.VERSION.SDK_INT > 13) {
                RequestBuilder.addHeader("Connection", "close");
            }*/
            build = RequestBuilder
                    .addHeader("accept-language", "en-IN")
                    .addHeader("x-client-token", SPUtil.getUserToken(MyApplication.getInstance()))
                    .addHeader("x-client-jietiao-token", "")
                    .addHeader("currentVersion", String.valueOf(AppUtils.getVersionCode(MyApplication.getInstance())))
                    .build();

            Log.e("wwb", "intercept: "+SPUtil.getUserToken(MyApplication.getInstance()));
            Response response = chain.proceed(build);
            int code = response.code();
            //对个别链接地址做处理（比如要对个别网络请求做特殊的拦截处理）
            HttpUrl url = response.request().url();
//            System.out.println("我的网址"+url);
            Log.e("okhttp", "我的网址"+url);
         //   updateHandler.sendEmptyMessage(code);
            if (code == 401) {
                //跳转到登录页面
//                updateHandler.sendEmptyMessage(401);
            } else if (code == 402) {
                //跳转到开户审核中界面
        //        updateHandler.sendEmptyMessage(402);
            } else if (code == 403) {
                //跳转到开户界面
      //          updateHandler.sendEmptyMessage(403);
            }
            return response;
        }
    }

    /**
     * 自动管理Cookies
     */
    private class CookiesManager implements CookieJar {
        private final PersistentCookieStore cookieStore = new PersistentCookieStore(MyApplication.getInstance().getApplicationContext());

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            if (cookies != null && cookies.size() > 0) {
                for (Cookie item : cookies) {
                    cookieStore.add(url, item);
                }
            }
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(url);
            return cookies;
        }
    }

    /**
     * 判断网络是否可用
     *
     * @param context Context对象
     */
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    public Boolean isNetworkReachable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = cm.getActiveNetworkInfo();
        if (current == null) {
            return false;
        }
        return (current.isAvailable());
    }
}
