package com.ahdz.oricalshelves.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ProgressBar;

import com.ahdz.oricalshelves.R;

/**
 *   圆形进度条
 */
public class StripTypePurpleProgressBar extends ProgressBar {
    private String text_progress;
    private Paint mPaint;
    private int curProgress;
    private int maxProgress;
    private static final int INTERVAL = 1;
    private static final int TAG_WEBVIEW = 100;

    public StripTypePurpleProgressBar(Context context) {
        super(context);
        initPaint();
        setAnimProgress(0);
    }

    public StripTypePurpleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public StripTypePurpleProgressBar(Context context, AttributeSet attrs,
                                      int defStyle) {
        super(context, attrs, defStyle);
        initPaint();
    }

    @Override
    public synchronized void setProgress(int progress) {
        super.setProgress(progress);
        setTextProgress(progress);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect rect = new Rect();
        this.mPaint.setTextSize(30);
        this.mPaint.getTextBounds(this.text_progress, 0,
                this.text_progress.length(), rect);
        int w = (int) mPaint.measureText(text_progress);

        int x = (getWidth() * curProgress / 100);

        int y = getHeight() / 2 - rect.centerY();
        if (x - 5 + getHeight() / 4 > getWidth()) {
            canvas.drawText(this.text_progress, getWidth() - w - 5, y,
                    this.mPaint);
        } else if (x - 5 + getHeight() / 4 > w) {
            Paint paint = new Paint();
            paint.setColor(getResources().getColor(R.color.purple));
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setStrokeWidth(3);
            canvas.drawCircle(x - 5, getHeight() / 2, getHeight() / 2, paint);
            canvas.drawText(this.text_progress, x - 10 + getHeight() / 2 - w,
                    y, this.mPaint);
        } else {
            canvas.drawText(this.text_progress, x + 10, y, this.mPaint);
        }
    }

    private void initPaint() {
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setColor(getResources().getColor(R.color.transparent));//  提示百分比字体颜色,随意更改,这里需求不显示就设置为透明色
    }

    private void setTextProgress(int progress) {
        this.text_progress = progress + "%";
    }

    public synchronized void setAnimProgress(int progress) {
        maxProgress = progress;
        curProgress = 0;
        mHandler.sendEmptyMessageDelayed(INTERVAL, 2);
    }

    public synchronized void setAnimProgress(int progress, boolean isMax) {
        maxProgress = progress;
        curProgress = 0;
        if (!isMax) {
            mHandler.sendEmptyMessageDelayed(INTERVAL, 2);
        } else {
            mHandler.removeMessages(INTERVAL);
            setProgress(progress);
        }

    }

    public synchronized void setAnimProgress2(int progress) {
        maxProgress = progress;
        curProgress = this.getProgress();
        Message msg = mHandler.obtainMessage();
        msg.what = TAG_WEBVIEW;
        mHandler.sendMessageDelayed(msg, 2);
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case INTERVAL:
                    if (curProgress < maxProgress) {
                        setProgress(curProgress);
                        curProgress += 1;
                        mHandler.sendEmptyMessageDelayed(INTERVAL, 2);
                    } else {
                        setProgress(curProgress);
                    }

                    break;
                case TAG_WEBVIEW:
                    if (curProgress < maxProgress) {
                        setProgress(curProgress);
                        curProgress += 1;
                        mHandler.sendEmptyMessageDelayed(TAG_WEBVIEW, 2);
                    } else {
                        setProgress(curProgress);
                    }
                    if (curProgress == 100) {
                        Animation animationUP = new TranslateAnimation(
                                Animation.RELATIVE_TO_SELF, 0.0f,
                                Animation.RELATIVE_TO_SELF, 0.0f,
                                Animation.RELATIVE_TO_SELF, 0,
                                Animation.RELATIVE_TO_SELF, -1.0f);
                        animationUP.setDuration(600);
                        animationUP.setFillAfter(true);
                    }
                    break;
                default:
                    setProgress(curProgress);
                    break;
            }
        }

        ;
    };

}
