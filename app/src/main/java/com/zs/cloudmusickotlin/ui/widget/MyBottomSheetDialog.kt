package com.zs.cloudmusickotlin.ui.widget

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.util.DisplayMetrics
import android.view.ViewGroup


/**
 * Created by Jeong Woo on 2017/9/5.
 *
 * 解决 BottomSheetDialog 弹出的时候statusBar变成黑的问题
 *
 */
class MyBottomSheetDialog(private val activity: Activity) : BottomSheetDialog(activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val screenHeight = getScreenHeight()
        val statusBarHeight = getStatusBarHeight()
        val dialogHeight = screenHeight - statusBarHeight
        window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, if (dialogHeight == 0) ViewGroup.LayoutParams.MATCH_PARENT else dialogHeight)

    }


    private fun getScreenHeight(): Int {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    private fun getStatusBarHeight(): Int {
        var statusBarHeight = 0
        val res = activity.resources
        val resourceId = res.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId)
        }
        return statusBarHeight
    }


}