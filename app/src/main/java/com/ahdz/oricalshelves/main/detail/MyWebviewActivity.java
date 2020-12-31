package com.ahdz.oricalshelves.main.detail;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ahdz.oricalshelves.BuildConfig;
import com.ahdz.oricalshelves.R;
import com.ahdz.oricalshelves.base.BaseActivity;
import com.ahdz.oricalshelves.databinding.ActivityMyWebviewBinding;
import com.ahdz.oricalshelves.util.BaseUtil;

public class MyWebviewActivity extends BaseActivity<ActivityMyWebviewBinding> {

    private WebView webView;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_my_webview;
    }


    /**
     *
     * @param context
     * @param url
     * @param type  0是链接 1是加密之后的文本
     * @param title
     */
    public static void GoToService(Context context,String  url,int type,String title) {
        context.startActivity(new Intent(context,MyWebviewActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                .putExtra("url",url).putExtra("type",type).putExtra("title",title));
    }
    @Override
    protected void initData() {
        mBindingView.titleView.tvTitle.setText(getIntent().getStringExtra("title"));

        mBindingView.titleView.llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        webView = mBindingView.webView;
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true); // 设置允许JS弹窗
        /**
         *   设置UA
         */
//        String ua = webView.getSettings().getUserAgentString();//原来获取的UA
//        webView.getSettings().setUserAgentString(ua + "\tChaoQiClient/" + BuildConfig.VERSION_NAME);//添加
//        webView.getSettings().setUserAgentString(ua.replace("Android","ChaoQiClient"));//替换


        settings.setDomStorageEnabled(true);
        //设置自适应屏幕，两者合用
        settings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        settings.setLoadsImagesAutomatically(true);    //支持自动加载图片
        settings.setAllowFileAccess(true);    // 可以读取文件缓存
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        String appCachePath = this.getApplicationContext().getCacheDir().getAbsolutePath();
        settings.setAppCachePath(appCachePath);
        settings.setAppCacheEnabled(true);    //开启H5(APPCache)缓存功能

        // webView.setDefaultHandler(new DefaultHandler());
        //缩放操作
        settings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        settings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        settings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        if (getIntent().getIntExtra("type",0)==0){
            webView.loadUrl(getIntent().getStringExtra("url"));
        }else {
            webView.loadDataWithBaseURL(null, BaseUtil.ungzipString(getIntent().getStringExtra("url")) , "text/html", "utf-8", null);

        }

        if (Build.VERSION.SDK_INT >= 19) {
            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView webView, String title) {
                super.onReceivedTitle(webView, title);
                Log.e("h5传递过来的数据", title + "//");
                //TODO   关闭等待
                super.onReceivedTitle(webView, title);

            }

            @Override
            public void onProgressChanged(WebView webView, int i) {
                //显示进度条
                super.onProgressChanged(webView, i);
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView webView, String s) {

                super.onPageFinished(webView, s);

            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 拦截 url 跳转,在里边添加点击链接跳转或者操作
//                view.loadUrl(url);
                Log.e("GAME-SOULoading", url);

//                return true;
                return false;
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view,
                                                              WebResourceRequest request) {
                // 在每一次请求资源时，都会通过这个函数来回调
//                Log.e("GAME-WRResponse", request.getUrl().toString());
//                if (request.getUrl().toString().equals(endUrl))



                return super.shouldInterceptRequest(view, request);
            }



            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();
            } else {
                finish();
            }

        }
        return true;
    }
}
