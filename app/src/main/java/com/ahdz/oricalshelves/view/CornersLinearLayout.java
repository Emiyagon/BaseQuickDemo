package com.ahdz.oricalshelves.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.widget.LinearLayout;

//圆角LinearLayout
public class CornersLinearLayout extends LinearLayout {
    private float round = 95f;//圆角半径像素值

    public CornersLinearLayout(Context context) {
        super(context);
    }

    public CornersLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CornersLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
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
            path.addRoundRect(rectF, round, round, Path.Direction.CW);
            // 先对canvas进行裁剪
            canvas.clipPath(path, Region.Op.INTERSECT);
        }
        super.dispatchDraw(canvas);
    }
}