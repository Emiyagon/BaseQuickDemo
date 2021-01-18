package com.ahdz.oricalshelves.util;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.ahdz.oricalshelves.MyApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static android.content.Context.SENSOR_SERVICE;

public class VerifyDeviceUtil {
    public static boolean isSuccess = false;// 默认不是模拟器,返回true就是模拟器


    public static void main(String[] args) {

    }

    /**
     *
     * @param context
     * @return  true 模拟器   false 真机
     */
    public static boolean isSimulator(Context context) {
        String url = "tel:" + "123456";
        Intent intent = new Intent();
        intent.setData(Uri.parse(url));
        intent.setAction(Intent.ACTION_DIAL);
        // 是否可以处理跳转到拨号的 Intent
        boolean canCallPhone = intent.resolveActivity(context.getPackageManager()) != null;

        // 该判定方法经过一段时间的使用，发现部分华为手机（华为折叠屏、华为mate30保时捷版）会出现真机被判定为模拟器的情况
        //在之前方法的基础上，如果得出是模拟器，并且可以拨打电话，并且手机品牌是华为，并且有光传感器，将结果改为真机
        if (canCallPhone && Build.BRAND.equalsIgnoreCase("HUAWEI") && hasLightSensor(context))
            return true;

        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.toLowerCase().contains("vbox")
                || Build.FINGERPRINT.toLowerCase().contains("test-keys")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("MuMu")
                || Build.MODEL.contains("virtual")
                || Build.SERIAL.equalsIgnoreCase("android")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT)
                || ((TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE)).getNetworkOperatorName().toLowerCase().equals("android")
                || !canCallPhone;
    }

    /**
     * .通过光传感器来判断
     * 大多数手机都是有光传感器的，模拟器都没有光传感器。
     * @param context
     * @return
     */
    public static boolean hasLightSensor(Context context)
    {
        SensorManager sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT); //光
        if (sensor == null)
            return true;
        else
            return false;
    }

    /**
     * 模拟器验证结果
     */
    public static boolean verify( ) {
        Context context = MyApplication.getInstance();
        if (notHasBlueTooth()){
            return  true;
        }else if (notHasLightSensorManager(context)){
            return  true;
        }else if (ifFeatures()){
            return  true;
        }else if (checkIsNotRealPhone()){
            return  true;
        }
        return false;
    }

    /**
     * 判断蓝牙是否有效
     */
    private static boolean notHasBlueTooth(){
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null){
            return true;
        }else{
            // 如果蓝牙不一定有效的。获取蓝牙名称，若为 null 则默认为模拟器
            String name = bluetoothAdapter.getName();
            if (TextUtils.isEmpty(name)){
                return true;
            }else{
                return false;
            }
        }
    }

    /**
     * 依据是否存在 光传感器 来判断是否为模拟器
     * @param context
     * @return
     */
    private static boolean notHasLightSensorManager(Context context){
        SensorManager sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (sensor == null){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 根据部分特征参数设备信息来判断是否为模拟器
     * @return
     */
    private static boolean ifFeatures(){
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.toLowerCase().contains("vbox")
                || Build.FINGERPRINT.toLowerCase().contains("test-keys")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                ||(Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT);
    }

    /*
     *根据CPU是否为电脑来判断是否为模拟器
     *返回:true 为模拟器
     */
    private static boolean checkIsNotRealPhone() {
        String cpuInfo = readCpuInfo();
        if ((cpuInfo.contains("intel") || cpuInfo.contains("amd"))) {
            return true;
        }
        return false;
    }

    /**
     * 根据 CPU 是否为电脑来判断是否为模拟器
     * @return
     */
    private static String readCpuInfo(){
        String result = "";
        try{
            String [] args = {"/system/bin/cat","/proc/cpuinfo"};
            ProcessBuilder processBuilder = new ProcessBuilder(args);
            Process process = processBuilder.start();
            StringBuffer stringBuffer = new StringBuffer();
            String readLine = "";
            BufferedReader responseReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "utf-8"));
            while ((readLine = responseReader.readLine())!=null){
                stringBuffer.append(readLine);
            }
            responseReader.close();
            result = stringBuffer.toString().toLowerCase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
