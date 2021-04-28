package com.ahdz.oricalshelves;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.ahdz.oricalshelves.main.MainActivity;
import com.ahdz.oricalshelves.util.NoDoubleClick;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import cat.ereza.customactivityoncrash.activity.DefaultErrorActivity;
import cat.ereza.customactivityoncrash.config.CaocConfig;
import com.ahdz.oricalshelves.BuildConfig;

public class MyApplication extends MultiDexApplication {
    public static MyApplication myApp;


    public static MyApplication getInstance() {
        return myApp;
    }


    //  app渠道号,这个应该是在build.gradle里面配置的,这里写死了就行
    public static String APP_LOCAL_CHANNELCODE = BuildConfig.FLAVOR;

    static {
        //启用矢量图兼容
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        //设置全局默认配置（优先级最低，会被其他设置覆盖）

    /*    SmartRefreshLayout.setDefaultRefreshInitializer((context, layout) -> {
            //全局设置（优先级最低）
            layout.setEnableAutoLoadMore(false);
            layout.setEnableOverScrollDrag(false);
            layout.setEnableOverScrollBounce(true);
            layout.setEnableLoadMoreWhenContentNotFull(true);
            layout.setEnableScrollContentWhenRefreshed(true);
            layout.setEnableLoadMore(true);
            layout.setPrimaryColorsId(R.color.transparent, android.R.color.black);
        });*/


        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            //  MaterialHeader

//            ClassicsHeader header = new ClassicsHeader(context);
//            header.setEnableLastTime(true);
//            header.setTextSizeTitle(14);
//            header.setTimeFormat(new DynamicTimeFormat("更新于 %s"));
//            header.setBackgroundColor(Color.TRANSPARENT);

            MaterialHeader header = new MaterialHeader(context);
            header.setColorSchemeColors(myApp.getResources().getColor(R.color.actionsheet_red)
                    ,myApp.getResources().getColor(R.color.actionsheet_blue)
                    ,myApp.getResources().getColor(R.color.orange_login));

//                header.setArrowResource(R.mipmap.logo);
//                header.setProgressResource(R.mipmap.logo);
            return header;
        });
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            return new ClassicsFooter(context);
        });

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        myApp=this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new NoDoubleClick.lifecycleCallbacks());//防止恶意多次点击
        MultiDex.install(this);

        //   LiveEventBus配置
        LiveEventBus.get()
                .config()
                .supportBroadcast(this)
                .lifecycleObserverAlwaysActive(true);
        OnHit();
    }

    /**
     *  Android检测程序崩溃框架CustomActivityOnCrash
     */
    @SuppressLint("RestrictedApi")
    private void OnHit() {
        //整个配置属性，可以设置一个或多个，也可以一个都不设置
        CaocConfig.Builder.create()
                //程序在后台时，发生崩溃的三种处理方式
                //BackgroundMode.BACKGROUND_MODE_SHOW_CUSTOM: //当应用程序处于后台时崩溃，也会启动错误页面，
                //BackgroundMode.BACKGROUND_MODE_CRASH:      //当应用程序处于后台崩溃时显示默认系统错误（一个系统提示的错误对话框），
                //BackgroundMode.BACKGROUND_MODE_SILENT:     //当应用程序处于后台时崩溃，默默地关闭程序！这种模式我感觉最好
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT)
                .enabled(true)     //这阻止了对崩溃的拦截,false表示阻止。用它来禁用customactivityoncrash框架
                .showErrorDetails(true) //这将隐藏错误活动中的“错误详细信息”按钮，从而隐藏堆栈跟踪。
                .showRestartButton(true)    //是否可以重启页面
                .trackActivities(true)     //错误页面中显示错误详细信息
                .minTimeBetweenCrashesMs(2000)      //定义应用程序崩溃之间的最短时间，以确定我们不在崩溃循环中。比如：在规定的时间内再次崩溃，框架将不处理，让系统处理！
                .errorDrawable(R.mipmap.ic_launcher)     //崩溃页面显示的图标
                .restartActivity(MainActivity.class)      //重新启动后的页面(最好是闪屏页面,这样可塑性强,也不会出太多问题)
                .errorActivity(DefaultErrorActivity.class) //程序崩溃后显示的页面,可以自定义
                .eventListener(new CustomEventListener())//设置监听
                .apply();
        //如果没有任何配置，程序崩溃显示的是默认的设置
//        CustomActivityOnCrash.install(this);
    }

    /**
     * 监听程序崩溃/重启
     */
    private static final String TAG = "TAG";

    private static class CustomEventListener implements CustomActivityOnCrash.EventListener {
        //程序崩溃回调
        @Override
        public void onLaunchErrorActivity() {
            Log.e(TAG, "onLaunchErrorActivity()");
        }

        //重启程序时回调
        @Override
        public void onRestartAppFromErrorActivity() {
            Log.e(TAG, "onRestartAppFromErrorActivity()");
        }

        //在崩溃提示页面关闭程序时回调
        @Override
        public void onCloseAppFromErrorActivity() {
            Log.e(TAG, "onCloseAppFromErrorActivity()");
        }

    }


}
