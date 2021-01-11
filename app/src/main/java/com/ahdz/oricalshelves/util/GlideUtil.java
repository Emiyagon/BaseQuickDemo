package com.ahdz.oricalshelves.util;

import android.content.Context;
import android.widget.ImageView;

import com.ds.oricalshelves.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;


/**
 *  glide加载工具类
 * @author bullet
 * @date 2018/8/13
 */

public class GlideUtil {

    private static int ERROR_IMG = R.mipmap.ic_launcher;
    private static int PLACE_IMG =  R.mipmap.ic_launcher;
    private static int FALLBACK_IMG =  R.mipmap.ic_launcher;




    /**
     * 加载圆形图片
     * @param context context
     * @param url  图片地址
     * @param imageView ima
     */
    public static void  putRollImg(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
//                .apply(options)
                .apply( new RequestOptions().centerCrop()
                        .placeholder(PLACE_IMG)
                        .error(ERROR_IMG)
                        .fallback(FALLBACK_IMG))
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(imageView);
    }



    /**
     * 加载圆角图片
     * @param url
     * @param imageView
     * @param corner
     */
    public static void putrollCornerImg(Object url, ImageView imageView, int corner) {
        //设置图片圆角角度
        Glide.with(imageView)
                .load(url)
                .apply(new RequestOptions().centerCrop()
                .placeholder(PLACE_IMG)
                .error(ERROR_IMG)
                .fallback(FALLBACK_IMG))
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(corner)))
//                .apply(RequestOptions.bitmapTransform(new BlurTransformation(imageView.getContext(),50)))
                .into(imageView);

    }


    /**
     *   加载一张正常的图片
     * @param url
     * @param imageView
     */
    public static void putHttpImg(Object url, ImageView imageView) {
        Glide.with(imageView)
                .load(url)
                .apply(new RequestOptions().centerInside()
                .placeholder(PLACE_IMG)//CENTER_INSIDE
                .error(ERROR_IMG)
                .fallback(FALLBACK_IMG))
//                .thumbnail(0.1f)//先显示缩略图  缩略图为原图的1/10
                .into(imageView);
    }

    private int BANNER_IMG = 0;
    public static void putBannerImg(Object url, ImageView imageView) {
        Glide.with(imageView)
                .load(url)
                .apply(new RequestOptions().centerInside()
//                        .placeholder(BANNER_IMG)
//                        .error(BANNER_IMG)
//                        .fallback(BANNER_IMG)
                )
//                .thumbnail(0.1f)//先显示缩略图  缩略图为原图的1/10
                .into(imageView);
    }

}
