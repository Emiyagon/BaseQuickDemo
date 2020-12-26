package com.ahdz.oricalshelves.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import java.util.Arrays;
import java.util.Random;

/**
 *  生成随机验证码图片
 *  里面还有判断方法,可以直接使用
 */
public class ImageCode {

    private  boolean isOnlyNum = true;

    public  void setIsOnlyNum(boolean isOnlyNum) {
        this.isOnlyNum = isOnlyNum;
    }


    //随机数数组

    private  char[] CHAR = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'

    };

    private  char[] MARS = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm',
            'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };
    // System.arraycopy(a, 0, c, 0, a.length);
    private  char[] CHARS =  isOnlyNum ?CHAR :concatAll(CHAR,MARS);


    // 合并多个数组
    public static   char[] concatAll(char[] first, char[]... rest) {
        int totalLength = first.length;
        for (char[] array : rest) {
            totalLength += array.length;
        }
        char[] result = Arrays.copyOf(first, totalLength);
        int offset = first.length;
        for (char[] array : rest) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }
        return result;
    }

    private static ImageCode bmpCode;

    public static ImageCode getInstance() {
        if(bmpCode == null)
            bmpCode = new ImageCode();
        return bmpCode;
    }

    //default settings
    //验证码默认随机数的个数
    private  int DEFAULT_CODE_LENGTH = 4;
    //默认字体大小
    private static final int DEFAULT_FONT_SIZE = 24;
    //默认线条的条数
    private static final int DEFAULT_LINE_NUMBER = 5;
    //padding值
    private static final int BASE_PADDING_LEFT = 10,
            RANGE_PADDING_LEFT = 12,
            BASE_PADDING_TOP = 14,
            RANGE_PADDING_TOP = 16;
    //验证码的默认宽高
    private static final int DEFAULT_WIDTH = 100, DEFAULT_HEIGHT = 40;

    //settings decided by the layout xml
    //canvas width and height
    private int width = DEFAULT_WIDTH, height = DEFAULT_HEIGHT;

    //random word space and pading_top
    private int base_padding_left = BASE_PADDING_LEFT, range_padding_left = RANGE_PADDING_LEFT,
            base_padding_top = BASE_PADDING_TOP, range_padding_top = RANGE_PADDING_TOP;

    //number of chars, lines; font size
    private int codeLength = DEFAULT_CODE_LENGTH, line_number = DEFAULT_LINE_NUMBER, font_size = DEFAULT_FONT_SIZE;

    //variables
    private String code;
    private int padding_left, padding_top;
    private Random random = new Random();

    //验证码图片生成
    public Bitmap createBitmap() {
        padding_left = 0;

        Bitmap bp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bp);

        code = createCode();
        Log.e("Imagecode", code);
        Log.e("Imagecode", "isOnlyNum? ="+isOnlyNum +" -- and chars`s length = "+CHARS.length );

        c.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextSize(font_size);
        //画验证码
        for (int i = 0; i < code.length(); i++) {
            randomTextStyle(paint);
            randomPadding();
            c.drawText(code.charAt(i) + "", padding_left, padding_top, paint);
        }
        //画线条
        for (int i = 0; i < line_number; i++) {
            drawLine(c, paint);
        }

        c.save();//保存 Canvas.ALL_SAVE_FLAG
        c.restore();//
        return bp;
    }


    // 设置图片
    public  String setBmpCode(ImageView view) {
        view.setImageBitmap(createBitmap());
        Log.e("code", "=" + code);
        return code;
    }


    /**
     *   判断验证码是否正确
     * @param passCode  输入的验证码
     * @param isBS  是否区分大小写
     *              true  区分大小写  false 不区分
     * @return
     */
    public boolean isEnter(String passCode, boolean isBS) {
        return isBS? TextUtils.equals(passCode, code) : passCode.equalsIgnoreCase(code);
    }


    public String getCode() {
        return code;
    }



    //生成验证码
    private String createCode() {
        StringBuilder buffer = new StringBuilder();
        CHARS = isOnlyNum ? CHAR : concatAll(CHAR, MARS);//重新赋值一次
        for (int i = 0; i < codeLength; i++) {
            buffer.append(CHARS[random.nextInt(CHARS.length)]);
        }
        return buffer.toString();
    }
    //画干扰线
    private void drawLine(Canvas canvas, Paint paint) {
        int color = randomColor();
        int startX = random.nextInt(width);
        int startY = random.nextInt(height);
        int stopX = random.nextInt(width);
        int stopY = random.nextInt(height);
        paint.setStrokeWidth(1);
        paint.setColor(color);
        canvas.drawLine(startX, startY, stopX, stopY, paint);
    }
    //生成随机颜色
    private int randomColor() {
        return randomColor(1);
    }

    private int randomColor(int rate) {
        int red = random.nextInt(256) / rate;
        int green = random.nextInt(256) / rate;
        int blue = random.nextInt(256) / rate;
        return Color.rgb(red, green, blue);
    }
    //随机生成文字样式，颜色，粗细，倾斜度
    private void randomTextStyle(Paint paint) {
        int color = randomColor();
        paint.setColor(color);
        paint.setFakeBoldText(random.nextBoolean());  //true为粗体，false为非粗体
        float skewX = random.nextInt(11) / 10;
        skewX = random.nextBoolean() ? skewX : -skewX;
        paint.setTextSkewX(skewX); //float类型参数，负数表示右斜，整数左斜
        //paint.setUnderlineText(true); //true为下划线，false为非下划线
        //paint.setStrikeThruText(true); //true为删除线，false为非删除线
    }
    //随机生成padding值
    private void randomPadding() {
        padding_left += base_padding_left + random.nextInt(range_padding_left);
        padding_top = base_padding_top + random.nextInt(range_padding_top);
    }

}
