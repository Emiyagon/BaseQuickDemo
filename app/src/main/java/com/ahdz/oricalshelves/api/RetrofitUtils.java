package com.ahdz.oricalshelves.api;

import com.ahdz.oricalshelves.BuildConfig;
import com.ahdz.oricalshelves.MyApplication;
import com.google.gson.Gson;
import com.readystatesoftware.chuck.ChuckInterceptor;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.Version;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by zeng on 2019/4/9.
 */
public abstract class RetrofitUtils {
    private static  Retrofit mRetrofit = null;
    private static  Retrofit mRetrofit2 = null;
    private static OkHttpClient mOkHttpClient;

    public static final String BASE_URL =  "https://sj.channel.jigame.cn";//
    private static Gson gson ;

    private static Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }

    /**
     * 获取Retrofit对象
     *
     * @return
     */
    public static  Retrofit getRetrofit() {

        if (null == mRetrofit) {
            if (null == mOkHttpClient) {
                OkHttp3Utils okHttp3Utils = new OkHttp3Utils();
                mOkHttpClient = okHttp3Utils.getOkHttpClient();
            }
     mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(mOkHttpClient)
                    .build();
        }
        return mRetrofit;
    }

    /**
     * 获取Retrofit对象
     *这个主要是为了应对多个BaseUrl而准备的
     * @return
     */
    public static  Retrofit getRetrofit2() {
        if (null == mRetrofit2) {
            if (null == mOkHttpClient) {
                OkHttp3Utils okHttp3Utils = new OkHttp3Utils();
                OkHttpClient okHttpClient = new OkHttpClient.Builder()
                        .addInterceptor(new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request request = chain.request()
                                        .newBuilder()
                                        .addHeader("Content-Type", "application/json; charset=UTF-8")
                                .addHeader("User-Agent", Version.userAgent() + " ss/" + BuildConfig.VERSION_NAME+ "")
//                                .addHeader("User-Agent", webView.getSettings().getUserAgentString() + " YouyixinClient/" + BuildConfig.VERSION_NAME)
                                        .build();
                                return chain.proceed(request);
                            }
                        }).sslSocketFactory(TrustAllCerts.createSSLSocketFactory())
                        .retryOnConnectionFailure(false)//  好像是禁止重复请求的方法
                        .addInterceptor(new ChuckInterceptor(MyApplication.getInstance()))//chuck
                        .hostnameVerifier(new TrustAllCerts.TrustAllHostnameVerifier())
                        .connectTimeout(10, TimeUnit.SECONDS).build();
                mOkHttpClient = okHttp3Utils.getOkHttpClient();
            }
            mRetrofit2 = new Retrofit.Builder()
                   // .baseUrl("http://192.168.101.52")
                    .baseUrl(BASE_URL)
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(mOkHttpClient)
                    .build();
        }
        return mRetrofit2;
    }


    public static  class NullOnEmptyConverterFactory extends Converter.Factory {
        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);
            return (Converter<ResponseBody, Object>) body -> {
                if (body.contentLength() == 0) return null;
                return delegate.convert(body);
            };
        }
    }

    /**
     * 封装RequestBody
     */
    public static RequestBody getRequestBody(String str) {
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), str);
    }


    public static String getTimestamp()   {
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", new Date().getTime());
//        getGson().toJson(map);
        return String.valueOf(getGson().toJson(map));
    }

}
