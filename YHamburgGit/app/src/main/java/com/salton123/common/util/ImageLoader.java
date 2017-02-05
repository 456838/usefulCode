package com.salton123.common.util;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.widget.ImageView;

import com.salton123.common.net.HttpResponseHandler;
import com.salton123.mengmei.R;

import org.xutils.common.Callback;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.File;


/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2015-11-05
 * Time: 18:25
 * Description:
 */
public class ImageLoader {

    public static void display(final ImageView p_ImageView, final String p_Uri, final HttpResponseHandler<String> p_HttpResponseHandler) {
        final ImageOptions _options = new ImageOptions.Builder().setFadeIn(true)
                .setConfig(Bitmap.Config.ALPHA_8)
                .setImageScaleType(ImageView.ScaleType.CENTER)
                .build();
        if (!TextUtils.isEmpty(p_Uri) && p_Uri.startsWith("http")) {
            x.image().loadFile(p_Uri, null, new Callback.CacheCallback<File>() {
                @Override
                public boolean onCache(File result) {
                    x.image().bind(p_ImageView, result.getAbsolutePath(), _options);
                    p_HttpResponseHandler.onSuccess(result.getAbsolutePath());
                    return false;
                }

                @Override
                public void onSuccess(File result) {
                    x.image().bind(p_ImageView, result.getAbsolutePath(), _options);
                    p_HttpResponseHandler.onSuccess(result.getAbsolutePath());
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {

                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });
        } else {
            p_ImageView.setImageResource(R.drawable.pic_loading);
        }
    }


//    public static void displayBigImage(Context p_Context, ImageView p_ImageView, String p_Uri) {
//        int screenWidth = DensityUtil.getScreenWidth();
//        int screenHeight = DensityUtil.getScreenHeight();
//        ImageOptions options = new ImageOptions.Builder().setFadeIn(true)
//                .setConfig(Bitmap.Config.ALPHA_8)
//                .setSize(screenWidth, screenHeight)
//                        // .setFailureDrawableId(R.mipmap.pic_loading_error)
//                .setLoadingDrawableId(R.drawable.pic_loading)
//                .build();
//
//        if (!TextUtils.isEmpty(p_Uri) && p_Uri.startsWith("http")) {
//            x.image().bind(p_ImageView, p_Uri);
//        } else {
//            p_ImageView.setImageResource(R.drawable.pic_loading);
//        }
//    }



//    final static ImageOptions options = new ImageOptions.Builder().setFadeIn(true)
//            .setConfig(Bitmap.Config.ALPHA_8)
//            .setSize(DensityUtil.getScreenWidth() / 2, DensityUtil.getScreenHeight() / 2)
////            .setFailureDrawableId(R.mipmap.pic_loading_error)
//            .setLoadingDrawableId(R.drawable.pic_loading)
//            .build();


   /* public static class CropSquareTransformation implements Transformation {
        @Override public Bitmap transform(Bitmap source) {
            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) /20;
            int y = (source.getHeight() - size) /20;
            Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
            if (result != source) {
                source.recycle();
            }
            return result;
        }
        @Override public String key() { return "square()"; }
    }*/

}
