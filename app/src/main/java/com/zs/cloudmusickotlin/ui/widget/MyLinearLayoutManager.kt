package com.zs.cloudmusickotlin.ui.widget

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet

/**
 * Created by Jeong Woo on 2017/9/5.
 *
 * RecyclerView的原生BUG
 *
 */
class MyLinearLayoutManager(context: Context) : LinearLayoutManager(context) {


    constructor(context: Context, orientation: Int,reverseLayout:Boolean):this(context)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int):this(context)



    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        try {
            super.onLayoutChildren(recycler, state)
        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
        }

    }

}