package com.zs.cloudmusickotlin.ui.widget

import android.content.Context
import android.widget.Scroller

/**
 * Created by llln on 2017/9/22.
 *
 * 更改viewpager.setCurrentItem的滑动时间.
 * @mDuration 时长 单位 ms.
 *
 */
class FixedSpeedScroller(context: Context, private var mDuration: Int) : Scroller(context) {


    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int) {
        startScroll(startX, startY, dx, dy, mDuration)
    }

    override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
        super.startScroll(startX, startY, dx, dy, mDuration)

    }


}