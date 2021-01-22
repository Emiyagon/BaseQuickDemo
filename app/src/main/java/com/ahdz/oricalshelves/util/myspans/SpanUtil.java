package com.ahdz.oricalshelves.util.myspans;

import android.text.TextUtils;
import android.widget.TextView;

import com.ahdz.oricalshelves.MyApplication;
import com.ahdz.oricalshelves.util.myspans.unit.SimplifySpanBuild;
import com.ahdz.oricalshelves.util.myspans.unit.SpecialGravity;
import com.ahdz.oricalshelves.util.myspans.unit.SpecialLabelUnit;


public class SpanUtil {


    /**
     *    淘宝 天猫/年货节 样式的span
     */
    public static void setNormalSpan(TextView tv, String author,String tag1,int tg1,int bg1,float tag1F,int tg1Width,int tg1Height,
                                     String tag2,int tg2,int bg2,float tag2F,int tg2Width,int tg2Height) {
        SimplifySpanBuild tv1SpannableString = new SimplifySpanBuild(author) ;// .build()
//                .appendToFirst(new SpecialLabelUnit("1212", Color.WHITE, sp2px(8), Color.RED, 70, 35).useTextBold().setGravity(SpecialGravity.CENTER))
//                .appendToFirst(new SpecialLabelUnit("天猫", Color.WHITE, sp2px(8), 0xFFFF5000, 60, 35).setGravity(SpecialGravity.CENTER))
        if (!TextUtils.isEmpty(tag1)) {
            tv1SpannableString.appendToFirst(
                    new SpecialLabelUnit(tag1, tg1, sp2px(tag1F), bg1, tg1Width, tg1Height)
                            .useTextBold().setGravity(SpecialGravity.CENTER));
        }
        if (!TextUtils.isEmpty(tag2)) {
            tv1SpannableString.appendToFirst(
                    new SpecialLabelUnit(tag2, tg2, sp2px(tag2F),bg2, tg2Width, tg2Height)
                            .useTextBold().setGravity(SpecialGravity.CENTER));
        }
        tv.setText(tv1SpannableString.build());

    }
    /**
     //上面的示例代码里面是这样的

     CharSequence tv1SpannableString = new SimplifySpanBuild(" 艾客优品雷霆Dock 2 雷电转USB3.0/火线/esata 扩展HUB")
     .appendToFirst(new SpecialLabelUnit("1212", Color.WHITE, sp2px(8), Color.RED, 70, 35).useTextBold().setGravity(SpecialGravity.CENTER))
     .appendToFirst(new SpecialLabelUnit("天猫", Color.WHITE, sp2px(8), 0xFFFF5000, 60, 35).setGravity(SpecialGravity.CENTER))
     .build();
     tvText1.setText(tv1SpannableString);
     */

    /**
     *  边框样式的,正常填充色黑色就行,但是这里为了拓展,把边框和填充色全部取出来,这样就可以拓展了
     *  其他图标样式的在下面具体的,有需要就直接拉出来用就行了
     * @param tv
     * @param tag
     * @param content
     * @param position   SpecialGravity.CENTER /SpecialGravity.TOP / SpecialGravity.BOTTOM
     */
    public static void setBroderSpan(TextView tv,String content,String tag,int tagColor,float spValue,int fillColor,
                                     int boredColor,float borderSize,int topPadding,int leftPadding,int position) {
        SimplifySpanBuild simple = new SimplifySpanBuild() ;// .build()

        simple.append(
                new SpecialLabelUnit(tag, tagColor, sp2px(spValue),fillColor).
                        showBorder(boredColor, borderSize).
                        setPadding(topPadding).
                        setPaddingLeft(leftPadding).// 这个和下面两个基本上都一样的,所以值设置为一样的,方便好看
                        setPaddingRight(leftPadding).
                        setGravity(position));
        simple.append(content);

        tv.setText(simple.build());

    }

