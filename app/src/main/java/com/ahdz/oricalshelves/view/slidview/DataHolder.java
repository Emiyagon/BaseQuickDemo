package com.ahdz.oricalshelves.view.slidview;

import android.graphics.drawable.Drawable;

/**
 * Created by peacepassion on 15/8/23.
 *  自定义tabview的修改者,可以通过这个对tabview做修改
 */
public class DataHolder {

    Drawable back;
    Drawable front;
    String title;
    int titleTargetColor;

    public DataHolder(Drawable back, Drawable front, String title, int titleTargetColor) {
        this.back = back;
        this.front = front;
        this.title = title;
        this.titleTargetColor = titleTargetColor;
    }
}
