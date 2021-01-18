package com.ahdz.oricalshelves.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.ahdz.oricalshelves.MyApplication;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PhoneUtil {


    /**
     *   验证是否是手机号
     */
    public static boolean isPhone(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
//            MToast.showToast("手机号应为11位数");
            Toast.makeText(MyApplication.getInstance(),"请输入11位的手机号",Toast.LENGTH_SHORT).show();
//            Toast.showToastCenter("请输入11位的手机号");
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            LogUtil.e(isMatch+"");
            if (!isMatch) {
//                ToastUtils.showToastCenter("请填入正确的手机号");
                Toast.makeText(MyApplication.getInstance(),"请填入正确的手机号",Toast.LENGTH_SHORT).show();
            }
            return isMatch;
        }
    }




    //获取当前的时间戳
    public static int getTime() {
        return Integer.parseInt(System.currentTimeMillis() / 1000 + "");
    }
    /**
     * 服务器返回url，通过url去获取视频的第一帧
     * Android 原生给我们提供了一个MediaMetadataRetriever类
     * 提供了获取url视频第一帧的方法,返回Bitmap对象
     *
     * @param videoPath
     * @return
     */
    public static Bitmap getNetVideoBitmap(String type, String videoPath) {
        Bitmap bitmap = null;
        if (type.equals("Local")) {
            MediaMetadataRetriever media = new MediaMetadataRetriever();
            media.setDataSource(videoPath);
// 视频封面
            bitmap = media.getFrameAtTime();
            return bitmap;
        } else if (type.equals("Net")) {
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            try {
                //根据url获取缩略图
                retriever.setDataSource(videoPath, new HashMap());
                //获得第一帧图片
                bitmap = retriever.getFrameAtTime();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } finally {
                retriever.release();
            }
            return bitmap;
        }
        return bitmap;
    }

    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return  语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return  系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }
    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }


    /**
     * 获取手机厂商
     *
     * @return  手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     *  获取UA
     * @return
     */
//    public String getUserAgent(){
//        String user_agent = ProductProperties.get(ProductProperties.USER_AGENT_KEY, null);
//        return user_agent;
//    }


    /**
     * 获取当前ip地址
     *
     * @param context
     * @return
     */
    public static String getLocalIpAddress(Context context) {
        try {

            WifiManager wifiManager = (WifiManager) context
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int i = wifiInfo.getIpAddress();
            return int2ip(i);
        } catch (Exception ex) {
            return "Error obtaining IP" + ex.getMessage();
        }
        // return null;
    }
    /**
     * 将ip的整数形式转换成ip形式
     *
     * @param ipInt
     * @return
     */
    public static String int2ip(int ipInt) {
        StringBuilder sb = new StringBuilder();
        sb.append(ipInt & 0xFF).append(".");
        sb.append((ipInt >> 8) & 0xFF).append(".");
        sb.append((ipInt >> 16) & 0xFF).append(".");
        sb.append((ipInt >> 24) & 0xFF);
        return sb.toString();
    }



    public static Integer getHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        int heightPixels = outMetrics.heightPixels;
        return heightPixels;
    }


    public static Integer getWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        int widthPixels = outMetrics.widthPixels;
        return widthPixels;
    }


    /**
     * 获取设备拨号运营商
     *
     * @return ["中国电信CTCC":3]["中国联通CUCC:2]["中国移动CMCC":1]["other":0]["无sim卡":-1]
     */
    public static int getSubscriptionOperatorType(Context context) {
        int opeType = -1;
        // No sim
        if (!hasSim(context)) {
            return opeType;
        }
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String operator = tm.getNetworkOperator();

        if ("46001".equals(operator) || "46006".equals(operator) || "46009".equals(operator)) {
            opeType = 2;// 中国联通

        } else if ("46000".equals(operator) || "46002".equals(operator) || "46004".equals(operator) || "46007".equals(operator)) {
            opeType = 1;// 中国移动

        } else if ("46003".equals(operator) || "46005".equals(operator) || "46011".equals(operator)) {
            opeType = 3;  // 中国电信
        } else {
            opeType = 0;
        }
        return opeType;
    }

    /**
     * 获取设备蜂窝网络运营商
     *
     * @return ["中国电信CTCC":3]["中国联通CUCC:2]["中国移动CMCC":1]["other":0]["无sim卡":-1]["数据流量未打开":-2]
     */
    public static int getCellularOperatorType(Context context) {
        int opeType = -1;
        // No sim
        if (!hasSim(context)) {
            return opeType;
        }
        // Mobile data disabled
        if (!isMobileDataEnabled(context)) {
            opeType = -2;
            return opeType;
        }
        // Check cellular operator
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String operator = tm.getSimOperator();
        // 中国联通
        if ("46001".equals(operator) || "46006".equals(operator) || "46009".equals(operator)) {
            opeType = 2;
            // 中国移动
        } else if ("46000".equals(operator) || "46002".equals(operator) || "46004".equals(operator) || "46007".equals(operator)) {
            opeType = 1;
            // 中国电信
        } else if ("46003".equals(operator) || "46005".equals(operator) || "46011".equals(operator)) {
            opeType = 3;
        } else {
            opeType = 0;
        }
        return opeType;
    }



    /**
     * 判断数据流量开关是否打开
     *
     * @param context
     * @return
     */
    public static boolean isMobileDataEnabled(Context context) {
        try {
            Method method = ConnectivityManager.class.getDeclaredMethod("getMobileDataEnabled");
            method.setAccessible(true);
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            return (Boolean) method.invoke(connectivityManager);
        } catch (Throwable t) {
            Log.d("isMobileDataEnabled", "Check mobile data encountered exception");
            return false;
        }
    }

    /**
     * 检查手机是否有sim卡
     */
    public static boolean hasSim(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String operator = tm.getSimOperator();
        if (TextUtils.isEmpty(operator)) {
            return false;
        }
        return true;
    }

    }