    /**
        //  上面的示例代码写法是这样的
     SimplifySpanBuild simplifySpanBuild11 = new SimplifySpanBuild();
     simplifySpanBuild11.append(new SpecialLabelUnit("火", Color.RED, sp2px(7), BitmapFactory.decodeResource(getResources(), R.drawable.label)).setPadding(15).setGravity(SpecialGravity.CENTER))
     .append("正常")
     .append(new SpecialLabelUnit("火", Color.RED, sp2px(7), BitmapFactory.decodeResource(getResources(), R.drawable.label)).setPadding(15).setGravity(SpecialGravity.TOP))
     .append("正常")
     .append(new SpecialLabelUnit("火", Color.RED, sp2px(7), BitmapFactory.decodeResource(getResources(), R.drawable.label)).setPadding(15).setGravity(SpecialGravity.BOTTOM))
     .append("正常")
     .append(new SpecialLabelUnit("原创", Color.BLACK, sp2px(10), BitmapFactory.decodeResource(getResources(), R.drawable.tag)).setPadding(5).setPaddingLeft(15).setPaddingRight(30).setGravity(SpecialGravity.TOP))
     .append("正常")
     .append(new SpecialLabelUnit("原创", Color.BLACK, sp2px(10), BitmapFactory.decodeResource(getResources(), R.drawable.tag)).setPadding(5).setPaddingLeft(15).setPaddingRight(30).setGravity(SpecialGravity.CENTER))
     .append("正常")
     .append(new SpecialLabelUnit("原创", Color.BLACK, sp2px(10), BitmapFactory.decodeResource(getResources(), R.drawable.tag)).setPadding(5).setPaddingLeft(15).setPaddingRight(30).setGravity(SpecialGravity.BOTTOM))
     .append("正常")
     .append(new SpecialLabelUnit("原创", Color.WHITE, sp2px(10), 0xFFFF5000).setLabelBgRadius(5).setPadding(5).setPaddingLeft(10).setPaddingRight(10).setGravity(SpecialGravity.BOTTOM))
     .append("正常")
     .append(new SpecialLabelUnit("原创", Color.WHITE, sp2px(10), 0xFFFF5000).setLabelBgRadius(5).setPadding(5).setPaddingLeft(10).setPaddingRight(10).setGravity(SpecialGravity.TOP))
     .append("正常")
     .append(new SpecialLabelUnit("原创", Color.WHITE, sp2px(10), 0xFFFF5000).setLabelBgRadius(5).setPadding(5).setPaddingLeft(10).setPaddingRight(10).setGravity(SpecialGravity.CENTER))
     .append("正常")
     .append(new SpecialLabelUnit("原创", Color.WHITE, sp2px(10), Color.GRAY).setLabelBgRadius(5).showBorder(Color.BLACK, 2).setPadding(5).setPaddingLeft(10).setPaddingRight(10).setGravity(SpecialGravity.BOTTOM))
     .append("正常")
     .append(new SpecialLabelUnit("原创", Color.WHITE, sp2px(10), Color.GRAY).setLabelBgRadius(5).showBorder(Color.BLACK, 2).setPadding(5).setPaddingLeft(10).setPaddingRight(10).setGravity(SpecialGravity.TOP))
     .append("正常")
     .append(new SpecialLabelUnit("原创", Color.WHITE, sp2px(10), Color.GRAY).setLabelBgRadius(5).showBorder(Color.BLACK, 2).setPadding(5).setPaddingLeft(10).setPaddingRight(10).setGravity(SpecialGravity.CENTER))
     .append("正常")
     .append(new SpecialLabelUnit("原创", Color.RED, sp2px(10), Color.TRANSPARENT).showBorder(Color.BLACK, 2).setPadding(5).setPaddingLeft(10).setPaddingRight(10).setGravity(SpecialGravity.BOTTOM))
     .append("正常")
     .append(new SpecialLabelUnit("原创", Color.RED, sp2px(10), Color.TRANSPARENT).showBorder(Color.BLACK, 2).setPadding(5).setPaddingLeft(10).setPaddingRight(10).setGravity(SpecialGravity.TOP))
     .append("正常")
     .append(new SpecialLabelUnit("原创", Color.RED, sp2px(10), Color.TRANSPARENT).showBorder(Color.BLACK, 2).setPadding(5).setPaddingLeft(10).setPaddingRight(10).setGravity(SpecialGravity.CENTER));
     tvText11.setText(simplifySpanBuild11.build());
     */



    private static float sp2px(float spValue) {
        final float scale = MyApplication.getInstance().getResources().getDisplayMetrics().scaledDensity;
        return spValue * scale;
    }

}
