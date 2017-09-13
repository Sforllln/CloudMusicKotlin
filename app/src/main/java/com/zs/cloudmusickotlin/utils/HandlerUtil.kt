package com.zs.cloudmusickotlin.utils

import android.os.Handler
import android.os.Looper

/**
 * Created by zs on 2016/10/12.
 */

object HandlerUtil {


    private val HANDLER = Handler(Looper.getMainLooper())

    fun runOnUiThread(runnable: Runnable) {
        HANDLER.post(runnable)
    }

    fun runOnUiThreadDelay(runnable: Runnable, delayMillis: Long) {
        HANDLER.postDelayed(runnable, delayMillis)
    }

    fun removeRunnable(runnable: Runnable) {
        HANDLER.removeCallbacks(runnable)
    }


}
