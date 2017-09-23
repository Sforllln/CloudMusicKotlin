package com.zs.cloudmusickotlin.ui.image

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.youth.banner.loader.ImageLoader

/**
 * Created by zs on 2017/8/29.
 */

class GlideImage : ImageLoader() {
    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        Glide.with(context!!.applicationContext)
                .load(path)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView)
    }

}