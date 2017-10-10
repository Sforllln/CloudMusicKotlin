package com.zs.cloudmusickotlin.ui.activity

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import android.widget.Toast

/**
 * Created by llln on 2017/9/23.
 * 逻辑优化
 */
open class BaseActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //todo
    }


    //设置状态栏透明
    fun statusBarTransParent() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val localLayoutParams = window.attributes
            localLayoutParams.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or localLayoutParams.flags
        }
    }

    //显示toast
     fun AppCompatActivity.showToast(charSequence: CharSequence, duration: Int) {
        Toast.makeText(this, charSequence, duration).show()
    }

}