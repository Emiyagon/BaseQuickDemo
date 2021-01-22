package com.ahdz.oricalshelves.download;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ahdz.oricalshelves.BuildConfig;
import com.ahdz.oricalshelves.R;
import com.ahdz.oricalshelves.view.dialog.ComPopupDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Downserver {

    private Context mContext;

    private ProgressBar mProgressBar;
    private TextView tv;
    private String mSavePath;
    private int mProgress =0;
    //  判断是否停止
    private boolean mIsCancel = false;
    private ComPopupDialog mDownloadDialog;


    public void showDownloadServer(Context context,String url) {
        this.mContext = context;

        mDownloadDialog = new ComPopupDialog(context, R.layout.progress_dialog) {
            TextView dismiss;
            @Override
            public void initView() {
                View view = getContentView();
                mProgressBar = view.findViewById(R.id.progress);
                tv = view.findViewById(R.id.tv_sy);
                dismiss = view.findViewById(R.id.tv_dismiss);
            }

            @Override
            public void initEvent() {
                dismiss.setOnClickListener(v -> {
                    mIsCancel = true;
                    dismiss();
                });

            }
        };

        mDownloadDialog.show();
        downloadAPK(url);
        
    }
    /*
     * 开启新线程下载apk文件
     */
    //  版本名称
    private String mVersion_name= BuildConfig.VERSION_NAME;
    private void downloadAPK(String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                        String sdPath = Environment.getExternalStorageDirectory() + "/";
//                      文件保存路径
                        mSavePath = sdPath + "jikedownload";

                        File dir = new File(mSavePath);
                        if (!dir.exists()){
                            dir.mkdir();
                        }
                        // 下载文件
                        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
                        conn.connect();
                        InputStream is = conn.getInputStream();
                        int length = conn.getContentLength();

                        File apkFile = new File(mSavePath, mVersion_name);
                        FileOutputStream fos = new FileOutputStream(apkFile);

                        int count = 0;
                        byte[] buffer = new byte[1024];
                        while (!mIsCancel){
                            int numread = is.read(buffer);
                            count += numread;
                            // 计算进度条的当前位置
                            mProgress = (int) (((float)count/length) * 100);
                            // 更新进度条
                            mUpdateProgressHandler.sendEmptyMessage(1);

                            // 下载完成
                            if (numread < 0){
                                mUpdateProgressHandler.sendEmptyMessage(2);
                                break;
                            }
                            fos.write(buffer, 0, numread);
                        }
                        fos.close();
                        is.close();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 接收消息
     */
    private Handler mUpdateProgressHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    // 设置进度条
                    mProgressBar.setProgress(mProgress);
                    break;
                case 2:
                    // 隐藏当前下载对话框
                    mDownloadDialog.dismiss();
                    // 安装 APK 文件
                    installAPK();
            }
        };
    };


    /*
     * 下载到本地后执行安装
     */
    protected void installAPK() {
        File apkFile = new File(mSavePath, mVersion_name);
        if (!apkFile.exists()){
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
//      安装完成后，启动app（源码中少了这句话）
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.parse("file://" + apkFile.toString());
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        mContext.startActivity(intent);
    }

}
