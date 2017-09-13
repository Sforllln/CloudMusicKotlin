package com.zs.cloudmusickotlin.ui.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.LinearLayout
import com.zs.cloudmusickotlin.R

/**
 * Created by Jeong Woo on 2017/8/31.
 *
 * 画Item的分割线.
 * @marginLeft 分割线距离左边的间距.
 */

class CustomLinearLayout(context: Context?, attributeSet: AttributeSet) : LinearLayout(context, attributeSet) {

    val paint = Paint()
    var marginLeft = 0f

    init {
        setWillNotDraw(false)
        val a: TypedArray = context!!.obtainStyledAttributes(attributeSet, R.styleable.CustomLinearLayout)
        marginLeft = a.getDimension(R.styleable.CustomLinearLayout_marginLeft, 0f)
        a.recycle()
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.color = resources.getColor(R.color.recycler_view_divider_color)
        paint.strokeWidth = 1f
        canvas!!.drawLine(marginLeft, height - 0.5f, width.toFloat(), height - 0.5f, paint)
    }


}
