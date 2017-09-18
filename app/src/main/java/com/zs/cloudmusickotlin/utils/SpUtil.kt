package com.zs.cloudmusickotlin.utils

import android.content.Context
import com.zs.cloudmusickotlin.Constant

/**
 * Created by llln on 2017/9/18.
 *
 */
object SpUtil {

    fun setUserFirstEnter(context: Context, isFirst: Boolean) {
        val sp = context.getSharedPreferences(Constant.USER_FITST, Context.MODE_PRIVATE)
        sp.edit().putBoolean(Constant.USER_FITST, isFirst).apply()
    }

    fun isUserFirstEnter(context: Context): Boolean {
        val sp = context.getSharedPreferences(Constant.USER_FITST, Context.MODE_PRIVATE)
        return sp.getBoolean(Constant.USER_FITST, true)
    }


}