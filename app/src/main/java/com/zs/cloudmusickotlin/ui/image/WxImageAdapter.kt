package com.zs.cloudmusickotlin.ui.image

import android.text.TextUtils
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.taobao.weex.WXEnvironment
import com.taobao.weex.WXSDKManager
import com.taobao.weex.adapter.IWXImgLoaderAdapter
import com.taobao.weex.common.WXImageStrategy
import com.taobao.weex.dom.WXImageQuality

/**
 * Created by llln on 2017/9/25.
 *
 */
class WxImageAdapter : IWXImgLoaderAdapter {
    override fun setImage(url: String?, view: ImageView?, quality: WXImageQuality?, strategy: WXImageStrategy?) {
        WXSDKManager.getInstance().postOnUiThread({
            if (view == null || view.layoutParams == null) return@postOnUiThread
            if (TextUtils.isEmpty(url)) {
                view.setImageBitmap(null)
                return@postOnUiThread
            }
            var temp = url
            if (url!!.startsWith("//")) {
                temp = "http:" + url
            }
            if (view.layoutParams.width <= 0 || view.height <= 0) return@postOnUiThread
            Glide.with(WXEnvironment.getApplication())
                    .load(temp).into(view)

        }, 0)
    }

}