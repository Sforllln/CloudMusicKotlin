package com.zs.cloudmusickotlin.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

/**
 * Created by zs on 2017/9/1.
 */

object ImageUtils {

    //图片加载工具类
    fun loadImageWithGlide(context: Context, res: Any, imageView: ImageView) {
        Glide.with(context).load(res).diskCacheStrategy(DiskCacheStrategy.NONE).into(imageView)
    }

}