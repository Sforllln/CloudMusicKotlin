package com.zs.cloudmusickotlin.ui.activity

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.taobao.weex.IWXRenderListener
import com.taobao.weex.WXSDKInstance
import com.taobao.weex.common.WXRenderStrategy
import com.taobao.weex.utils.WXFileUtils
import com.zs.cloudmusickotlin.R
import kotlinx.android.synthetic.main.activity_wxtest.*


/**
 * 体验一下 weex
 *
 * **/
class WXTestActivity : BaseActivity() {

    private val TEST_URL = "http://online.youchang88.com/salesAssist/shopDetail.js"
    private lateinit var mWXSDKInstance: WXSDKInstance


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wxtest)

        statusBarTransParent() // 设置状态栏透明

        mWXSDKInstance = WXSDKInstance(this)
        val options = HashMap<String, Any>()
        options.put(WXSDKInstance.BUNDLE_URL, TEST_URL)
//        mWXSDKInstance.renderByUrl(packageName, TEST_URL, options, null, -1, -1, WXRenderStrategy.APPEND_ONCE)   //加载网络上的一段JS
        mWXSDKInstance.render(packageName, WXFileUtils.loadFileContent("hello.js", this), options, null, -1, -1, WXRenderStrategy.APPEND_ONCE)  //加载本地的一段JS
        mWXSDKInstance.registerRenderListener(object : IWXRenderListener {
            override fun onRefreshSuccess(instance: WXSDKInstance?, width: Int, height: Int) {

            }

            override fun onException(instance: WXSDKInstance?, errCode: String?, msg: String?) {
            }

            override fun onViewCreated(instance: WXSDKInstance?, view: View?) {
                if (view!!.parent != null) {
                    (view.parent as ViewGroup).removeView(view)
                }
                viewGroup.addView(view)
            }

            override fun onRenderSuccess(instance: WXSDKInstance?, width: Int, height: Int) {
            }
        })
    }

    override fun onStart() {
        super.onStart()

        mWXSDKInstance.onActivityStart()

    }

    override fun onPostResume() {
        super.onPostResume()
        mWXSDKInstance.onActivityResume()
    }

    override fun onPause() {
        super.onPause()
        mWXSDKInstance.onActivityPause()
    }


    override fun onStop() {
        super.onStop()
        mWXSDKInstance.onActivityStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mWXSDKInstance.onActivityDestroy()
    }


}
