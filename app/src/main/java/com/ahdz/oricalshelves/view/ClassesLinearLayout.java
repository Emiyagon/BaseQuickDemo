package com.ahdz.oricalshelves.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class ClassesLinearLayout extends LinearLayout {
    private float round = 65;//圆角半径像素值

    public ClassesLinearLayout(Context context) {
        super(context);
    }

    public ClassesLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClassesLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setRound(float round) {
        this.round = round;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (round > 0) {
            Path path = new Path();
            RectF rectF = new RectF(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
            path.addRoundRect(rectF,round , round, Path.Direction.CW);
            // 先对canvas进行裁剪
            canvas.clipPath(path, Region.Op.INTERSECT);
        }
        super.dispatchDraw(canvas);
    }
}