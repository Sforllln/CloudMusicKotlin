package com.zs.cloudmusickotlin.ui.activity

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.view.PagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.*
import android.widget.Toast
import com.zs.cloudmusickotlin.R
import com.zs.cloudmusickotlin.ui.widget.FixedSpeedScroller
import kotlinx.android.synthetic.main.activity_guide.*
import kotlinx.android.synthetic.main.activity_guide_indicator.*


class GuideActivity : AppCompatActivity(), View.OnClickListener {


    //滑动标志
    private val TOUCH_LEFT = 0
    private val TOUCH_RIGHT = 1
    //
    private var gestureDetector: GestureDetector? = null
    private var pagerView: ArrayList<View> = ArrayList(3)
    private var firstOnTouch: Long = 0

    //页数
    private val PAGE_ONE = 1
    private val PAGE_TWO = 2
    private val PAGE_THREE = 3

    //处理滑动时间的最小时间为 1000ms
    private val CAN_SCROLLER_TIME = 1000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)
        guide_ind_one.isSelected = true //初始化默认的指示器状态
        gestureDetector = GestureDetector(this, onGestureListener)//初始化手指监听
        initTopPagerView()
        setPagerScrollSpeed(800)
        initButtonEvent()
    }

    private fun initButtonEvent() {
        iv_register.setOnClickListener(this)
        iv_enter.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            iv_register.id -> {
                showToast("register")
            }
            iv_enter.id -> {
                showToast("enter")
            }
        }
    }

    //反射修改 viewpager 的滑动速度
    private fun setPagerScrollSpeed(ms: Int) {
        try {
            val clazz = Class.forName("android.support.v4.view.ViewPager")
            val f = clazz.getDeclaredField("mScroller")
            val fixedSpeedScroller = FixedSpeedScroller(this, ms)
            f.isAccessible = true
            f.set(guide_title_pager, fixedSpeedScroller)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    //初始化视图
    private fun initTopPagerView() {
        val viewOne = LayoutInflater.from(this).inflate(R.layout.guide_title_one, null)
        val viewTwo = LayoutInflater.from(this).inflate(R.layout.guide_title_two, null)
        val viewThree = LayoutInflater.from(this).inflate(R.layout.guide_title_three, null)
        pagerView.add(viewOne)
        pagerView.add(viewTwo)
        pagerView.add(viewThree)
        guide_title_pager.adapter = guideTopTitleAdapter(pagerView)
        guide_title_pager.setOnTouchListener { _, _ -> true }
    }

    //滑动监听
    private val onGestureListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            val x = e2.x - e1.x
            if (x > 0) {
                touchResult(TOUCH_RIGHT)
            } else if (x < 0) {
                touchResult(TOUCH_LEFT)
            }
            return true
        }
    }

    //触摸事件交由手势监听对象处理.
    override fun onTouchEvent(event: MotionEvent): Boolean
            = gestureDetector!!.onTouchEvent(event)


    private fun touchResult(action: Int) {
        val secondOnTouch = System.currentTimeMillis()
        if (secondOnTouch - firstOnTouch < CAN_SCROLLER_TIME) return
        firstOnTouch = secondOnTouch
        changPage(action)
    }

    private fun changPage(mode: Int) {
        val currentPage = getCurrentPage()
        if (mode == TOUCH_LEFT) {
            if (currentPage != 3) resetGuideIndStatus()
            when (currentPage) {
                PAGE_ONE -> {
                    iv_guide_share_one.visibility = View.VISIBLE

                    guide_ind_two.isSelected = true
                    guide_title_pager.currentItem = 1
                    guideAnim(iv_guide_share_one, R.mipmap.guide_two)
                }
                PAGE_TWO -> {
                    iv_guide_share_two.visibility = View.VISIBLE
                    iv_guide_share_one.visibility = View.INVISIBLE

                    guide_ind_three.isSelected = true
                    guide_title_pager.currentItem = 2
                    guideAnim(iv_guide_share_two, R.mipmap.guide_three)
                }
                PAGE_THREE -> {
                    startActivity(Intent(this, SplashActivity::class.java))
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    finish()
                }
            }
        }
        if (mode == TOUCH_RIGHT) {
            if (currentPage != 1) resetGuideIndStatus()
            when (currentPage) {
                PAGE_THREE -> {
                    iv_guide_share_two.visibility = View.INVISIBLE
                    ObjectAnimator.ofFloat(iv_guide_share_two, "y", (cardview.top + cardview.height * 0.317).toFloat() - iv_guide_share_two.height / 2).setDuration(0).start()
                    guide_ind_two.isSelected = true
                    guide_title_pager.currentItem = 1
                    cardview.setImageResource(R.mipmap.guide_two)
                }
                PAGE_TWO -> {
                    iv_guide_share_one.visibility = View.INVISIBLE
                    ObjectAnimator.ofFloat(iv_guide_share_one, "y", (cardview.top + cardview.height * 0.555).toFloat() - iv_guide_share_one.height / 2).setDuration(0).start()
                    guide_ind_one.isSelected = true
                    guide_title_pager.currentItem = 0
                    cardview.setImageResource(R.mipmap.guide_one)


                }
            }
        }
    }

    /**
     *(cardview.top + cardview.height * 0.023 + 16).toFloat())
     *
     * cardview.top + 16 则表示约束对象cardview在ConstraintLayout的位置.
     *
     *cardview.height * 0.023 - >ConstraintLayout中Vertical_bias的数值,
     * 表明动画对象在cardview中的相对位置,从而可以得出动画标志在ConstraintLayout的位置
     *
     * **/
    private fun guideAnim(target: View, @IdRes id: Int) {

        cardview.visibility = View.INVISIBLE
        cardview.setImageResource(id)

        val anim = ObjectAnimator.ofFloat(target, "y", (cardview.top + cardview.height * 0.023 + 16).toFloat()).setDuration(500)

        anim.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {}

            override fun onAnimationEnd(animation: Animator?) {
                cardview.visibility = View.VISIBLE
                ObjectAnimator.ofFloat(cardview, "alpha", 0.0f, 1f).setDuration(240).start()
            }

            override fun onAnimationCancel(animation: Animator?) {}

            override fun onAnimationStart(animation: Animator?) {}

        })
        anim.start()

    }


    //判断状态以确认当前的页数
    private fun getCurrentPage(): Int {
        if (guide_ind_one.isSelected) return PAGE_ONE
        if (guide_ind_two.isSelected) return PAGE_TWO
        if (guide_ind_three.isSelected) return PAGE_THREE
        return 0
    }

    //重置指示器的状态
    private fun resetGuideIndStatus() {
        guide_ind_one.isSelected = false
        guide_ind_two.isSelected = false
        guide_ind_three.isSelected = false
    }

    //适配器
    class guideTopTitleAdapter(var view: List<View>) : PagerAdapter() {

        override fun isViewFromObject(view: View?, `object`: Any?): Boolean
                = view == `object`

        override fun getCount(): Int
                = view.size

        override fun instantiateItem(container: ViewGroup?, position: Int): Any {
            container!!.addView(view[position])
            return view[position]
        }

        override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
            container!!.removeView(view[position])
        }


    }

    //拓展函数
    private fun AppCompatActivity.showToast(text: CharSequence) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()


}
