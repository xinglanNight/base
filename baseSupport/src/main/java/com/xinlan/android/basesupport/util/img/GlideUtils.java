package com.xinlan.android.basesupport.util.img;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class GlideUtils {
    public static void loadVideoThumbnail(Context context, int error,
                                          int placeHolder, String url, ImageView iv) {
        Glide.with(context).setDefaultRequestOptions(
                new RequestOptions()
                        .frame(1000000)
                        .centerCrop()
                        .error(error)
                        .placeholder(placeHolder))
                .load(url)
                .into(iv);
    }

    public static void loadVideoThumbnail(Context context, String url, ImageView iv) {
        Glide.with(context).setDefaultRequestOptions(
                new RequestOptions()
                        .frame(1000000)
                        .centerCrop())
                .load(url)
                .into(iv);
    }
}
