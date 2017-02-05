package com.salton123.common.util;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;

import com.salton123.mengmei.R;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2015-11-05
 * Time: 18:25
 * Description:
 */
public class ImageLoaderUtils {

    /**
     * 基于XUtils3的图片加载框架
     *
     * @param p_ImageView 图片组件
     * @param p_Uri       图片url
     */
    public static void display(ImageView p_ImageView, String p_Uri) {
        int screenWidth = DensityUtil.getScreenWidth();
        int screenHeight = DensityUtil.getScreenHeight();
        ImageOptions options = new ImageOptions.Builder().setFadeIn(true)
                .setConfig(Bitmap.Config.ALPHA_8)
                .setSize(screenWidth / 2, screenHeight / 2)
                        // .setFailureDrawableId(R.mipmap.pic_loading_error)
                .setLoadingDrawableId(R.drawable.pic_loading)
                .build();

        if (!TextUtils.isEmpty(p_Uri) ) {
            x.image().bind(p_ImageView, p_Uri, options);
        } else {
            p_ImageView.setImageResource(R.drawable.pic_loading);
        }
    }

    /**
     * 显示稍微大一点的图片
     *
     * @param p_ImageView 图片组件
     * @param p_Uri       图片url
     */
    public static void displayBigImage(ImageView p_ImageView, String p_Uri) {
        int screenWidth = DensityUtil.getScreenWidth();
        int screenHeight = DensityUtil.getScreenHeight();
        ImageOptions options = new ImageOptions.Builder().setFadeIn(true)
                .setConfig(Bitmap.Config.ALPHA_8)
                .setSize(screenWidth, screenHeight)
                        // .setFailureDrawableId(R.mipmap.pic_loading_error)
                .setLoadingDrawableId(R.drawable.pic_loading)
                .build();
        if (!TextUtils.isEmpty(p_Uri)) {
            x.image().bind(p_ImageView, p_Uri);
        } else {
            p_ImageView.setImageResource(R.drawable.pic_loading);
        }
    }
}
