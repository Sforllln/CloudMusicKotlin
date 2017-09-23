package com.zs.cloudmusickotlin.ui.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.zs.cloudmusickotlin.MainActivity
import com.zs.cloudmusickotlin.R
import com.zs.cloudmusickotlin.utils.HandlerUtil
import com.zs.cloudmusickotlin.utils.SharedPreferencesUtil

/**
 * Created by Jeong Woo on 2017/9/15.
 *
 */
class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        HandlerUtil.runOnUiThreadDelay(Runnable {
            //需要判断用户是不是第一次运行,第一次运行则打开引导页.
            if (SharedPreferencesUtil.isUserFirstEnter(this)) {
                startActivity(Intent(this, GuideActivity::class.java))
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                SharedPreferencesUtil.setUserFirstEnter(this, false)
                finish()
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit)
                finish()
            }

        }, 1800)

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        }

    }


}