package com.zs.cloudmusickotlin.utils

import android.content.Context
import com.zs.cloudmusickotlin.Constant

/**
 * Created by llln on 2017/9/18.
 *
 */
object SharedPreferencesUtil {

    fun setUserFirstEnter(context: Context, isFirst: Boolean) {
        val sp = context.getSharedPreferences(Constant.USER_FIRST, Context.MODE_PRIVATE)
        sp.edit().putBoolean(Constant.USER_FIRST, isFirst).apply()
    }

    fun isUserFirstEnter(context: Context): Boolean {
        val sp = context.getSharedPreferences(Constant.USER_FIRST, Context.MODE_PRIVATE)
        return sp.getBoolean(Constant.USER_FIRST, true)
    }


}