package com.ahdz.oricalshelves.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.ahdz.oricalshelves.R;


public abstract class ComPopupDialog extends Dialog {

    private View contentView;



    public ComPopupDialog(Context context, int layoutId) {
        super(context, R.style.MyPopupDialog);
        contentView = LayoutInflater.from(context).inflate(layoutId, null, false);
        initView();
        initEvent();
    }
    public ComPopupDialog(Context context, int layoutId, int style) {
        super(context,style);
        contentView = LayoutInflater.from(context).inflate(layoutId, null, false);
        initView();
        initEvent();
    }
    public View getContentView() {
        return contentView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(contentView);
        setCanceledOnTouchOutside(true);// 点击外部是否取消
        setCancelable(true);
        Window window = getWindow();
//        WindowManager.LayoutParams params = win.getAttributes();
//        win.setSoftInputMode(params.softInputMode);
        window.setWindowAnimations(R.style.AnimBottom);
        window.getDecorView().setPadding(0, 0, 0, 0);
        // 获取Window的LayoutParams
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = ViewGroup.LayoutParams.MATCH_PARENT;
        attributes.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        // 一定要重新设置, 才能生效
        window.setAttributes(attributes);
    }

    public abstract void initView();

    public abstract void initEvent();
}
