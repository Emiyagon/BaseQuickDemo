package com.ahdz.oricalshelves.main.auth.activity.setting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.ahdz.oricalshelves.MyApplication;
import com.ahdz.oricalshelves.R;
import com.ahdz.oricalshelves.api.BaseResponse;
import com.ahdz.oricalshelves.api.UserApi;
import com.ahdz.oricalshelves.base.BaseActivity;
import com.ahdz.oricalshelves.databinding.ActivitySettingBinding;
import com.ahdz.oricalshelves.main.auth.activity.complaints.ComplaintsActivity;
import com.ahdz.oricalshelves.main.login.LoginActivity;
import com.ahdz.oricalshelves.util.AppUtils;
import com.ahdz.oricalshelves.util.FileCacheUtils;
import com.ahdz.oricalshelves.util.SPUtil;
import com.ahdz.oricalshelves.view.dialog.ActionSheetDialog;
import com.ahdz.oricalshelves.view.dialog.MyAlertDialog;
//import com.lzy.forempty.tools.PictureSelector;
//import com.lzy.forempty.tools.config.PictureConfig;
//import com.lzy.forempty.tools.config.PictureMimeType;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class SettingActivity extends BaseActivity<ActivitySettingBinding> {


    private SettingPresent present;


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                default:
                    break;
                case 0x21:
                    dismissDialog();
                    mBindingView.tvHc.setText("0.00KB");
                    showToast("清除成功!");
                    break;
            }
        }
    };
    @Override
    protected int setLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initData() {


        present = new SettingPresent(this, mBindingView);
        mBindingView.titleView.tvTitle.setText("设置");
        mBindingView.titleView.llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mBindingView.tvBbh.setText(String.format("V-%s", AppUtils.getVersionName(SettingActivity.this)));
        try {
            mBindingView.tvHc.setText(FileCacheUtils.getTotalCacheSize(SettingActivity.this));
        } catch (Exception e) {
            e.printStackTrace();
            mBindingView.tvHc.setText("0.00KB");
        }

        mBindingView.llTo.setOnClickListener(v -> {
            startActivity(new Intent(SettingActivity.this, ComplaintsActivity.class));
        });

        mBindingView.llAbout.setOnClickListener(v -> {

            /*PictureSelector.create(this)
                    .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    .theme( R.style.picture_QQ_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                    .maxSelectNum(9)// 最大图片选择数量
                    .minSelectNum(1)// 最小选择数量
                    .imageSpanCount(5)// 每行显示个数
                    .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                    .previewImage(true)// 是否可预览图片
                    .isCamera(true)// 是否显示拍照按钮
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
//                    .setOutputCameraPath(photoPath)// 自定义拍照保存路径
                    .enableCrop(false)// 是否裁剪
                    .compress(false)// 是否压缩
                    .synOrAsy(true)//同步true或异步false 压缩 默认同步
//                    .compressSavePath(getPath())//压缩图片保存地址
                    //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                    .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
                    .withAspectRatio(3, 4)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
                    .hideBottomControls(false)// 是否显示uCrop工具栏，默认不显示
                    .isGif(false)// 是否显示gif图片
                    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
                    .circleDimmedLayer(false)// 是否圆形裁剪
                    .showCropFrame(true)// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
                    .showCropGrid(true)// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                    .openClickSound(true)// 是否开启点击声音
//                    .selectionMedia(selectList)// 是否传入已选图片
                    .previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                    .cropCompressQuality(90)// 裁剪压缩质量 默认100
                    .minimumCompressSize(100)// 小于100kb的图片不压缩
                    //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                    .rotateEnabled(true) // 裁剪是否可旋转图片
                    .scaleEnabled(true)// 裁剪是否可放大缩小图片
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code*/

        });

        mBindingView.llQc.setOnClickListener(v -> {


            new MyAlertDialog(SettingActivity.this)
                    .builder()
                    .setTitle("提示")
                    .setMsg("是否清除缓存?")
                    .setCancelable(true)
                    .setNegativeButton("取消", v1 -> {

                    })
                    .setPositiveButton("确认", v12 -> {
                        FileCacheUtils.clearAllCache(SettingActivity.this);
                        showDialog("清除中...",1);
                        Message message = new Message();
                        message.what = 0x21;
                        handler.sendMessageAtTime(message, 2100);
                    })
                    .show();


        });


        mBindingView.stvLogout.setOnClickListener(v -> {

            new ActionSheetDialog(SettingActivity.this)
                    .builder()
                    .setTitle("退出登录？")
                    .setCancelable(false)
                    .setCanceledOnTouchOutside(true)
                    .addSheetItem("是", ActionSheetDialog.SheetItemColor.Blue
                            , which -> {
                                Intent intent = new Intent(BASE_ADDRESS);
                                intent.putExtra(CLOSE_ALL, 1);
                                //发送广播
                                sendBroadcast(intent);
                                startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                                SPUtil.clearAll(MyApplication.getInstance());
                                UserApi.toLogout(new Observer<BaseResponse<String>>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(BaseResponse<String> stringBaseResponse) {

                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                            })
                    .addSheetItem("否", ActionSheetDialog.SheetItemColor.Red
                            , which -> {

                            })

                    .show();


        });

    }
}
