package com.zs.cloudmusickotlin

import android.app.Application
import com.taobao.weex.InitConfig
import com.taobao.weex.WXEnvironment
import com.taobao.weex.WXSDKEngine
import com.zs.cloudmusickotlin.ui.image.WxImageAdapter

/**
 * Created by llln on 2017/9/25.
 */
class MyApplication : Application() {


    override fun onCreate() {
        super.onCreate()
        WXEnvironment.addCustomOptions("appName", "TBSample")
        WXSDKEngine.initialize(this, InitConfig.Builder().setImgAdapter( WxImageAdapter()).build())
    }


}