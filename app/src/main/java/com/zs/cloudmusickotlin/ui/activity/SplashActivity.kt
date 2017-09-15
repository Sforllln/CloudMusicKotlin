package com.zs.cloudmusickotlin.ui.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.zs.cloudmusickotlin.MainActivity
import com.zs.cloudmusickotlin.utils.HandlerUtil

/**
 * Created by Jeong Woo on 2017/9/15.
 *
 */
class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        HandlerUtil.runOnUiThreadDelay(Runnable {

            startActivity(Intent(this, MainActivity::class.java))
            finish()

            //需要判断用户是不是第一次运行,第一次运行则打开引导页.
//        if (first){
//            startActivity(Intent(this,GuideActivity.class))
//            finish()
//        }else{
//            startActivity(Intent(this, MainActivity::class.java))
//            finish()
//        }

        }, 1600)

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        }

    }


}